package org.dodo.violet.entities;


import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class Album {
    private int id;
    private String name;
    private String introduction;
    private Date createTime;
    private List<UpFile> photos = new ArrayList<>();
    private String coverPath;

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @OneToMany(mappedBy = "album",fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    public List<UpFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<UpFile> photos) {
        this.photos = photos;
    }

    public Album(String name, String introduction, Date createTime) {
        this.name = name;
        this.introduction = introduction;
        this.createTime = createTime;
    }


    public Album() {
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", createTime=" + createTime +
                ", photos=" + photos +
                '}';
    }
}
