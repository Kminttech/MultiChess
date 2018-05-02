package com.google.example.games.tbmpskeleton.gui;

import android.content.Context;

import com.google.example.games.tbmpskeleton.grid.Location;
import com.google.example.games.tbmpskeleton.pieces.ChessPiece;

public class Square extends android.support.v7.widget.AppCompatImageView {
    private boolean isWhite, isSelected;
    private ChessPiece piece;
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

    public ChessPiece getPiece(){
        return this.piece;
    }

    public void addPiece(ChessPiece p){
        this.piece = p;
    }

    public void removePiece(){
        if (this.piece == null){
            return;
        }
        this.piece = null;
        return;
    }

    public void toggleSelected(){
        isSelected = !isSelected;
    }
}
