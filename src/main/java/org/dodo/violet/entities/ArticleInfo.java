package org.dodo.violet.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table
@Entity
public class ArticleInfo {

    private int id;
    private String title;
    private Date createTime;
    private int views;
    private String introduction;

    @Transient
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    private ArticleContent articleContent;


    @OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn
    public ArticleContent getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(ArticleContent articleContent) {
        this.articleContent = articleContent;
    }

    private List<SortInfo> sortInfos;

    private List<Comment> comments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }



    @ManyToMany(mappedBy = "articleInfos",fetch = FetchType.EAGER)
    public List<SortInfo> getSortInfos() {
        return sortInfos;
    }

    public void setSortInfos(List<SortInfo> sortInfos) {
        this.sortInfos = sortInfos;
    }

    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "articleInfo")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public ArticleInfo() {
    }

    public ArticleInfo(String title, Date createTime, int views, ArticleContent articleContent, List<SortInfo> sortInfos) {
        this.title = title;
        this.createTime = createTime;
        this.views = views;
        this.articleContent = articleContent;
        this.sortInfos = sortInfos;
    }
}
