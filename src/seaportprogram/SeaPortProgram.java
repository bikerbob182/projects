/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seaportprogram;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Hess
 */
public class SeaPortProgram extends JFrame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        new SeaPortProgram();
        World world = new World();
        Scanner scan;
        String st = null;
        try{
            scan = new Scanner(new File(chooseFile()));
            while(scan.hasNextLine()){
                st = scan.nextLine();
                world.process(st);
            }
            world.process(st);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        
    }
    public SeaPortProgram() throws FileNotFoundException{
        
        
        //build GUI
        setTitle("Class Dependecny Graph");
        setSize(650,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Toolkit tk = Toolkit.getDefaultToolkit();

        //start window centered on screen
        Dimension dim = tk.getScreenSize();
        int xPos = (dim.width / 2) - (this.getWidth() / 2);
        int yPos = (dim.height / 2) - (this.getHeight() / 2); 
        setLocation(xPos, yPos);
        
        setResizable(false);
        //build main panel
        JPanel window = new JPanel();
        window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
        
                
        //build input field panel
        JPanel inputPanel =  new JPanel();
        
        JPanel inputPanelA = new JPanel();
        inputPanelA.setLayout(new FlowLayout());
        JLabel inputLabel = new JLabel("Originl List       ");   
        JTextField inputField = new JTextField("");               
        inputField.setPreferredSize(new Dimension(200,30));
        JButton selectFile = new JButton("Select File");  
        inputPanelA.add(inputLabel);
        inputPanelA.add(inputField);
        inputPanelA.add(selectFile);
        
        JPanel inputPanelB = new JPanel();
        inputPanelB.setLayout(new FlowLayout());
        JLabel classLabel = new JLabel("Sorted List       ");
        JTextField classField = new JTextField("");
        classField.setPreferredSize(new Dimension(200,30));
        JButton sort = new JButton("Topological Order");
        inputPanelB.add(classLabel);
        inputPanelB.add(classField);
        inputPanelB.add(sort);
        
        TitledBorder titleBorder1 = new TitledBorder(" ");
        inputPanel.setBorder(titleBorder1);
        inputPanel.add(inputPanelA);
        inputPanel.add(inputPanelB);
        
        //build radio button pannel
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new FlowLayout());
        JTextArea textArea = new JTextArea(11, 52);
        textArea.append(" ");
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane();
        TitledBorder titleBorder2 = new TitledBorder("Recompilation Order");
        textArea.setBorder(titleBorder2);
        textAreaPanel.add(scrollPane);
          
        //add panels to main panel
        window.add(inputPanel);
        //window.add(buttonPanel);
        window.add(textAreaPanel);

        //add main window containing all sub-panels
        add(window);
        //add(window, BorderLayout.WEST);
        setVisible(true);
        
        selectFile.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
        });
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
}



    