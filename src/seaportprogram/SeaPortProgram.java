/*
 * File: SeaPortProgram.Java
 * Author: Robert Hess 
 * Date: 3/21/2018
 * Purpose: Build a data stucture from text file and display in GUI 
 */
package seaportprogram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            System.gc();
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
        private final String [] sortList1 = {"Ship", "Dock", "Person",};
        private final String [] sortList2 = {"Name", "Weight", "Length", "Width", "Draft"};
        private final String [] sortList3 = {"Name", "Skill" };
        public BuildGUI(){
            
            JFrame frame = new JFrame("Sea Port Progam");
            frame.setSize(new Dimension(900, 600));
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BorderLayout());
            leftPanel.setSize(new Dimension(500,600));
           
            //build scrollpane           
            JTextArea textArea = new JTextArea();
            textArea.setText(world.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            //build search panel
            JPanel searchPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            JLabel searchBy = new JLabel("Search by:");
            JButton searchButton = new JButton("Search");
            JLabel sortBy = new JLabel("Sort by");
            JButton sortButton = new JButton("Sort");
            JComboBox<String> searchCrit = new JComboBox<String>(searchList);
            JComboBox<String> sortCrit = new JComboBox<String>(sortList1);
            JComboBox<String> sortCrit2 = new JComboBox<String>(sortList2);
            JTextField searchInput = new JTextField();
            searchInput.setPreferredSize(new Dimension(100,30));
            JPanel containerForJobs = new JPanel();
            JPanel containerForResources = new JPanel();
            containerForJobs.setLayout(new BoxLayout(containerForJobs, BoxLayout.Y_AXIS));
            containerForResources.setLayout(new BoxLayout(containerForResources, BoxLayout.Y_AXIS));
            boolean hasJob = false;
            boolean hasResource = false;
            for (SeaPort seaPort : world.portsMap.values()) {
                containerForResources.add(seaPort.getPanel());
                if (!hasResource)
                    hasResource = seaPort.persons.size() > 0;
                for (Ship ship : seaPort.ships) {
                    for (Job job : ship.ships) {
                        containerForJobs.add(job.getPanel());
                        hasJob = true;
                    }
                }
            }
            JScrollPane jobsScrollPane = new JScrollPane(containerForJobs);
            JScrollPane resourcesScrollPane = new JScrollPane(containerForResources);
            jobsScrollPane.setMaximumSize(new Dimension(5660,800));
            resourcesScrollPane.setMaximumSize(new Dimension(5660,800));
            //sort.setPreferredSize(new Dimension(50,40));
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 0;
            gbc.gridy = 0;
            searchPanel.add(searchBy, gbc);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 1;
            gbc.gridy = 0;
            searchPanel.add(searchCrit, gbc);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 2;
            gbc.gridy = 0;
            searchPanel.add(searchInput, gbc);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 3;
            gbc.gridy = 0;
            searchPanel.add(searchButton, gbc);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 0;
            gbc.gridy = 1;
            searchPanel.add(sortBy, gbc);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 1;
            gbc.gridy = 1;
            searchPanel.add(sortCrit, gbc);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 2;
            gbc.gridy = 1;
            searchPanel.add(sortCrit2, gbc);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 3;
            gbc.gridy = 1;
            searchPanel.add(sortButton, gbc);
            
            
            //build button panel 2
            JPanel buttonPanel2 = new JPanel();
            JButton resetButton = new JButton("Reset");
            //reset.setPreferredSize(new Dimension(50,30));
            buttonPanel2.add(resetButton);
            //build space panels
            JPanel spaceLeft = new JPanel();
            JPanel spaceRight = new JPanel();
            //add panels to JFrame
            leftPanel.add(searchPanel, BorderLayout.PAGE_START);
            leftPanel.add(scrollPane, BorderLayout.CENTER);
            leftPanel.add(resourcesScrollPane, BorderLayout.PAGE_END);
            leftPanel.add(spaceLeft, BorderLayout.LINE_START);
            leftPanel.add(spaceRight, BorderLayout.LINE_END);
            
            JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, jobsScrollPane);
            frame.add(jSplitPane);
            // make it easy to close the application
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // set the frame size (you'll usually want to call frame.pack())
            frame.setLocationRelativeTo(null);
            // make it visible to the user
            frame.setVisible(true);
            World.setValue();
            
            //construct button actions
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object selection = searchCrit.getSelectedItem(); 
                    String str = searchInput.getText();  
                    if(selection.equals("Name")){
                        textArea.setText(world.searchByName(str));                        
                    }
                    else if(selection.equals("Index")){
                        int index = 0;
                        try {
                            index = Integer.parseInt(str);
                        } catch (NumberFormatException f) {
                            JOptionPane.showMessageDialog(null, "Plese Enter a Valid Number");
                        }
                        textArea.setText(world.searchByIndex(index));
                    }
                    else if(selection.equals("Skill")){
                        textArea.setText(world.searchBySkill(str));
                    }                    
                } 
            });
            resetButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    textArea.setText(world.toString());
                }  
            });
            sortCrit.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object selection = sortCrit.getSelectedItem();
                    if(selection.equals("Ship")){
                        sortCrit2.removeAllItems();
                        for(String str : sortList2) {
                            sortCrit2.addItem(str);
                        }
                    }
                    else if(selection.equals("Person")){
                        sortCrit2.removeAllItems();
                        for(String str : sortList3) {
                            sortCrit2.addItem(str);
                        }
                    }
                    else{
                        sortCrit2.removeAllItems();
                        sortCrit2.addItem("Name");
                    }
                }  
            });
            sortButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object selection = sortCrit.getSelectedItem();
                    Object selection2 = sortCrit2.getSelectedItem();
                    if(selection.equals("Ship")){
                        if(selection2.equals("Name")){
                            textArea.setText(world.sortShipByName());
                        }
                        else if(selection2.equals("Weight")){
                            textArea.setText(world.sortShipByWeight());
                        }
                        else if(selection2.equals("Length")){
                            textArea.setText(world.sortShipByLength());
                        }
                        else if(selection2.equals("Width")){
                            textArea.setText(world.sortShipByWidth());
                        }
                        else if(selection2.equals("Draft")){
                            textArea.setText(world.sortShipByDraft());
                        }
                    }
                    else if(selection.equals("Person")){
                        if(selection2.equals("Name")){
                            textArea.setText(world.sortPersonByName());
                        }
                        else if(selection2.equals("Skill")){
                            textArea.setText(world.sortPersonBySkill());
                        }
                    }
                    else if(selection.equals("Dock")){
                        textArea.setText(world.sortDockByName());
                    }
                    
                }  
            });
        }
    }    
}



    