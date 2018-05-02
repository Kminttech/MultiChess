package com.google.example.games.tbmpskeleton.grid;

/**
 * Represents a location on a board.
 * @author Kevin
 */
public class Location {
    private int row, col;

    public static final int UP = 0;
    public static final int UPRIGHT = 45;
    public static final int RIGHT = 90;
    public static final int DOWNRIGHT = 135;
    public static final int DOWN = 180;
    public static final int DOWNLEFT = 225;
    public static final int LEFT = 270;
    public static final int UPLEFT = 315;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Location))
            return false;

        Location loc = (Location) o;
        return loc.col == col && loc.row == row;
    }

    /**
     * Gets an adjacent location to this object in the given direction.
     * @param direction The direction to move in.
     * @return An adjacent location.
     * @throws IllegalArgumentException if the direction is not a multiple of
     * forty-five degrees.
     */
    public Location getAdjacentLocation(int direction) throws IllegalArgumentException {
        while(direction < 0)
            direction += 360;
        direction %= 360;

        switch(direction) {
            case UP:
                return new Location(row + 1, col);
            case UPRIGHT:
                return new Location(row + 1, col + 1);
            case UPLEFT:
                return new Location(row + 1, col - 1);
            case LEFT:
                return new Location(row, col - 1);
            case RIGHT:
                return new Location(row, col + 1);
            case DOWNRIGHT:
                return new Location(row - 1, col + 1);
            case DOWN:
                return new Location(row - 1, col);
            case DOWNLEFT:
                return new Location(row - 1, col - 1);
            default:
                throw new IllegalArgumentException("" + direction + " is not a" + " multiple of 45 degrees.");
        }
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    /**
     * @return A copy of this location.
     */
    public Location copy() {
        return new Location(row, col);
    }
}
