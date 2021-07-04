package com.example.galleryaccess.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.galleryaccess.Adapters.MainActivityAdapter;
import com.example.galleryaccess.Model.ImageModel;
import com.example.galleryaccess.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityAdapter.AlbumClickListener {
    int STORAGE_PERMISSION = 101;

    String[] projection = {MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATE_TAKEN, MediaStore.Images.Media.DATA};
    List<ImageModel> images = new ArrayList<>();


    RecyclerView recyclerView;
    private static final String GRID = "GRID";
    private static final String LIST = "LIST";
    MaterialToolbar materialToolbar;

    SharedPreferences sharedPreferences;
    String current_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getPermission()) {
            init();
            getAllImages();
        }
        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.list_view){
                    current_layout=sharedPreferences.getString("layout","");
                        if (current_layout.equals(LIST)) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("layout", GRID);
                            editor.apply();
                            item.setIcon(R.drawable.list_drawable);
                        } else if (current_layout.equals(GRID)) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("layout", LIST);
                            editor.apply();
                            item.setIcon(R.drawable.grid_drawable);
                        }
                    setList();
                    return true;
                }
                return false;
            }
        });
    }

    private boolean getPermission() {
        int ACCESS_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (ACCESS_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
            return false;
        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
            getAllImages();
        }

    }

    void init() {
        recyclerView = findViewById(R.id.recycler_view);
        materialToolbar = findViewById(R.id.material_toolbar);

        sharedPreferences=getSharedPreferences("Layout",MODE_PRIVATE);
    }

    void getAllImages() {
        String BUCKET_ORDER_BY = MediaStore.Images.Media.DATE_MODIFIED + " DESC";

        HashMap<String, ImageModel> hashMap = new HashMap<>();
        final Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, BUCKET_ORDER_BY);
        if (cursor != null && cursor.moveToFirst()) {
            final int col_bucket_id = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
            final int col_bucket_name = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);


            do {
                final String bucket_id = cursor.getString(col_bucket_id);
                final String bucket_name = cursor.getString(col_bucket_name);
                final String imageURL = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                if (hashMap.containsKey(bucket_id) == false) {
                    ImageModel imageModel = new ImageModel(bucket_name, imageURL, bucket_id);
                    hashMap.put(bucket_id, imageModel);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        images.addAll(hashMap.values());
        setList();

    }

    void setList() {
        MainActivityAdapter adapter = new MainActivityAdapter(this, images, this);
        current_layout=sharedPreferences.getString("layout","");
        if(current_layout.equals("")){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("layout",LIST);
            editor.apply();
            current_layout=LIST;
        }
        if(current_layout.equals(LIST)) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
        else if(current_layout.equals(GRID)){
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            recyclerView.setAdapter(adapter);
        }

    }



    @Override
    public void onAlbumClicked(String bucket_id) {
        Intent intent = new Intent(MainActivity.this, Images.class);
        intent.putExtra("bucket_id", bucket_id);
        startActivity(intent);
    }
}