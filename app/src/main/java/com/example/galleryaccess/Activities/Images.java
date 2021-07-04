package com.example.galleryaccess.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;

import com.example.galleryaccess.Adapters.ImagesAdapter;
import com.example.galleryaccess.Model.ImageModel;
import com.example.galleryaccess.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Images extends AppCompatActivity implements ImagesAdapter.ImageClickListener {

    String bucket_id;
    String[] projection={MediaStore.Images.Media.DATE_TAKEN,MediaStore.Images.Media.DATA,MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.Media.BUCKET_ID};
    List<ImageModel> imageModelList;

    RecyclerView recyclerView;

    MaterialToolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        init();

        bucket_id=getIntent().getStringExtra("bucket_id");

        String BUCKET_ORDER_BY = MediaStore.Images.Media.DATE_MODIFIED + " DESC";
        String SEARCH_QUERY=MediaStore.Images.Media.BUCKET_ID+" like "+bucket_id;
        Cursor cursor=getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,projection,SEARCH_QUERY,null,BUCKET_ORDER_BY);
        cursor.moveToFirst();
        String bucket_name=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
        toolbar.setTitle(bucket_name);
        cursor.moveToPrevious();
        while(cursor.moveToNext()){

            String imageURL=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            ImageModel imageModel=new ImageModel(bucket_name,imageURL,bucket_id);
            imageModelList.add(imageModel);
            setList();
        }
        cursor.close();

    }
    void init(){
        imageModelList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler_view);

        toolbar=findViewById(R.id.material_toolbar);
    }
    void setList(){
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        ImagesAdapter imagesAdapter=new ImagesAdapter(this,imageModelList,this);
        recyclerView.setAdapter(imagesAdapter);
    }
    @Override
    public void onImageClicked(List<ImageModel> models, String imageURL, int position, View itemView) {
        Intent intent=new Intent(this,FullImage.class);;
        intent.putExtra("imageList",new Gson().toJson(models));
        intent.putExtra("imageURL",imageURL);
        intent.putExtra("position",position);
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Images.this,itemView,"image");
        startActivity(intent,options.toBundle());
    }





}