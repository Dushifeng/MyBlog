package org.dodo.violet.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class UpFile {

    private int id;
    private String introduction;
    private Date createTime;
    private String srcPath;
    private Album album;
    private String name;

    private String showPath;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean isPhoto = false;

    @JSONField(serialize=false)
    @JoinColumn
    @ManyToOne(fetch = FetchType.EAGER)
    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


    @JSONField(format="yyyy-MM-dd HH:mm")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public boolean isPhoto() {
        return isPhoto;
    }

    public void setPhoto(boolean photo) {
        isPhoto = photo;
    }

    public String getShowPath() {
        return showPath;
    }

    public void setShowPath(String showPath) {
        this.showPath = showPath;
    }

    public UpFile(String introduction, Date createTime, String srcPath, Album album) {
        this.introduction = introduction;
        this.createTime = createTime;
        this.srcPath = srcPath;
        this.album = album;
    }

    public UpFile(String introduction, Date createTime, String srcPath, Album album, boolean isPhoto) {
        this.introduction = introduction;
        this.createTime = createTime;
        this.srcPath = srcPath;
        this.album = album;
        this.isPhoto = isPhoto;
    }

    public UpFile() {
    }

    @Override
    public String toString() {
        return "UpFile{" +
                "id=" + id +
                ", introduction='" + introduction + '\'' +
                ", createTime=" + createTime +
                ", srcPath='" + srcPath + '\'' +
                '}';
    }
}
