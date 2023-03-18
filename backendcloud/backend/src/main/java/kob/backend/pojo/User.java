package kob.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String botName;
    private String botPwd;
    private Integer botRating;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getBotPwd() {
        return botPwd;
    }

    public void setBotPwd(String botPwd) {
        this.botPwd = botPwd;
    }

    public Integer getBotRating() {
        return botRating;
    }

    public void setBotRating(Integer botRating) {
        this.botRating = botRating;
    }

    public User(Integer id, String botName, String botPwd, Integer botRating, String photo) {
        this.id = id;
        this.botName = botName;
        this.botPwd = botPwd;
        this.botRating = botRating;
        this.photo = photo;
    }


    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", botName='" + botName + '\'' +
                ", botPwd='" + botPwd + '\'' +
                ", botRating=" + botRating +
                '}';
    }
}
