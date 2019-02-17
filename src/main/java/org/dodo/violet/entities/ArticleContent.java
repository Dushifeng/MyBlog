package org.dodo.violet.entities;

import javax.persistence.*;

@Table
@Entity
public class ArticleContent {


    private int id;

    private String content;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleContent{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

    public ArticleContent(String content) {
        this.content = content;
    }

    public ArticleContent() {
    }
}
