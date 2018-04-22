/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seaportprogram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Hess
 */

;

//define the class Job extends Thing implements Runnable

public class Job extends Thing implements Runnable {
    private ArrayList<String> requirements = new ArrayList<String>();
    JPanel panel;
    long duration;
    JProgressBar bar = new JProgressBar();
    boolean flag = true, noKillFlag = true;
    JButton stop = new JButton("Stop");
    JButton cancel = new JButton("Cancel");
    Status status = Status.WAITING;
    Job removedJob;
    Ship parentShip;


    enum Status {RUNNING, SUSPENDED, WAITING, DONE}

    public Job(Scanner sc) {
        super(sc);
        duration = (long) sc.nextDouble();
        //while (sc.hasNext()) {
            //String requirement = sc.next();
            //if (requirement != null && requirement.length() > 0)
              //  requirements.add(requirement);
        //}
        System.out.println(this.toString());
    }

    public void setThingObject(HashMap thinkObject) {
        if (thinkObject.get(getParent()) != null) {
            this.thingObject = (Thing) thinkObject.get(getParent());
            parentShip = (Ship) this.thingObject;
            setElements();
            removedJob = this;
            new Thread(this).start();
        }
    }

    private void setElements() {
        panel = new JPanel();
        bar = new JProgressBar();
        bar.setStringPainted(true);
        stop.setMinimumSize(new Dimension(120, 25));
        stop.setMaximumSize(new Dimension(120, 25));
        GroupLayout groupLayout = new GroupLayout(panel);
        panel.setLayout(groupLayout);
        setLayout(groupLayout);
        setActionListeners();
    }

    private void setLayout(GroupLayout groupLayout) {
        

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        JLabel jLabel = new JLabel(parentShip.getName(), SwingConstants.CENTER);
        jLabel.setMinimumSize(new Dimension(150, 25));
        jLabel.setMaximumSize(new Dimension(150, 25));
        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
        .addComponent(bar)
        .addComponent(jLabel)
        .addComponent(stop)
        .addComponent(cancel));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(bar)
        .addComponent(jLabel)
        .addComponent(stop)
        .addComponent(cancel));
    }

    public JPanel getPanel() {
        return panel;
    }
        
    private void setActionListeners() {
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setFlag();
            }
        });
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setKillFlag();
            }
        });
    }

    public void setFlag() {
        flag = !flag;
    }
       
    public void setKillFlag() {
        noKillFlag = false;
        cancel.setBackground(Color.red);
    }
    
    void showStatus(Status st) {
        status = st;
        switch (status) {
            case RUNNING: stop.setBackground(Color.green); stop.setText("Running");
                break;
            case SUSPENDED: stop.setBackground(Color.yellow); stop.setText("Suspended");
                break;
            case WAITING: stop.setBackground(Color.orange); stop.setText("Waiting turn");
                break;
            case DONE: stop.setBackground(Color.red); stop.setText("Done");
                break;
        }
    }

    public void run() {
        while (!World.getValue())
            waitTime(300);
        while (!parentShip.isShipDocked()) {
            waitTime(100);
        }     
        while (!parentShip.processShip()) {
            waitTime(100);
        }
        long time = System.currentTimeMillis();
        long startTime = time;
        long stopTime = time + 1000 * duration;
        double duration = stopTime - time;
        while (time < stopTime && noKillFlag) {
            waitTime(100);
            if (flag) {
                showStatus(Status.RUNNING);
                time += 100;
                bar.setValue((int) (((time - startTime) / duration) * 100));
            } else {
                showStatus(Status.SUSPENDED);
            }
        }
        bar.setValue(100);
        showStatus(Status.DONE);
        parentShip.removeShip(removedJob);
    }

    private void waitTime(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
        }
    }

    public String toString() {
        return String.format("j:%7d:%15s:%5d", getIndex(), getName(), duration);
    }
}