package com.codesystem.botrunningsystem.Service.impl.utlis;

import com.codesystem.botrunningsystem.config.CompilerUtil;
import com.codesystem.botrunningsystem.utlis.BotInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@Component
public class Consumer extends Thread{
    private Bot bot;
    private static RestTemplate restTemplate;
    private static final String receiveBotMoveUrl = "http://localhost:3000/api/pk/receive/bot/move/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        Consumer.restTemplate = restTemplate;
    }

    public void startTimeout(long timeout, Bot bot){
        this.bot = bot;
        this.start();
        try {
            this.join(timeout); // 等待timeout秒，即代码最多执行多久
            this.interrupt();  // 中断当前线程
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String addUid(String code, String uid){
        int k = code.indexOf(" implements com.kob.botrunningsystem.utlis.BotInterface");
        return code.substring(0,k) + uid + code.substring(k);
    }

    @Override
    public void run(){
        // 动态执行一个代码
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0,8);
        String code = addUid(bot.getBotCode(), uid);
        //创建这个类
        createFile("Bot" + uid, code);
        try {
            BotInterface botInterface = CompilerUtil.generateClass("Bot" + uid, "com.kob.botrunningsystem.utils", code);
//            File file = new File("input.txt");
//            try(PrintWriter fout = new PrintWriter(file)){
//                fout.println(bot.getInput());
//                System.out.println("botInput = " + bot.getInput());
//                fout.flush();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            Integer direction = botInterface.nextMove(bot.getInput());

            MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
            data.add("direction", direction.toString());
            data.add("user_id", bot.getUserId().toString());
            deleteFile("Bot" + uid);
            restTemplate.postForObject(receiveBotMoveUrl, data, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void deleteFile(String name) {
        File file = new File("E:\\maven\\apache-maven-3.9.0-bin\\apache-maven-3.9.0\\mavenWorkSpace\\KingOfBot\\kob\\backendcloud\\botrunningsystem\\src\\main\\java\\com\\kob\\botrunningsystem\\utlis/" + name + ".java");
        if (file.exists()) {
            file.delete();
        }
    }

    private void createFile(String name, String code) {
        try (FileWriter file = new FileWriter("E:\\maven\\apache-maven-3.9.0-bin\\apache-maven-3.9.0\\mavenWorkSpace\\KingOfBot\\kob\\backendcloud\\botrunningsystem\\src\\main\\java\\com\\kob\\botrunningsystem\\utlis/" + name + ".java")) {
            file.write("package com.kob.botrunningsystem.utlis;" + "\n" + code);
            file.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}


