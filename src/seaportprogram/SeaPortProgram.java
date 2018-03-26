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
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

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
             //SeaPortProgram.scrollPane = new JScrollPane(jTree);
        //return jTree; 
        
            return file;
        }
    public static class BuildGUI {
        public BuildGUI(){
           buildTree();
        }
         public  void  buildTree(){
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("World");
        createNodes(top);
        JTree jTree = new JTree(top);
        //SeaPortProgram.scrollPane = new JScrollPane(jTree);
        //return jTree;
        JTextArea textArea = new JTextArea(11, 52);
        textArea.setText("akjfhasdlkfhasdkfl;as");
        // create a scrollpane, givin it the textarea as a constructor argument
        JScrollPane scrollPane = new JScrollPane(jTree);

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
    public  void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode portNode;
        for (SeaPort seaPort : world.ports.values()) {
            portNode = createThingNode(seaPort);
            DefaultMutableTreeNode node = new DefaultMutableTreeNode("Docks");
            for (Dock dock : seaPort.docks) {
                DefaultMutableTreeNode dockNode = createThingNode(dock);
                if (dock.getShip() != null) {
                    DefaultMutableTreeNode shipNode = createThingNode(dock.getShip());
                    dockNode.add(shipNode);
                }
                node.add(dockNode);
                portNode.add(node);
            }
            node = new DefaultMutableTreeNode("Ships in Que");
            for (Ship ship : seaPort.que) {
                DefaultMutableTreeNode shipNode = createThingNode(ship);              
                node.add(shipNode);
                }
                portNode.add(node);
                node = new DefaultMutableTreeNode("All Ships");
                for (Ship ship : seaPort.ships) {
                    DefaultMutableTreeNode shipNode = createThingNode(ship);               
                    node.add(shipNode);
                }
                portNode.add(node);
                node = new DefaultMutableTreeNode("People");
                for (Person person : seaPort.persons) {
                    DefaultMutableTreeNode personNode = createThingNode(person);
                    node.add(personNode);
                }
                portNode.add(node);
                top.add(portNode);
        }
    }
    //definition of the createThingNode() method
    private DefaultMutableTreeNode createThingNode(Thing thing) {
        return new DefaultMutableTreeNode(thing.getIndex() + " " + thing.getName());
    }
    }
   
}



    