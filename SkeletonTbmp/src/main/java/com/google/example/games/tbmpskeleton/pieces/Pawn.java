package com.google.example.games.tbmpskeleton.pieces;

import com.google.example.games.tbmpskeleton.game.ChessGame;
import com.google.example.games.tbmpskeleton.grid.Location;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

    public Pawn(boolean isWhite, ChessGame game, Location location) {
        super(isWhite, game, location);
    }

    @Override
    public ChessPiece copy() {
        return new Pawn(isWhite(), getGame(), getLocation().copy());
    }

    @Override
    public ArrayList<Location> getAttackedLocations() {
        ArrayList<Location> locs = new ArrayList();
        Location leftAtk = getLocation().getAdjacentLocation(isWhite() ? Location.UPLEFT : Location.DOWNLEFT);
        if(getGrid().isValid(leftAtk)){
            locs.add(leftAtk);
        }
        Location rightAtk = getLocation().getAdjacentLocation(isWhite() ? Location.UPRIGHT: Location.DOWNRIGHT);
        if(getGrid().isValid(rightAtk)){
            locs.add(rightAtk);
        }
        return locs;
    }

    @Override
    public ArrayList<Location> getMoveLocations() {
        ArrayList<Location> locs = new ArrayList();
        for(Location l : getAttackedLocations()){
            if(getGrid().isOccupied(l) && getGrid().get(l).isWhite() != isWhite()){
                locs.add(l);
            }
        }
        int dir = isWhite() ? 0 : 180;
        Location l = getLocation().getAdjacentLocation(dir);
        if(getGrid().isValid(l) && !getGrid().isOccupied(l)){
            locs.add(l);
        }
        return locs;
    }

    @Override
    public int representation() {
        return 6 * (isWhite() ? 1 : -1);
    }
}
