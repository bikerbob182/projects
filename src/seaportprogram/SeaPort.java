/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seaportprogram;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Hess
 */
class SeaPort extends Thing {
    ArrayList<Dock> docks = new ArrayList();
    ArrayList<Ship> que = new ArrayList();
    ArrayList<Ship> ships = new ArrayList();
    ArrayList<Person> persons = new ArrayList();
    
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
