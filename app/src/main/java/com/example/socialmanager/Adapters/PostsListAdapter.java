package com.example.socialmanager.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmanager.Models.Posts;
import com.example.socialmanager.PostsClickListener;
import com.example.socialmanager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PostsListAdapter extends RecyclerView.Adapter<PostsViewHolder>{
    //TODO: WHAT IS CONTEXT?
    Context context;
    //list of posts
    List<Posts> list;
    //when you click on post, how will code know what to do? uske liye we created
    // another interface post click listener with 2 methods, onClick and onLongClick
    PostsClickListener listener;

    // made constructor using generate
    public PostsListAdapter(Context context, List<Posts> list, PostsClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostsViewHolder(LayoutInflater.from(context).inflate(R.layout.posts_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {

        //for the title, set the text, get position from list and get the title
        holder.textView_title.setText(list.get(position).getTitle());
        //scrolling effect ke liye
        holder.textView_title.setSelected(true);

        //toh posts is the actual post but xml mein textview below title ko caption naam diya
        holder.textView_caption.setText(list.get(position).getPosts());

        //for date and add scrolling effect
        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);

        //For Pin ka items
        //boolean hai, toh we will see if its true or no
        //if true then show ic_pin, if not then don't show ic_pin
        //what does ic mean in res folders mean btw? you reckon we should to_do this too?

        /*
         if (list.get(position).isPinned()){
            //toh from image view pin, set the img resource to ic_pin which is in Resources (R)
            // wala folder ke andar drawable ka folder.
            holder.imageView_pin.setImageResource(R.drawable.ic_push_pin);
        }
        else {
            holder.imageView_pin.setImageResource(0);
        } */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                holder.posts_container.setCardBackgroundColor(0xffA5C9CA);
        }


        holder.posts_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));

            }
        });

        holder.posts_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.posts_container);
                return true;
            }
        });

    }

    /*
    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.Tertiary);
        colorCode.add(R.color.Tertiary2);
        colorCode.add(R.color.Tertiary3);
        colorCode.add(R.color.Tertiary4);
        colorCode.add(R.color.Tertiary5);
        colorCode.add(R.color.Tertiary6);
        colorCode.add(R.color.Tertiary7);
        colorCode.add(R.color.Tertiary8);
        colorCode.add(R.color.Tertiary9);
        colorCode.add(R.color.Tertiary10);
        colorCode.add(R.color.Tertiary11);
        colorCode.add(R.color.Tertiary12);
        colorCode.add(R.color.Tertiary13);
        colorCode.add(R.color.Tertiary14);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Posts> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
}
//basically, from xml file assign the views and find them to a variable in java file
class PostsViewHolder extends RecyclerView.ViewHolder {

    CardView posts_container;
    TextView textView_title, textView_caption, textView_date;
    ImageView imageView_pin, imageView_upload;

    public PostsViewHolder(@NonNull View itemView) {
        super(itemView);

        posts_container = itemView.findViewById(R.id.posts_container);

        textView_title = itemView.findViewById(R.id.textView_title);
        textView_caption = itemView.findViewById(R.id.textView_caption);
        textView_date = itemView.findViewById(R.id.textView_date);

        imageView_pin = itemView.findViewById(R.id.imageView_pin);

        imageView_upload = itemView.findViewById(R.id.imageView_upload);
    }
}