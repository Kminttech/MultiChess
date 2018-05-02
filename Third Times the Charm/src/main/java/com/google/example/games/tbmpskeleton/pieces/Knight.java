package com.google.example.games.tbmpskeleton.pieces;


import com.google.example.games.tbmpskeleton.game.ChessGame;
import com.google.example.games.tbmpskeleton.grid.Location;

import java.util.ArrayList;

public class Knight extends ChessPiece {

    public Knight(boolean isWhite, ChessGame game, Location location) {
        super(isWhite, game, location);
    }

    @Override
    public ChessPiece copy() {
        return null;
    }

    @Override
    public ArrayList<Location> getAttackedLocations() {
        ArrayList<Location> locs = new ArrayList();
        for(int dir = 0; dir < 360; dir += 90) {
            Location l = getLocation().getAdjacentLocation(dir).getAdjacentLocation(dir);
            Location l1 = l.getAdjacentLocation(dir + 90);
            Location l2 = l.getAdjacentLocation(dir - 90);

            if(getGrid().isValid(l1)){
                locs.add(l1);
            }
            if(getGrid().isValid(l2)){
                locs.add(l2);
            }
        }
        return locs;
    }
}
