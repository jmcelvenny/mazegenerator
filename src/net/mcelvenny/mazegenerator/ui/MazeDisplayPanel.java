package net.mcelvenny.mazegenerator.ui;

import net.mcelvenny.mazegenerator.Cell;
import javax.swing.*;
import java.awt.*;

/** MazeDisplayPanel class contains the logic to render a maze given a 2d array of cells. */
public class MazeDisplayPanel extends JPanel {

    /** Width, in pixels, of each cell. */
    private static final int CELL_SIZE = 10;

    /** Width, in pixels, of each wall. */
    private static final int CELL_MARGIN = 2;

    /** Height and width of the maze. */
    private int size;

    /** Grid of Cells to hold wall information. */
    private Cell[][] cells;

    public MazeDisplayPanel(Cell[][] maze) {
        this.cells = maze;
        this.size = maze.length;
    }

    /** Paint component method for JPanel. */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        setSize(new Dimension(getWindowDimension(), getWindowDimension()));
        draw(g);
    }

    /** Returns the dimension, in pixels, of the maze. */
    public int getWindowDimension() {
        return cells.length*CELL_SIZE + CELL_MARGIN*2;
    }

    /** Draw the maze on a provided Graphics object. */
    private void draw(Graphics g) {
        g.setColor(Color.BLACK);
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if ( (cells[i][j].getWalls() & Cell.NORTH) != 0 ) {
                    g.drawLine(i*CELL_SIZE + CELL_MARGIN,
                            j * CELL_SIZE + CELL_MARGIN,
                            (i+1)*CELL_SIZE + CELL_MARGIN,
                            j * CELL_SIZE + CELL_MARGIN);
                }

                if ( (cells[i][j].getWalls() & Cell.EAST) != 0 ) {
                    g.drawLine((i+1)*CELL_SIZE + CELL_MARGIN,
                            j * CELL_SIZE + CELL_MARGIN,
                            (i+1)*CELL_SIZE + CELL_MARGIN,
                            (j+1) * CELL_SIZE + CELL_MARGIN);
                }

                if ( (cells[i][j].getWalls() & Cell.SOUTH) != 0 ) {
                    g.drawLine(i*CELL_SIZE + CELL_MARGIN,
                            (j+1) * CELL_SIZE + CELL_MARGIN,
                            (i+1)*CELL_SIZE + CELL_MARGIN,
                            (j+1) * CELL_SIZE + CELL_MARGIN);
                }

                if ( (cells[i][j].getWalls() & Cell.WEST) != 0 ) {
                    g.drawLine(i*CELL_SIZE + CELL_MARGIN,
                            j * CELL_SIZE + CELL_MARGIN,
                            i*CELL_SIZE + CELL_MARGIN,
                            (j+1) * CELL_SIZE + CELL_MARGIN);
                }
            }
        }
    }
}
