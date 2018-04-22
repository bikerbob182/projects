/*
 * File: Dock.Java
 * Author: Robert Hess 
 * Date: 3/21/2018
 * Purpose: Build a data stucture from text file and display in GUI 
 */
package seaportprogram;

import java.util.Scanner;

/**
 *
 * @author Hess
 */
class Dock extends Thing {
    Ship ship;

    Dock(Scanner sc) {
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
    public String toString(){
        if(ship != null)
            return "\n Dock: " + super.toString() + "\n " + ship.toString();
        else
            return "\n Dock: " + super.toString();
    }
}
