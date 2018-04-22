/*
 * File: SeaPort.Java
 * Author: Robert Hess 
 * Date: 3/21/2018
 * Purpose: Build a data stucture from text file and display in GUI 
 */
package seaportprogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

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
