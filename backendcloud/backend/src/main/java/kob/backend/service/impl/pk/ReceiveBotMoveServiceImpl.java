package kob.backend.service.impl.pk;


import kob.backend.consumer.WebSocketServer;
import kob.backend.consumer.utils.Game;
import kob.backend.service.pk.ReceiveBotMoveService;
import org.springframework.stereotype.Service;

@Service
public class ReceiveBotMoveServiceImpl implements ReceiveBotMoveService {
    @Override
    public String receiveBotMove(Integer userId, Integer direction) {
        if(WebSocketServer.users.get(userId) != null){
            Game game = WebSocketServer.users.get(userId).game;
            if(game != null){
                // AI操作
                if(game.getPlayerA().getId().equals(userId)) game.setNextStepA(direction);
                else game.setNextStepB(direction);
            }
        }
        return "receive bot move success";
    }
}
