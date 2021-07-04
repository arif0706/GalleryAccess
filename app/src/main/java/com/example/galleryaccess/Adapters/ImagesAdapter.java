package com.example.galleryaccess.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.galleryaccess.Model.ImageModel;
import com.example.galleryaccess.R;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {
    View view;
    Context context;
    List<ImageModel> imageList;
    ImageClickListener imageClickListener;
    public ImagesAdapter(Context context, List<ImageModel> imageList, ImageClickListener imageClickListener) {
        this.context = context;
        this.imageList=imageList;
        this.imageClickListener = imageClickListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.image_card2,parent,false);
        return new ImageViewHolder(view, imageClickListener);
    }

    @Override
    public void onBindViewHolder(ImagesAdapter.ImageViewHolder holder, int position) {
        Glide.with(context)
                .load(imageList.get(position).getImageURL())

                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .dontAnimate()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
         ImageView imageView;
         ImageClickListener imageClickListener;
        public ImageViewHolder(View itemView, ImageClickListener imageClickListener) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view);

            this.imageClickListener = imageClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageClickListener.onImageClicked(imageList,imageList.get(getAdapterPosition()).getImageURL(),getAdapterPosition(),itemView);
                }
            });

        }
    }
    public interface ImageClickListener {
        void onImageClicked(List<ImageModel> imageModelList, String imageURL,int position,View view);

    }
}
