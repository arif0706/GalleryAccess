package com.example.galleryaccess.Model;

public class ImageModel {
    public String Bucket_Name;
    public String imageURL;
    public String Bucket_id;

    public ImageModel(String Bucket_Name, String imageURL,String bucket_id) {
        this.Bucket_Name = Bucket_Name;
        this.imageURL = imageURL;
        this.Bucket_id=bucket_id;
    }

    public String getBucket_id() {
        return Bucket_id;
    }

    public void setBucket_id(String bucket_id) {
        Bucket_id = bucket_id;
    }

    public String getBucket_Name() {
        return Bucket_Name;
    }

    public void setBucket_Name(String bucket_Name) {
        this.Bucket_Name = bucket_Name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
