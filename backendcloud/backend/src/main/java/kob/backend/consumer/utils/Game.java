package kob.backend.consumer.utils;

import com.alibaba.fastjson.JSONObject;
import kob.backend.consumer.WebSocketServer;
import kob.backend.pojo.Record;
import kob.backend.pojo.Subuser;
import kob.backend.pojo.User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread{
    private final Integer row, col, wall_cnt;
    private final int[][] g;
    private final static int[] dx= {-1,0,1,0}, dy = {0,1,0,-1};
    private final Player playerA, playerB;
    private Integer nextStepA = null;
    private Integer nextStepB = null;
    private ReentrantLock lock = new ReentrantLock();
    private String status = "playing"; // playing -> finished
    private String loser = ""; // all、A、B
    private Subuser botA;
    private Subuser botB;
    private final static String addBotUrl = "http://localhost:3002/bot/add/";

    public Integer getNextStepA() {
        return nextStepA;
    }

    public void setNextStepA(Integer nextStepA) {
        lock.lock();
        try {
            this.nextStepA = nextStepA;
        } finally {
            lock.unlock();
        }
    }

    public Integer getNextStepB() {
        return nextStepB;
    }

    public void setNextStepB(Integer nextStepB) {
        lock.lock();
        try {
            this.nextStepB = nextStepB;
        } finally {
            lock.unlock();
        }
    }

    public Game(Integer row, Integer col, Integer wall_cnt, Integer idA, Integer idB, Subuser botA, Subuser botB) {
        this.row = row;
        this.col = col;
        this.wall_cnt = wall_cnt;
        this.g = new int[row][col];

        Integer botIdA = -1, botIdB = -1;
        String botCodeA = "", botCodeB = "";
        if(botA != null){
            botIdA = botA.getId();
            botCodeA = botA.getContent();
        }
        if(botB != null){
            botIdB = botB.getId();
            botCodeB = botB.getContent();
        }
        playerA = new Player(idA,row-2, 1, new ArrayList<Integer>(), botIdA, botCodeA);
        playerB = new Player(idB,1,col-2, new ArrayList<Integer>(), botIdB, botCodeB);

        this.botA = botA;
        this.botB = botB;
    }

    public Player getPlayerA() {
        return playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public int[][] getG(){
        return g;
    }

    private boolean draw() {
        for(int i = 0; i < this.row; i++){
            for(int j = 0; j < this.col; j++){
                g[i][j] = 0;
            }
        }

        for(int r = 0; r < this.row; r++){
            g[r][0] = g[r][this.col-1] = 1;
        }
        for(int c = 0; c < this.col; c++){
            g[0][c] = g[this.row-1][c] = 1;
        }
        Random random = new Random();
        for(int i = 0; i < this.wall_cnt / 2; i++){
            for(int j = 0; j < 1000; j++){
                int r = random.nextInt(this.row); // [0, this.row-1]
                int c = random.nextInt(this.col);
                if(g[r][c] == 1 || g[this.row-1-r][this.col-1-c] == 1){
                    continue;
                }
                if(r == this.row - 2 && c == 1 || r == 1 && c == this.col - 2){
                    continue;
                }
                g[r][c] = g[this.row-1-r][this.col-1-c] = 1;
                break;
            }
        }
        return check_connection(this.row-2,1,1,this.col-2);
    }

    private boolean check_connection(int sx, int sy, int tx, int ty){
        if(sx == tx && sy == ty) return true;
        g[sx][sy] = 1;
        for(int i = 0; i < 4; i++){
            int x = sx + dx[i], y = sy + dy[i];
            if(x >= 0 && x < this.row && y >= 0 && y < this.col && g[x][y] == 0){
                if(check_connection(x,y,tx,ty)){
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }
        g[sx][sy] = 0;
        return false;
    }

    public void createMap(){
        for(int i = 0; i < 1000; i++){
            if(draw()) break;
        }
    }

    private String getInput(Player player){
        // 将当前的局面信息编码成String = 地图 + "#" + "#" + sx + "#" + sy + "#" + 操作 + ......
        Player me, you;
        if(playerA.getId().equals(player.getId())){
            me = playerA;
            you = playerB;
        }else{
            me = playerB;
            you = playerA;
        }
        return getMapString() + "#" +
                me.getSx() + "#" +
                me.getSy() + "#(" +
                me.getStepsToString() + ")#" +
                you.getSx() + "#" +
                you.getSy() + "#(" +
                you.getStepsToString() + ")#";
    }
    private void sendBotCode(Player player){
        if(player.getBotId().equals(-1)) return;
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id",player.getId().toString());
        data.add("bot_code",player.getBotCode());
        data.add("input", getInput(player));
        WebSocketServer.restTemplate.postForObject(addBotUrl, data, String.class);
    }

    private boolean nextStep(){
        // 等待两名玩家的下一步操作
        try {
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }

        sendBotCode(playerA);
        sendBotCode(playerB);

        for(int i = 0; i < 100; i++){
            try{
                Thread.sleep(100);
                lock.lock();
                try{
                    if(nextStepA != null && nextStepB != null){
                        playerA.getSteps().add(nextStepA);
                        playerB.getSteps().add(nextStepB);
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void sendResult(){
        // 向两个client发送输赢结果
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser", loser);
        sendAllMessage(resp.toJSONString());
        saveToDatabase();
    }
    private void sendMove(){
        // 向两个client发送move信息
        lock.lock();
        try{
            JSONObject resp = new JSONObject();
            resp.put("event", "move");
            resp.put("a_direction", nextStepA);
            resp.put("b_direction", nextStepB);
            sendAllMessage(resp.toJSONString());
            nextStepA = nextStepB = null;
        } finally {
            lock.unlock();
        }
    }
    private boolean check_valid(List<Cell> cellsA, List<Cell> cellsB){
        int n = cellsA.size();
        Cell cell = cellsA.get(n-1);
        if(g[cell.x][cell.y] == 1) return false;

        for(int i = 0; i < n-1; i++){
            if(cellsA.get(i).x == cell.x && cellsA.get(i).y == cell.y) return false;
        }

        for(int i = 0; i < n-1; i++){
            if(cellsB.get(i).x == cell.x && cellsB.get(i).y == cell.y) return false;
        }

        return true;
    }

    private void judge(){
        // 判断两名玩家下一步操作是否合法
        List<Cell> cellsA = playerA.getCells();
        List<Cell> cellsB = playerB.getCells();

        boolean validA = check_valid(cellsA, cellsB);
        boolean validB = check_valid(cellsB, cellsA);

        if(!validA || !validB){
            status = "finished";
            if(!validA && !validB){
                loser = "all";
            }else if(!validA){
                loser = "A";
            }else {
                loser = "B";
            }
        }
    }

    private void sendAllMessage(String message){
        if(WebSocketServer.users.get(playerA.getId()) != null) WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        if(WebSocketServer.users.get(playerB.getId()) != null) WebSocketServer.users.get(playerB.getId()).sendMessage(message);

    }

    @Override
    public void run(){
        for(int i = 0; i < row * col * 5; i++){
            if(nextStep()){
                judge();
                if(status.equals("playing")){
                    sendMove();
                }else{
                    sendResult();
                    break;
                }
            } else{
                status = "finished";
                lock.lock();
                try{
                    if(nextStepA == null && nextStepB == null){
                        loser = "all";
                    } else if (nextStepA == null) {
                        loser = "A";
                    } else {
                        loser = "B";
                    }

                } finally {
                  lock.unlock();
                }
                sendResult();
                break;
            }
        }
    }

    // 天梯积分
    private void updateUserRating(Player player, Integer rating){
        User user = WebSocketServer.userMapper.selectById(player.getId());
        user.setBotRating(rating);
        WebSocketServer.userMapper.updateById(user);
    }

    // 存放对局记录
    private void saveToDatabase(){
        Integer ratingA = WebSocketServer.userMapper.selectById(playerA.getId()).getBotRating();
        Integer ratingB = WebSocketServer.userMapper.selectById(playerB.getId()).getBotRating();
        if("A".equals(loser)){
            ratingA -= 2;
            ratingB += 5;
        } else if ("B".equals(loser)){
            ratingA += 5;
            ratingB -= 2;
        }
        updateUserRating(playerA, ratingA);
        updateUserRating(playerB, ratingB);

        Record record = new Record(
            null,
            playerA.getId(),
            playerA.getSx(),
            playerA.getSy(),
            playerB.getId(),
            playerB.getSx(),
            playerB.getSy(),
            playerA.getStepsToString(),
            playerB.getStepsToString(),
            getMapString(),
            loser,
            new Date()
        );
        WebSocketServer.recordMapper.insert(record);
    }

    private String getMapString(){
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                res.append(g[i][j]);
            }
        }
        return res.toString();
    }
}
