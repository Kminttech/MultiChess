package com.google.example.games.tbmpskeleton.pieces;


import com.google.example.games.tbmpskeleton.game.ChessGame;
import com.google.example.games.tbmpskeleton.grid.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class King extends ChessPiece {

    public King(boolean isWhite, ChessGame game, Location location) {
        super(isWhite, game, location);
    }

    @Override
    public ChessPiece copy() {
        return new King(isWhite(), getGame(), getLocation().copy());
    }

    @Override
    public ArrayList<Location> getAttackedLocations() {
        ArrayList<Location> locs = new ArrayList(8);
        for(int dir = 0; dir < 360; dir += 45) {
            Location l = getLocation().getAdjacentLocation(dir);
            if(getGrid().isValid(l)) {
                locs.add(l);
            }
        }
        return locs;
    }

    /**
     * @return An ArrayList containing all the valid locations on this piece's
     * grid that it can move to.
     */
    @Override
    protected ArrayList<Location> getMoveLocations(){
        ArrayList<Location> locs = getAttackedLocations();
        HashSet<Location> invalid = isWhite() ? getGame().getLocsControlledByBlack() : getGame().getLocsControlledByWhite();
        Iterator<Location> i = locs.iterator();
        while(i.hasNext()) {
            Location loc = i.next();
            if(invalid.contains(loc) || (getGrid().isOccupied(loc) && isSameColor(getGrid().get(loc))))
                i.remove();
        }
        return locs;
    }
}
