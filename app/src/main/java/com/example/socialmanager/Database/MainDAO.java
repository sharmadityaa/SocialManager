package com.example.socialmanager.Database;
//THis is the DATA ACCESS OBJECT
//Todo: WHAT DOES DAO DO?? HELP
import static androidx.room.OnConflictStrategy.REPLACE;

import android.icu.text.Replaceable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.socialmanager.Models.Posts;

import java.util.List;

@Dao
public interface MainDAO {
    //to insert something in db
    @Insert(onConflict = REPLACE)
    void insert(Posts posts);
    //ig this is for search
    //Todo: WHATS A QUERY???
    @Query("SELECT * FROM posts ORDER BY ID DESC")
    List<Posts> getAll();
    //update db item so when editing it
    @Query("UPDATE posts SET title = :title, posts = :posts WHERE ID = :id")
    void update(int id, String title, String posts);
    //deleting an item, annotation takes care of everything
    @Delete
    void delete(Posts posts);
    //pinning unpinning a post
    @Query("UPDATE posts SET pinned = :pin WHERE ID = :id")
    void pin(int id, boolean pin);
}
