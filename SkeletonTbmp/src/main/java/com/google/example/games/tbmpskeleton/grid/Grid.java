package com.google.example.games.tbmpskeleton.grid;

public interface Grid<E> {
    /**
     * Gets the occupant of a given location on the grid.
     * @param loc A location on this grid.
     * @return The object occupying it, or <code>null</code> if it is empty.
     */
    public E get(Location loc);

    /**
     * Inverts the grid so that it is rendered upside-down. Undo the change by
     * calling this method again.
     */
    public void invert();

    /**
     * @return True if the grid is displayed upside-down; false otherwise.
     */
    public boolean isInverted();

    public int getCols();

    public int getRows();

    /**
     * @param loc The location in question.
     * @return True if the given location is occupied; false otherwise.
     */
    public boolean isOccupied(Location loc);

    /**
     * @param loc The location in question.
     * @return True if the location is valid in this grid; false otherwise.
     */
    public boolean isValid(Location loc);

    /**
     * Places an object on the given location.
     */
    public E put(Location loc, E obj);

    /**
     * Removes the object at the given location.
     */
    public void remove(Location loc);
}
