package com.google.example.games.tbmpskeleton.gui;

import android.content.Context;

import com.google.example.games.tbmpskeleton.grid.Location;

public class Square extends android.support.v7.widget.AppCompatImageView {
    private boolean isWhite, isSelected;
    private Location location;

    public Square(Context context, Boolean isWhite, Location loc) {
        super(context);
        this.isWhite = isWhite;
        this.location = loc;
        this.setClickable(true);
    }

    public Location getLocation(){
        return location;
    }

    public boolean isWhite(){
        return this.isWhite;
    }

    public boolean isSelected(){
        return this.isSelected;
    }

    public void toggleSelected(){
        isSelected = !isSelected;
    }
}
