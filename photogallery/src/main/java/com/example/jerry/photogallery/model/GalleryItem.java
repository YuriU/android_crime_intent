package com.example.jerry.photogallery.model;

/**
 * Created by jerry on 26.07.2016.
 */
public class GalleryItem {
    public String Caption;
    public String Id;
    public String URL;
    public String Owner;

    public String getPhotoPageUrl()
    {
        return "https://www.flickr.com/photos/" + Owner + "/" + Id;
    }
    public String toString() {
        return Caption;
    }
}
