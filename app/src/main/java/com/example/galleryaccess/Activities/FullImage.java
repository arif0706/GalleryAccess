package com.example.galleryaccess.Activities;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.galleryaccess.Adapters.FullImageAdapter;
import com.example.galleryaccess.Model.ImageModel;
import com.example.galleryaccess.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FullImage extends AppCompatActivity {

    ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_full_image);
        viewPager2=findViewById(R.id.view_pager2);

        int position=getIntent().getIntExtra("position",0);
        Type list=new TypeToken<List<ImageModel>>(){}.getType();
        List<ImageModel> list1=new Gson().fromJson(getIntent().getStringExtra("imageList"),list);

        FullImageAdapter adapter=new FullImageAdapter(this,list1);

        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(adapter);
        viewPager2.setCurrentItem(position,false);

    }



}