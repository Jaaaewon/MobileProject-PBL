package com.example.pbl2shoppingmall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProductViewHolder> {

    private ArrayList<Product> Products = new ArrayList<>();
    private Context mcontext;
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        holder.onBind(Products.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(v.getContext(), ProductActivity.class);
                Product input = Products.get(position);
                nextIntent.putExtra("product", input);
                v.getContext().startActivity(nextIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return Products.size();
    }

    public void setContext(Context mcontext){this.mcontext = mcontext;}

    void addProduct(Product Product) {
        Products.add(Product);
    }
    class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView productName, productPrice;
        private ImageView imageView;
        ProductViewHolder(View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void onBind(Product product) {
            productName.setText(product.getName());
            productPrice.setText(product.getPrice());
            imageTake(product.getPath());
        }
        public void imageTake(String path){
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference().child(path);

            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // Glide 이용하여 이미지뷰에 로딩
                        GlideApp.with(imageView.getContext())
                                .load(task.getResult())
                                .override(1024, 980)
                                .into(imageView);
                    } else { }
                }
            });
        }
    }
}