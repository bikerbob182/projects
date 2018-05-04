/*
 * File: SeaPort.Java
 * Author: Robert Hess 
 * Date: 3/21/2018
 * Purpose: Build a data stucture from text file and display in GUI 
 */
package seaportprogram;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 *
 * @author Hess
 */
class SeaPort extends Thing {
    ArrayList<Dock> docks = new ArrayList<Dock>();
    ArrayList<Ship> que = new ArrayList<Ship>();
    ArrayList<Ship> ships = new ArrayList<Ship>();
    ArrayList<Person> persons = new ArrayList<Person>();
    ArrayList <Job> jobs = new ArrayList <Job>(); 
    JPanel containerPanel = new JPanel();
    JLabel resourceLabel = new JLabel("", SwingConstants.LEFT);
    JLabel requestLabel = new JLabel("", SwingConstants.LEFT);
    JProgressBar resourceProgress;
    
    public SeaPort (Scanner sc){
        super(sc);
    }
    public void addShip(Ship ship) {
        ships.add(ship);
    }
    public void addShipToQue(Ship ship) {
        que.add(ship);
    }
    public void addDock(Dock dock){
        docks.add(dock);
    }
    public void addPerson(Person person){
        persons.add(person);
    }

    public void sortByWeight() {
        Collections.sort(que, new Comparator<Ship>() {
            @Override
            public int compare(Ship s1, Ship s2) {
                return (s1.weight < s2.weight ? -1 : (s1.weight == s2.weight ? 0 : 1));
            }
        });
    }
    public void sortByLength() {
        Collections.sort(que, new Comparator<Ship>() {
            @Override
            public int compare(Ship s1, Ship s2) {
                return (s1.length < s2.length ? -1 : (s1.length == s2.length ? 0 : 1));
            }
        });
    }
    public void sortByWidth() {
        Collections.sort(que, new Comparator<Ship>() {
            @Override
            public int compare(Ship s1, Ship s2) {
                return (s1.width < s2.width ? -1 : (s1.width == s2.width ? 0 : 1));
            }
        });
    }
    public void sortByDraft() {
        Collections.sort(que, new Comparator<Ship>() {
            @Override
            public int compare(Ship s1, Ship s2) {
                return (s1.draft < s2.draft ? -1 : (s1.draft == s2.draft ? 0 : 1));
            }
        });
    }
    public void sortAllListsByName() {
        Collections.sort(ships);
        Collections.sort(docks);
        Collections.sort(persons);
        Collections.sort(que);
    }
    
    public Ship getShipFromQue(){
        synchronized (this){
            if(que.size() > 0){
                Ship ship = que.get(que.size()-1);
                que.remove(ship);
                return ship;
            }
        }
        return null;
    }
    
    public synchronized void checkDocksAtSeaPort(){
        for (Dock dock : docks){
            dock.checkDocksAtDock();
        }
    }
    public boolean askForPersonnel(ArrayList<String> requirements) {
        ArrayList<Person> personnelChecker = new ArrayList<Person>();
        for (String requirement : requirements) {
            for (Person person : persons) {
                if (person.hasSkill(requirement) && !personnelChecker.contains(person))
                    personnelChecker.add(person);
                    if (personnelChecker.size() == requirements.size())
                        return true;
                }
        }
        return false;
    }
    public ArrayList<Person> requestWorkers(ArrayList<String> requirements, Job job) {
        ArrayList<Person> requiredWorkers = new ArrayList<Person>();
        for (String requirement : requirements) {
            for (Person person : persons) {
                if (person.hasSkill(requirement)) synchronized (this) {
                    requiredWorkers.add(person.hire());
                }
                if (requiredWorkers.size() == requirements.size()) {
                    broadcastUpdateOnResourcePool();
                    if (jobs.contains(job)) jobs.remove(job);
                        return requiredWorkers;
                }
            }
        }
        if (!jobs.contains(job)) {
            jobs.add(job);
        }
        if (requiredWorkers.size() > 0)
            releaseWorkers(requiredWorkers);
            return null;
    }
    public void releaseWorkers(ArrayList<Person> workers) {
        for (Person worker : workers) {
            if (worker != null) 
                synchronized (this) {
                    worker.release();
                }
        }
        broadcastUpdateOnResourcePool();
    }
    private void broadcastUpdateOnResourcePool() {
        int availableResources = getAvailableResources();
        resourceLabel.setText("Resources: " + availableResources);
        requestLabel.setText("Requests: " + jobs.size());
        if (resourceProgress != null) {
            resourceProgress.setValue(availableResources);
            if (availableResources < Math.round(persons.size() * 0.2)) {
                resourceProgress.setForeground(Color.red);
            } else if (availableResources < Math.round(persons.size() * 0.6)) {
                resourceProgress.setForeground(Color.yellow);
            } else {
                resourceProgress.setForeground(Color.blue);
            }
        }
    }
    private int getAvailableResources() {
        int numOfAvailablePerson = 0;
        for (Person person : persons) {
            if (person.isAvailable)
                numOfAvailablePerson++;
        }
        return numOfAvailablePerson;
    }
     private void setVisualElements() {
        containerPanel = new JPanel();
        requestLabel.setMaximumSize(new Dimension(100, 20));
        requestLabel.setMaximumSize(new Dimension(100, 20));
        resourceLabel.setMinimumSize(new Dimension(100, 20));
        resourceLabel.setMinimumSize(new Dimension(100, 20));
        GroupLayout groupLayout = new GroupLayout(containerPanel);
        containerPanel.setLayout(groupLayout);
        setLayout(groupLayout);
    }
    private void setLayout(GroupLayout groupLayout) {
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        JLabel portLabel = new JLabel("Port: " + getName(), SwingConstants.LEFT);
        portLabel.setMaximumSize(new Dimension(120, 20));
        portLabel.setMinimumSize(new Dimension(120, 20));
        resourceProgress = new JProgressBar(0, persons.size());
        broadcastUpdateOnResourcePool();
        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
        .addComponent(portLabel)
        .addComponent(resourceProgress)
        .addComponent(resourceLabel)
        .addComponent(requestLabel)
        );
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(portLabel)
        .addComponent(resourceProgress)
        .addComponent(resourceLabel)
        .addComponent(requestLabel)
        );
    }
    public Component getPanel() {
        setVisualElements();
        return containerPanel;
    }
    public String toString () {
        String st = "\n\nSeaPort: " + super.toString();
        for (Dock md: docks) st += "\n" + md;
            st += "\n\n --- List of all ships in que:";
        for (Ship ms: que ) st += "\n > " + ms;
            st += "\n\n --- List of all ships:";
        for (Ship ms: ships) st += "\n > " + ms;
            st += "\n\n --- List of all persons:";
        for (Person mp: persons) st += "\n > " + mp;
            return st;
    } // end method toString
}
