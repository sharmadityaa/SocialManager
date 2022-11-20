package com.example.socialmanager;

import androidx.cardview.widget.CardView;

import com.example.socialmanager.Models.Posts;

public interface PostsClickListener {
    void onClick(Posts posts);
    void onLongClick(Posts posts, CardView cardView);
}
