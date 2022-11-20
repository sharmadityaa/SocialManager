package com.example.socialmanager.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//annotation entity since it will be shown as table in the app
@Entity(tableName = "posts")
//serializable converts to bytestream so it can be stored and then unserialised again,helps in
//storing something and getting it back
public class Posts implements Serializable {
    //primary key is required for DB cuz duh
    @PrimaryKey(autoGenerate = true)
    int ID = 0;
    //this is the title of the post, we are annotating that in the db, the column name is title
    @ColumnInfo(name = "title")
    String title = "";
    //the post itself
    @ColumnInfo(name = "posts")
    String posts = "";
    //date time when post was created
    @ColumnInfo(name = "date")
    String date = "";
    //post can be pinned unpinned
    @ColumnInfo(name = "pinned")
    boolean pinned = false;

    //image's column
    @ColumnInfo(name = "images",typeAffinity = ColumnInfo.BLOB)
    byte[] images;

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    //variables were created but we need to access them and that is done by getter
    // and setter methods, they were made using generate feature (android studio ZINDABAAD!!)
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }


}
