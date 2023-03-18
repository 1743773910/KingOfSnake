package kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;
    private Integer sx;
    private Integer sy;
    private List<Integer> steps;  // 每一步的方向
    private Integer botId; // -1表示玩家操作，否则为AI
    private String botCode;

    private boolean check_tail_increasing(int step){
        // 检测当前回合蛇是否变长
        if(step <= 10) return true;
        return step % 3 == 1;
    }

    public List<Cell> getCells(){
        // 返回蛇的每个Cell，用于处理下一步操作是否撞墙等
        List<Cell> res = new ArrayList<Cell>();
        int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};
        int x = sx, y = sy;
        int step = 0;
        res.add(new Cell(x,y));
        for(int d : steps){
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x,y));
            if(!check_tail_increasing(++step)){
                // 蛇尾不增加
                res.remove(0);
            }
        }
        return res;
    }

    public String getStepsToString(){
        StringBuilder res = new StringBuilder();
        for(int d : steps){
            res.append(d);
        }
        return res.toString();
    }
}
