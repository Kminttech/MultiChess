package com.google.example.games.tbmpskeleton.move;


import com.google.example.games.tbmpskeleton.grid.Location;

/**
 * Represents a move on a chessboard.
 * @author Kevin Minter
 */
public class Move {
    private Location src, dest;

    /**
     * Creates an object to represent a single move. The special field
     * indicates a special type of move.
     * @param src The source location of the piece moving.
     * @param dest Its destination location.
     */
    public Move(Location src, Location dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Move))
            return false;

        Move m = (Move) o;
        return m.src.equals(src) && m.dest.equals(dest);
    }

    /**
     * @return The destination location.
     */
    public Location getDestination() {
        return dest;
    }

    /**
     * @return The source location.
     */
    public Location getSource() {
        return src;
    }
}
