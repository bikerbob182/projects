/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public String toString(){
        if(ship != null)
            return "\n Dock: " + super.toString() + "\n " + ship.toString();
        else
            return "\n Dock: " + super.toString();
    }
}
