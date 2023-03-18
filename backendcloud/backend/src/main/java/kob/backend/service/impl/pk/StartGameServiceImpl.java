package kob.backend.service.impl.pk;

import kob.backend.consumer.WebSocketServer;
import kob.backend.service.pk.StartGameService;
import org.springframework.stereotype.Service;

@Service
public class StartGameServiceImpl implements StartGameService {

    @Override
    public String startGame(Integer aId, Integer bId, Integer aBotId, Integer bBotId) {
        WebSocketServer.startGame(aId, bId, aBotId, bBotId);
        return "start game success";
    }

}
