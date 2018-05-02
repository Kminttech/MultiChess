package com.google.example.games.tbmpskeleton.pieces;

import com.google.example.games.tbmpskeleton.game.ChessGame;
import com.google.example.games.tbmpskeleton.grid.Grid;
import com.google.example.games.tbmpskeleton.grid.Location;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a chess piece.
 * @author Kevin
 */
public abstract class ChessPiece {
    private boolean isWhite;
    private ChessGame game;
    private Location location;
    private ArrayList<Location> moves;

    /**
     * Create a new chess piece.
     * @param isWhite The isWhite of this piece. True if it is white; false if it
     * is black.
     * //@param grid The chessboard on which this piece resides.
     * @param game The game manager for the current game.
     * @param location The piece's location on its grid.
     */
    public ChessPiece(boolean isWhite, ChessGame game, Location location) {
        if(game == null)
            throw new IllegalArgumentException("Invalid game object.");
        this.isWhite = isWhite;
        this.game = game;
        this.location = location;
        if(location != null)
            this.moves = getMoveLocations();
    }

    /**
     * Determines whether or not this piece can validly move to that location.
     * @param loc The location in question.
     * @return True if this piece can move to that location; false otherwise.
     */
    public boolean canMove(Location loc) {
        moves = getMoveLocations();
        return moves.contains(loc);
    }

    /**
     * @return A copy of this piece.
     */
    public abstract ChessPiece copy();

    /**
     * Two pieces are equal if and only if they are the same isWhite and occupy
     * the same location.
     * @param other The object to compare to.
     * @return True if <code>other</code> is a matching piece; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if(other == null || !(other instanceof ChessPiece)) {
            return false;
        }

        ChessPiece p = (ChessPiece) other;
        if(location == null)
            return p.location == null && isWhite == p.isWhite;
        else
            return isWhite == p.isWhite && location.equals(p.location);
    }

    /**
     * @return All locations attacked by this piece.
     */
    public abstract ArrayList<Location> getAttackedLocations();

    /**
     * @return The game this piece is taking part in.
     */
    public ChessGame getGame() {
        return game;
    }

    /**
     * @return The grid on which this piece lies.
     */
    public Grid<ChessPiece> getGrid() {
        return game.getGrid();
    }

    /**
     * @return The location this piece is located on.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return A list of moves that this piece can move to, to its best
     * knowledge. If this piece has no location, this method returns null.
     */
    public ArrayList<Location> getMoves() {
        return moves;
    }

    /**
     * @return True if this is a white piece; false if it is black.
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * Sets the location of this piece. Note that this method will allow a
     * change to location even if the piece cannot legally move there.
     * @param loc This piece's new location.
     */
    public void setLocation(Location loc) {
        location = loc;
    }

    public void update() {
        moves = getMoveLocations();
    }

    /**
     * @return An ArrayList containing all the valid locations on this piece's
     * grid that it can move to.
     */
    protected ArrayList<Location> getMoveLocations(){
        ArrayList<Location> locs = getAttackedLocations();
        Iterator<Location> i = locs.iterator();
        while(i.hasNext()) {
            Location loc = i.next();
            if(getGrid().isOccupied(loc) && isSameColor(getGrid().get(loc)))
                i.remove();
        }
        return locs;
    }

    /**
     * @param p Another piece.
     * @return True if the two pieces are the same isWhite; false otherwise.
     */
    boolean isSameColor(ChessPiece p) {
        return isWhite == p.isWhite;
    }
}
