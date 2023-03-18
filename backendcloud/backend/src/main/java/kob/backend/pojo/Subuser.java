package kob.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Subuser {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String subbotName;
    private String description;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date modifyTime;

    public Subuser(Integer id, Integer userId, String subbotName, String description, String content, Date createTime, Date modifyTime) {
        this.id = id;
        this.userId = userId;
        this.subbotName = subbotName;
        this.description = description;
        this.content = content;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }

    public Subuser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSubbotName() {
        return subbotName;
    }

    public void setSubbotName(String subbotName) {
        this.subbotName = subbotName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "Subuser{" +
                "id=" + id +
                ", userId=" + userId +
                ", subbotName='" + subbotName + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
