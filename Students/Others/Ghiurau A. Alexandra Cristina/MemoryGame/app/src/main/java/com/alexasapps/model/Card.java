package com.alexasapps.model;


import android.widget.ImageView;

public class Card {

    private int id;
    private boolean matched = false;
    private boolean selected = false;
    private ImageView frontImage;
    private ImageView backImage;

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public ImageView getFrontImage() {

        return frontImage;
    }

    public void setFrontImage(ImageView frontImage) {
        this.frontImage = frontImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageView getBackImage() {
        return backImage;
    }

    public void setBackImage(ImageView backImage) {
        this.backImage = backImage;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
