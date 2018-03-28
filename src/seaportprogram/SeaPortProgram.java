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
import javax.swing.*;


/**
 *
 * @author Hess
 */
public class SeaPortProgram {
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
    public static class BuildGUI extends JFrame{
        private final String[] searchList = {"Name", "Index", "Skill"};
        public BuildGUI(){
            JFrame frame = new JFrame("Sea Port Progam");
            frame.setSize(new Dimension(400, 300));

            //build scrollpane           
            JTextArea textArea = new JTextArea();
            textArea.setText(world.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            //build search panel
            JPanel searchPanel = new JPanel();
            JLabel searchBy = new JLabel("Search by:");
            JButton searchButton = new JButton("Search");
            JComboBox<String> petList = new JComboBox<String>(searchList);
            JTextField searchInput = new JTextField();
            searchInput.setPreferredSize(new Dimension(100,30));
            //sort.setPreferredSize(new Dimension(50,40));
            searchPanel.add(searchBy);
            searchPanel.add(petList);
            searchPanel.add(searchInput);
            searchPanel.add(searchButton);            
            //build button panel 2
            JPanel buttonPanel2 = new JPanel();
            JButton reset = new JButton("Reset");
            //reset.setPreferredSize(new Dimension(50,30));
            buttonPanel2.add(reset);
            //build space panels
            JPanel spaceLeft = new JPanel();
            JPanel spaceRight = new JPanel();
            //add panels to JFrame
            frame.getContentPane().add(searchPanel, BorderLayout.PAGE_START);
            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            frame.getContentPane().add(buttonPanel2, BorderLayout.PAGE_END);
            frame.getContentPane().add(spaceLeft, BorderLayout.LINE_START);
            frame.getContentPane().add(spaceRight, BorderLayout.LINE_END);
            
            // make it easy to close the application
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // set the frame size (you'll usually want to call frame.pack())
            frame.setLocationRelativeTo(null);
            // make it visible to the user
            frame.setVisible(true);
        }
    }
}



    