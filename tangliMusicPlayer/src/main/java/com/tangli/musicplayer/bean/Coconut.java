package com.tangli.musicplayer.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity
public class Coconut {
    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true)
    private int cover;
    private String name;
    private String artist;
    private int duration;
    private int resId;
    private boolean isFavourite;
    @Generated(hash = 547738610)
    public Coconut(Long id, int cover, String name, String artist, int duration,
            int resId, boolean isFavourite) {
        this.id = id;
        this.cover = cover;
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.resId = resId;
        this.isFavourite = isFavourite;
    }
    @Generated(hash = 77985698)
    public Coconut() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getCover() {
        return this.cover;
    }
    public void setCover(int cover) {
        this.cover = cover;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getArtist() {
        return this.artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public int getDuration() {
        return this.duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getResId() {
        return this.resId;
    }
    public void setResId(int resId) {
        this.resId = resId;
    }
    public boolean getIsFavourite() {
        return this.isFavourite;
    }
    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
    
   
}