package com.example.socialmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.socialmanager.Adapters.PostsListAdapter;
import com.example.socialmanager.Database.RoomDB;
import com.example.socialmanager.Models.Posts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    PostsListAdapter postsListAdapter;
    List<Posts> posts = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;
    SearchView searchView_home;

    Posts selectedPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);
        searchView_home = findViewById(R.id.searchView_home);

        database = RoomDB.getInstance(this);
        posts = database.mainDAO().getAll();

        updateRecycler(posts);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PostsInfoActivity.class);
                //we will pass 233 for adding, 234 for editing
                startActivityForResult(intent, 233);

            }
        });

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false;}

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<Posts> filteredList = new ArrayList<>();
        for(Posts singlePost: posts){
            if(singlePost.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singlePost.getPosts().toLowerCase().contains(newText.toLowerCase()))
            {
                filteredList.add(singlePost);
            }
        }

        postsListAdapter.filterList(filteredList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==233){
            if(resultCode == Activity.RESULT_OK){
                Posts new_posts = (Posts) data.getSerializableExtra("post");
                database.mainDAO().insert(new_posts);
                posts.clear();
                posts.addAll(database.mainDAO().getAll());
                postsListAdapter.notifyDataSetChanged();
            }
        }
        else if(requestCode==234){
            if(resultCode == Activity.RESULT_OK){
                Posts new_posts = (Posts) data.getSerializableExtra(("post"));
                database.mainDAO().update(new_posts.getID(), new_posts.getTitle(), new_posts.getPosts());
                posts.clear();
                posts.addAll(database.mainDAO().getAll());
                postsListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Posts> posts) {
        recyclerView.setHasFixedSize(true);
        postsListAdapter = new PostsListAdapter(MainActivity.this,
                posts,
                postsClickListener);
        recyclerView.setAdapter(postsListAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
    }

    private final PostsClickListener postsClickListener = new PostsClickListener() {
        @Override
        public void onClick(Posts posts) {
            Intent intent = new Intent(MainActivity.this, PostsInfoActivity.class);
            intent.putExtra("existing_post", posts);
            //new data code is 233, and editing is 234,
            startActivityForResult(intent, 234);
        }

        @Override
        public void onLongClick(Posts posts, CardView cardView) {

            selectedPost = new Posts();
            selectedPost = posts;
            showPopup(cardView);

        }
    };

    private void showPopup(CardView cardView) {

        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_popup);
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pin:
                if(selectedPost.isPinned()){
                    database.mainDAO().pin(selectedPost.getID(), false);
                    Toast.makeText(MainActivity.this, "Unpinned", Toast.LENGTH_SHORT).show();

                }
                else{
                    database.mainDAO().pin(selectedPost.getID(), true);
                    Toast.makeText(MainActivity.this, "Pinned", Toast.LENGTH_SHORT).show();
                }

                posts.clear();
                posts.addAll(database.mainDAO().getAll());
                postsListAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete:
                database.mainDAO().delete(selectedPost);
                posts.remove(selectedPost);
                postsListAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Post Deleted", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return false;

        }
    }
}