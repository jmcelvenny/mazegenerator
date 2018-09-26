package net.mcelvenny.mazegenerator;

/** Cell class represents a single cell in the maze. */
public class Cell {

    /** Bit representation of a cell with no walls. */
    public static final int NONE = 0;

    /** Bit representation of a wall to the north. */
    public static final int NORTH = 1;

    /** Bit representation of a wall to the east. */
    public static final int EAST = 2;

    /** Bit representation of a wall to the south. */
    public static final int SOUTH = 4;

    /** Bit representation of a wall to the west. */
    public static final int WEST = 8;

    /** Bit representation a cell with all walls possible. */
    public static final int ALL = 15;

    /** Integer in [0,15] representing which neighbors have walls. */
    private int walls;

    /** X and Y coordinate of the cell. */
    private int x, y;

    /** Accepts an x, y coordinate pair representing the cell's location in a  maze and a bit vector of walls. */
    public Cell(int x, int y, int walls) {
        this.x = x;
        this.y = y;
        this.walls = walls;
    }

    /** Returns the bit vector of walls. */
    public int getWalls() {
        return walls;
    }

    /** Sets the bit vector of walls. */
    public void setWalls(int walls) { this.walls = walls; }

    /** Returns the X coordinate of the cell. */
    public int getX() {
        return x;
    }

    /** Returns the Y coordinate of the cell. */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cell))
            return false;
        Cell c = (Cell) o;
        return (x == c.x && y  == c.y);
    }

    @Override
    public int hashCode() {
        return 10001 * x + y;
    }
}
