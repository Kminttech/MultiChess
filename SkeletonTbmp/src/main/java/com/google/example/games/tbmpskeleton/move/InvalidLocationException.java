package com.google.example.games.tbmpskeleton.move;


import com.google.example.games.tbmpskeleton.grid.Location;

/**
 * Indicates that a given location is not valid.
 * @author Kevin Minter
 */
public class InvalidLocationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final Location loc;

    /**
     * Creates a new exception indicating that the given location was not
     * valid with default error text.
     * @param loc The location in question.
     */
    public InvalidLocationException(Location loc) {
        this("Location " + loc.toString() + " is not valid.", loc);
    }

    /**
     * Creates a new exception indicating that the given location was not
     * valid.
     * @param message A message detailing the error.
     * @param loc The location in question.
     */
    public InvalidLocationException(String message, Location loc) {
        super(message);
        this.loc = loc;
    }

    /**
     * @return The location that generated this exception.
     */
    public Location getLocation() {
        return loc;
    }
}
