package com.google.example.games.tbmpskeleton.pieces;


import com.google.example.games.tbmpskeleton.game.ChessGame;
import com.google.example.games.tbmpskeleton.grid.Location;

import java.util.ArrayList;

public class Rook extends ChessPiece {

    public Rook(boolean isWhite, ChessGame game, Location location) {
        super(isWhite, game, location);
    }

    @Override
    public ChessPiece copy() {
        return  new Rook(isWhite(), getGame(), getLocation().copy());
    }

    @Override
    public ArrayList<Location> getAttackedLocations() {
        ArrayList<Location> locs = new ArrayList();
        for(int dir = 0; dir < 360; dir += 90) {
            Location l = getLocation().getAdjacentLocation(dir);
            while(getGrid().isValid(l)) {
                locs.add(l);
                if(getGrid().isOccupied(l))
                    break;
                l = l.getAdjacentLocation(dir);
            }
        }
        return locs;
    }
}
