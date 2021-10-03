package com.example.topic.RoomInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import com.example.topic.URL.Url;

import java.util.ArrayList;
import java.util.List;

class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> products =new ArrayList<>();
    Url url = new Url();

    public RecylerAdapter(Context context, List<Product> products){
        this.mContext = context;
        this.products = products;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        private TextView mAddress,mName,mHouseType,mPrice;
        private ImageView mImageView;
        private LinearLayout mContainer;
        private ConstraintLayout mView;


        public MyViewHolder(View view) {
            super(view);

            mAddress=view.findViewById(R.id.cell_addressText);
            mName=view.findViewById(R.id.cell_nameText);
            mImageView=view.findViewById(R.id.cell_image);
            mHouseType= view.findViewById(R.id.cell_houseTypeText);
            mPrice= view.findViewById(R.id.cell_priceText);
            mView=view.findViewById(R.id.cellView);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cell_product,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Product product = products.get(position);

        if(!product.getName().equals("null")){
            holder.mView.setVisibility(View.VISIBLE);
            holder.mAddress.setText(product.getAddress());
            holder.mName.setText(product.getName());
            holder.mHouseType.setText(product.getHouseType());
            holder.mPrice.setText(product.getPrice());
            Glide.with(mContext)
                    .load(url.urlGetImage+product.getImage())
                    .into(holder.mImageView);
        }else{
            holder.mView.setVisibility(View.GONE);
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Product_Detail.class);

                intent.putExtra("id",product.getId());
                intent.putExtra("image",product.getImage());
                intent.putExtra("address",product.getAddress());
                intent.putExtra("name",product.getName());
                intent.putExtra("phone",product.getPhone());
                intent.putExtra("houseType",product.getHouseType());
                intent.putExtra("price",product.getPrice());
                intent.putExtra("status",product.getStatus());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}
