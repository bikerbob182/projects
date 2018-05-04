/*
 * File: Dock.Java
 * Author: Robert Hess 
 * Date: 3/21/2018
 * Purpose: Build a data stucture from text file and display in GUI 
 */
package seaportprogram;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Hess
 */
class Dock extends Thing {
    Ship ship;

    public Dock(Scanner sc) {
        super(sc);
    }
    public Ship getShip(){
        return ship;
    }
    public void setShip(Ship ship){
        synchronized (this){
            this.ship = ship;
            if (ship != null){
                this.ship.thingObject = this;
            }
        }
    }
    
    public void checkDocksAtDock(){
        if(ship == null || ship.ships.size() == 0)
            requestNewShip();
    }
    
    public void requestNewShip(){
        Ship ship = ((SeaPort) thingObject).getShipFromQue();
        if (ship != null)
            setShip(ship);
    }
    
    public void leaveShipFromDock() {
        setShip(null);
        checkDocksAtDock();
    }
    boolean askForPersonnel(ArrayList<String> requirements) {
        return ((SeaPort) thingObject).askForPersonnel(requirements);
    }
    public ArrayList<Person> requestWorkers(ArrayList<String> requirements, Job job) {
        return ((SeaPort) thingObject).requestWorkers(requirements, job);
    }
    public void releaseWorkers(ArrayList<Person> workers) {
        ((SeaPort) thingObject).releaseWorkers(workers);
    }
    
    public String toString(){
        if(ship != null)
            return "\n Dock: " + super.toString() + "\n " + ship.toString();
        else
            return "\n Dock: " + super.toString();
    }
}
