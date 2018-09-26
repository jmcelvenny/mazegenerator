package net.mcelvenny.mazegenerator;

import net.mcelvenny.mazegenerator.ui.MazeDisplayPanel;

import javax.swing.*;
import java.awt.*;

/** Runner class for Maze Generator. */
public class Runner {

    public static void main(String[] args) {
        int size = 0, innerSize = 0;
        try {
            size = Integer.parseInt(args[0]);
            if (args.length > 1) {
                innerSize = Integer.parseInt(args[1]);
            }
        } catch(NumberFormatException nfe) {
            System.out.println(String.format("Argument %s is not a valid integer!", args[0]));
            System.exit(0);
        }

        MazeGenerator mg = new MazeGenerator(size, innerSize);
        Cell[][] cells = mg.build();

        JFrame jframe = new JFrame("Maze Generator");
        MazeDisplayPanel mdp = new MazeDisplayPanel(cells);
        JScrollPane jsp = new JScrollPane(mdp);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(mdp.getWindowDimension() + 10, mdp.getWindowDimension() + 25 );
        jframe.add(jsp, BorderLayout.CENTER);
        jframe.setVisible(true);
    }

}
