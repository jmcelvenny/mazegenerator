package net.mcelvenny.mazegenerator;

import java.util.*;

/** Maze Generator class to hold maze generation logic. */
public class MazeGenerator {

    /** Size of the maze. */
    private int size;

    /** Size of inner box of maze. */
    private int innerSize;

    /** Random number generator. */
    private Random random;

    public MazeGenerator(int size, int innerSize) {
        this.size = size;
        this.innerSize = innerSize;
        this.random = new Random(System.currentTimeMillis());
    }

    /**
     * DFS Maze generation using backtracking:
     *
     *  (0) Make the initial cell the current cell and mark it as visited
     *  (1) While there are unvisited cells
     *    (2) If the current cell has any neighbours which have not been visited
     *      (3) Choose randomly one of the unvisited neighbours
     *      (4) Push the current cell to the stack
     *      (5) Remove the wall between the current cell and the chosen cell
     *      (6) Make the chosen cell the current cell and mark it as visited
     *    (7) Else if stack is not empty
     *      (8) Pop a cell from the stack
     *      (9) Make it the current cell
     *
     *  We can modify this algorithm in step (0) to respect a k-by-k box in the center of the grid by treating any
     *  cells within the box as "already visited".
     *
     *  Courtesy of Wikipedia.
     */

    public Cell[][] build() {
        // Initialize data structures for maze generation.
        Cell[][] grid = blankGrid();
        Set<Cell> visited = new HashSet<>();
        Stack<Cell> stack = new Stack<>();

        // (0)
        visited.add(grid[0][0]);
        Cell current = grid[0][0];

        // (0) modified to add excluded cells to visited and empty them.
        for(Cell c : getExcludedCells(grid)) {
            c.setWalls(Cell.NONE);
            visited.add(c);
        }

        // Generate maze using backtracking.
        while(visited.size() != size*size) { // (1)
            List<Cell> unvisitedNeighbors = getUnvisitedNeighbors(current, grid, visited);
            if (unvisitedNeighbors.size() != 0) { // (2)
                Cell randomUnvisitedNeighbor = unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size())); // (3)
                stack.push(current); // (4)
                removeBorderBetween(randomUnvisitedNeighbor, current); // (5)
                current = randomUnvisitedNeighbor; visited.add(current); // (6)
            } else if (!stack.empty()){ // (7)
                current = stack.pop(); // (8) and (9)
            }
        }

        // Add entry and exit.
        Cell entry = grid[0][0], exit = grid[size-1][size-1];
        entry.setWalls(entry.getWalls() - Cell.WEST);
        exit.setWalls(exit.getWalls() - Cell.EAST);

        return grid;
    }


    // Square
    private List<Cell> getExcludedCells(Cell[][] grid) {
        List<Cell> result = new ArrayList<>();
        int mid = (size - innerSize)/2;
        for(int i = mid; i < size-mid; i++) {
            for(int j = mid; j < size-mid; j++) {
                result.add(grid[i][j]);
            }
        }
        return result;
    }


    /*
    // Diamond
    private List<Cell> getExcludedCells(Cell[][] grid) {
        List<Cell> result = new ArrayList<>();
        int mid = (size - innerSize)/2;
        for(int i = mid; i < size-mid; i++) {
            int numCellInRow = i>=2*mid ? 2*(size-mid-i) - 1 :  2*(i - mid) + 1;
            int rowStart = (size - numCellInRow)/2;
            for(int j = rowStart; j < size-rowStart; j++) {
                result.add(grid[i][j]);
            }
        }
        return result;
    }
    */

    /** Returns a blank grid. */
    private Cell[][] blankGrid() {
        Cell[][] grid = new Cell[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                grid[i][j] = new Cell(i, j, Cell.ALL);
            }
        }
        return grid;
    }

    /** Returns a list of unvisited neighbors from a cell. */
    private List<Cell> getUnvisitedNeighbors(Cell current, Cell[][] grid, Set<Cell> visited) {
        int[][] dirs = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
        List<Cell> result = new ArrayList<>();
        for(int[] dir : dirs) {
            int x = current.getX() + dir[0];
            int y = current.getY() + dir[1];
            if (0 <= x && x < grid.length && 0 <= y && y < grid.length && !visited.contains(grid[x][y]))
                result.add(grid[x][y]);
        }
        return result;
    }

    /** Removes the border between two specified cells. */
    private void removeBorderBetween(Cell a, Cell b) {
        int[][] dirs = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
        for(int[] dir : dirs) {
            int x = a.getX() + dir[0];
            int y = a.getY() + dir[1];
            if (b.getX() == x && b.getY() == y) {
                if (dir == dirs[0]) {
                    a.setWalls(a.getWalls() - Cell.EAST);
                    b.setWalls(b.getWalls() - Cell.WEST);
                } else if (dir == dirs[1]) {
                    a.setWalls(a.getWalls() - Cell.WEST);
                    b.setWalls(b.getWalls() - Cell.EAST);
                } else if (dir == dirs[2]) {
                    a.setWalls(a.getWalls() - Cell.SOUTH);
                    b.setWalls(b.getWalls() - Cell.NORTH);
                } else {
                    a.setWalls(a.getWalls() - Cell.NORTH);
                    b.setWalls(b.getWalls() - Cell.SOUTH);
                }
                break;
            }
        }
    }

}
