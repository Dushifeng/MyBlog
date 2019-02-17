package org.dodo.violet.entities;

import javax.persistence.*;
import java.util.Date;


@Table
@Entity
public class Comment {


    private int id;
    private String username;
    private String content;
    private Date createTime;
    private ArticleInfo articleInfo;

    @JoinColumn
    @ManyToOne
    public ArticleInfo getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(ArticleInfo articleInfo) {
        this.articleInfo = articleInfo;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Comment() {
    }

    public Comment(String username, String content, Date createTime, ArticleInfo articleInfo) {
        this.username = username;
        this.content = content;
        this.createTime = createTime;
        this.articleInfo = articleInfo;
    }
}
