package com.example.galleryaccess.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.galleryaccess.Model.ImageModel;
import com.example.galleryaccess.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class FullImageAdapter extends RecyclerView.Adapter<FullImageAdapter.ImageViewHolder>{
    Context context;
    List<ImageModel> imageModelList;

    View view;
    public FullImageAdapter(Context context, List<ImageModel> imageModelList){
        this.context=context;
        this.imageModelList=imageModelList;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.viewpager2_full_image,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        try {
            Glide.with(context)
                    .load(imageModelList.get(position).getImageURL())
                    .into(holder.photoView);
        }
        catch (RuntimeException e){
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return imageModelList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        PhotoView photoView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            photoView=itemView.findViewById(R.id.photo_view);
        }
    }
}
