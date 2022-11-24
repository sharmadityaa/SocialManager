package com.example.socialmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.socialmanager.Models.ImBitMap;
import com.example.socialmanager.Models.Posts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostsInfoActivity extends AppCompatActivity {
    EditText editText_title, editText_caption;
    ImageView imageView_save, imageView_upload;
    Posts posts;
    Button button_upload;

    Bitmap bitmapImage;
    int SELECT_PICTURE = 200;
    byte [] imageSources;

    boolean isOldPost = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_info);

        imageView_save = findViewById(R.id.imageView_save);
        editText_title = findViewById(R.id.editText_title);
        editText_caption = findViewById(R.id.editText_caption);

        imageView_upload = findViewById(R.id.imageView_upload);
        button_upload = findViewById(R.id.button_upload);

        bitmapImage = null;

        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                Bundle data = new Bundle();
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                iGallery.putExtras(data);
                startActivityForResult(iGallery,1000);
            }
        });


        posts = new Posts();
        try {
            posts = (Posts) getIntent().getSerializableExtra("existing_post");
            editText_title.setText(posts.getTitle());
            editText_caption.setText(posts.getPosts());

            bitmapImage = ImBitMap.convertByteArray2Im(posts.getImages());
            imageView_upload.setImageBitmap(bitmapImage);


            isOldPost = true;
        } catch (Exception e){
            e.printStackTrace();
        }

        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title =editText_title.getText().toString();
                String caption = editText_caption.getText().toString();

                if(bitmapImage == null){
                    Toast.makeText(PostsInfoActivity.this, "Please Add Image", Toast.LENGTH_SHORT).show();
                } else if(caption.isEmpty()){
                    Toast.makeText(PostsInfoActivity.this, "Please Add Caption", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                if (!isOldPost){
                    posts = new Posts();
                }

                posts.setTitle(title);
                posts.setPosts(caption);
                posts.setDate(formatter.format(date));
                posts.setImages(ImBitMap.convertIm2ByteArray(bitmapImage));

                Intent intent = new Intent();

                intent.putExtra("post",posts);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {

                //bitmapImage = (Bitmap) data.getExtras().get("data");
            //}
              //  if(bitmapImage != null) {
               //     imageView_upload.setImageBitmap(bitmapImage);
                //} else {
                 //   Toast.makeText(this, "Bitmap is Null", Toast.LENGTH_SHORT).show();

                Uri imageUri = data.getData();
                imageView_upload.setImageURI(imageUri);

                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}