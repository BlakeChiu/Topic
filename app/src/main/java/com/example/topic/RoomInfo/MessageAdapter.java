package com.example.topic.RoomInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.topic.MainActivity;
import com.example.topic.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<MessagePost> post =new ArrayList<>();

    public MessageAdapter(Context context, ArrayList<MessagePost> post){
        this.mContext = context;
        this.post = post;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        private  TextView name,data,time;
        private  TextView boardLine;

        public MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.messageName_text);
            data = view.findViewById(R.id.messageData_text);
            time = view.findViewById(R.id.messageTime_text);
            boardLine = view.findViewById(R.id.boardLine);

        }
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cell_message,parent,false);

        return new MessageAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        final MessagePost msg = post.get(position);

        holder.name.setText(msg.getMsg_name());
        holder.data.setText(msg.getMsg_data());
        holder.time.setText(msg.getMsg_time());

        if(position == post.size()-1){
            holder.boardLine.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

}