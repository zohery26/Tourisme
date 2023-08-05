package com.itu.tourisme;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class AfficheImageUrl {
    public void loadImage(String url, ImageView img) {
        Picasso.get()
                .load("https://madatourismeitu.alwaysdata.net/images"+url)

                .into(img);
    }
}
