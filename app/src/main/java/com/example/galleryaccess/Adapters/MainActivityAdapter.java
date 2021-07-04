package com.example.galleryaccess.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.galleryaccess.Model.ImageModel;
import com.example.galleryaccess.R;

import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ImageViewHolder> {
    View view;
    Context context;
    List<ImageModel> imageList;
    AlbumClickListener albumClickListener;
    public MainActivityAdapter(Context context, List<ImageModel> imageList, AlbumClickListener albumClickListener) {
        this.context = context;
        this.imageList=imageList;
        this.albumClickListener=albumClickListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.image_card,parent,false);
        return new ImageViewHolder(view,albumClickListener);
    }

    @Override
    public void onBindViewHolder(MainActivityAdapter.ImageViewHolder holder, int position) {
        Glide.with(context)
                .load(imageList.get(position).getImageURL())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .dontAnimate()
                .into(holder.imageView);
        holder.category_name.setText(imageList.get(position).getBucket_Name());

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
         ImageView imageView;
         TextView category_name;
         AlbumClickListener albumClickListener;
        public ImageViewHolder(View itemView,AlbumClickListener albumClickListener) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view);
            category_name=itemView.findViewById(R.id.category_name);

            this.albumClickListener=albumClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    albumClickListener.onAlbumClicked(imageList.get(getAdapterPosition()).getBucket_id());
                }
            });
        }
    }
    public interface AlbumClickListener{
         void onAlbumClicked(String bucket_id);
    }
}
