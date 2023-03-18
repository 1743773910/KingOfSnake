package kob.backend.consumer;

import com.alibaba.fastjson.JSONObject;
import kob.backend.mapper.RecordMapper;
import kob.backend.consumer.utils.Game;
import kob.backend.consumer.utils.JwtAuthentication;
import kob.backend.mapper.SubuserMapper;
import kob.backend.mapper.UserMapper;
import kob.backend.pojo.Subuser;
import kob.backend.pojo.User;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.websocket.Session;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    private Session session = null;
    private User user;
    private static CopyOnWriteArrayList<User> matchPool = new CopyOnWriteArrayList<>(); // 匹配池
    public static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>(); // 用户id和webSocket映射
    public static UserMapper userMapper;
    public static RecordMapper recordMapper;
    public Game game = null;
    public static RestTemplate restTemplate;
    private final static String addPlayerUrl = "http://localhost:3001/player/add/";
    private final static String removePlayerUrl = "http://localhost:3001/player/remove/";
    private static SubuserMapper subuserMapper;

    public WebSocketServer() {
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }

    @Autowired
    public void setRecordMapper(RecordMapper recordMapper){
        WebSocketServer.recordMapper = recordMapper;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        WebSocketServer.restTemplate = restTemplate;
    }

    @Autowired
    public void setSubuserMapper(SubuserMapper subuserMapper){
        WebSocketServer.subuserMapper = subuserMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        this.session = session;
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);
        if(this.user != null){
            users.put(userId, this);
            System.out.println("connected");
        }else{
            this.session.close();
        }
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        if(this.user != null){
            users.remove(user.getId());
        }
        System.out.println("disconnected");
    }

    public static void startGame(Integer aId, Integer bId, Integer aBotId, Integer bBotId){
        User a = userMapper.selectById(aId);
        User b = userMapper.selectById(bId);
        Subuser botA = subuserMapper.selectById(aBotId);
        Subuser botB = subuserMapper.selectById(bBotId);

        Game game = new Game(25,16,30,a.getId(),b.getId(),botA,botB);
        game.createMap();
        if(users.get(a.getId()) != null) users.get(a.getId()).game = game;
        if(users.get(b.getId()) != null) users.get(b.getId()).game = game;
        game.start();


        JSONObject respGame = new JSONObject();
        respGame.put("a_id", game.getPlayerA().getId());
        respGame.put("a_sx", game.getPlayerA().getSx());
        respGame.put("a_sy", game.getPlayerA().getSy());
        respGame.put("b_id", game.getPlayerB().getId());
        respGame.put("b_sx", game.getPlayerB().getSx());
        respGame.put("b_sy", game.getPlayerB().getSy());
        respGame.put("map", game.getG());


        JSONObject respA = new JSONObject();
        respA.put("event", "start-matching");
        respA.put("opponent_username", b.getBotName());
        respA.put("opponent_photo", b.getPhoto());
        respA.put("game", respGame);
        if(users.get(a.getId()) != null) users.get(a.getId()).sendMessage(respA.toJSONString());

        JSONObject respB = new JSONObject();
        respB.put("event", "start-matching");
        respB.put("opponent_username", a.getBotName());
        respB.put("opponent_photo", a.getPhoto());
        respB.put("game", respGame);
        if(users.get(b.getId()) != null) users.get(b.getId()).sendMessage(respB.toJSONString());
    }

    private void startMatching(Integer botId){
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id",this.user.getId().toString());
        data.add("rating",this.user.getBotRating().toString());
        data.add("bot_id",botId.toString());
        restTemplate.postForObject(addPlayerUrl, data, String.class);
    }

    private void stopMatching(){
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id",this.user.getId().toString());
        restTemplate.postForObject(removePlayerUrl, data, String.class);
    }

    private void move(int direction){
        if (game.getPlayerA().getId().equals(user.getId())){
            if(game.getPlayerA().getBotId().equals(-1)){
                // 玩家操作
                game.setNextStepA(direction);
            }
        } else if (game.getPlayerB().getId().equals(user.getId())) {
            if(game.getPlayerB().getBotId().equals(-1)){
                game.setNextStepB(direction);
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 前端发送,后端接收
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if("start-matching".equals(event)){
            startMatching(data.getInteger("subbot_id"));
        } else if ("stop-matching".equals(event)) {
            stopMatching();
        } else if ("move".equals(event)) {
            move(data.getInteger("direction"));
        }
    }

    // 从后端发送消息至前端
    public void sendMessage(String message){
        synchronized (this.session){
            try{
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}
