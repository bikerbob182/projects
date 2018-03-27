/*
 * File: SeaPortProgram.Java
 * Author: Robert Hess 
 * Date: 3/21/2018
 * Purpose: Build a data stucture from text file and display in GUI 
 */
package seaportprogram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Hess
 */
public class SeaPortProgram extends JFrame{
    //declare static variable world
    static World world;
    private static JScrollPane scrollPane;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        //new SeaPortProgram();
        world = new World();
        Scanner scan;
        String st = null;
        try{
            scan = new Scanner(new File(chooseFile()));
            world.process(scan);
            scan = null;
            BuildGUI buildGUI = new BuildGUI();
            System.out.println(world.toString());
       } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static String chooseFile(){
            String file = null;
            JFileChooser fc = new JFileChooser(".");
            fc.setDialogTitle("Please choose a file");
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if(fc.showOpenDialog(null)== JFileChooser.APPROVE_OPTION){
                file = fc.getSelectedFile().toString();
            }
            return file;
        }
    public static class BuildGUI {
        public BuildGUI(){
            JTextArea textArea = new JTextArea(11, 52);
            textArea.setText(world.toString());
            // create a scrollpane, givin it the textarea as a constructor argument
            JScrollPane scrollPane = new JScrollPane(textArea);
            // now add the scrollpane to the jframe's content pane, specifically
            // placing it in the center of the jframe's borderlayout
            JFrame frame = new JFrame("JScrollPane Test");
            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            // make it easy to close the application
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // set the frame size (you'll usually want to call frame.pack())
            frame.setSize(new Dimension(240, 180));
            // center the frame
            frame.setLocationRelativeTo(null);
            // make it visible to the user
            frame.setVisible(true);
        }
    }
}



    