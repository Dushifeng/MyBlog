package org.dodo.violet.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Table
@Entity
public class SortInfo {

    private int id;

    private String name;

    private Date createTime;

    private List<ArticleInfo> articleInfos = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JoinTable(name = "sort_article",
            joinColumns = {@JoinColumn(name = "sort_id")},
            inverseJoinColumns = {@JoinColumn(name = "article_id")}
            )
    @ManyToMany
    public List<ArticleInfo> getArticleInfos() {
        return articleInfos;
    }

    public void setArticleInfos(List<ArticleInfo> articleInfos) {
        this.articleInfos = articleInfos;
    }

    @Override
    public String toString() {
        return "SortInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", articleInfos=" + articleInfos +
                '}';
    }

    public SortInfo() {
    }
}
