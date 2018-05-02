package com.google.example.games.tbmpskeleton.move;


import com.google.example.games.tbmpskeleton.grid.Location;
import com.google.example.games.tbmpskeleton.pieces.ChessPiece;

/**
 * Indicates that something has attempted to move a chessPiece in an illegal or
 * invalid way.
 * @author Kevin Minter
 */
public class IllegalMoveException extends RuntimeException {
    private final ChessPiece chessPiece;
    private final Location location;

    /**
     * Creates a new exception with a standard message stating that the given
     * chessPiece attempted to move to the given location, but the move was not
     * valid.
     * @param p The chessPiece to move.
     * @param loc The attempted move location.
     */
    public IllegalMoveException(ChessPiece p, Location loc) {
        this(p.toString() + " cannot move to location " + loc.toString() + ".", p, loc);
    }

    /**
     * Creates a new exception stating that the given chessPiece attempted to move
     * to the given location, but the move was not valid.
     * @param p The chessPiece to move.
     * @param loc The attempted move location.
     * @param message A message describing the error.
     */
    public IllegalMoveException(String message, ChessPiece p, Location loc) {
        super(message);
        this.chessPiece = p;
        this.location = loc;
    }

    /**
     * @return The location the chessPiece attempted to move to.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The chessPiece that attempted the illegal move.
     */
    public ChessPiece getChessPiece() {
        return chessPiece;
    }
}
