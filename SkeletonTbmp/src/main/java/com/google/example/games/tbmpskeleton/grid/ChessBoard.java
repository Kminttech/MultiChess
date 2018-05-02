package com.google.example.games.tbmpskeleton.grid;


import com.google.example.games.tbmpskeleton.pieces.ChessPiece;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents an 8x8 chessboard, made of locations that hold Chess pieces.
 * @author Kevin
 */
public class ChessBoard implements Grid<ChessPiece> {
    private HashMap<Location, ChessPiece> map;
    private boolean inverted;

    /**
     * Create a new empty Chessoard.
     */
    public ChessBoard() {
        super();
        map = new HashMap<Location, ChessPiece>(32);
    }

    public ChessBoard(Set<Map.Entry<Location, ChessPiece>> entrySet) {
        super();
        map = new HashMap(32);
        for(Map.Entry<Location, ChessPiece> e : entrySet) {
            map.put(e.getKey().copy(), e.getValue().copy());
        }
    }

    public Set<Map.Entry<Location, ChessPiece>> getEntrySet() { return map.entrySet(); }

    @Override
    public void invert() {
        inverted = !inverted;
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }

    @Override
    public ChessPiece get(Location loc) {
        return map.get(loc);
    }

    @Override
    public int getCols() {
        return 8;
    }

    @Override
    public int getRows() {
        return 8;
    }

    @Override
    public boolean isOccupied(Location loc) {
        return map.containsKey(loc);
    }

    @Override
    public boolean isValid(Location loc) {
        return (loc.getCol() >= 0 && loc.getCol() < 8) && (loc.getRow() >= 0 && loc.getRow() < 8);
    }

    @Override
    public ChessPiece put(Location loc, ChessPiece chessPiece) {
        if(chessPiece == null) {
            throw new IllegalArgumentException("ChessPiece cannot be null.");
        }
        if(chessPiece.getLocation() != null && !chessPiece.getLocation().equals(loc)) {
            Location prev = chessPiece.getLocation();
            map.remove(prev);
        }

        ChessPiece other = map.get(loc);
        chessPiece.setLocation(loc);
        if(other != null)
            other.setLocation(null);
        map.put(loc, chessPiece);

        return other;
    }

    @Override
    public void remove(Location loc) {
        map.remove(loc);
    }
}
