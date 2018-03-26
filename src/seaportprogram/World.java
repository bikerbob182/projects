/*
 * File: World.Java
 * Author: Robert Hess 
 * Date: 3/21/2018
 * Purpose: Build a data stucture from text file and display in GUI 
 */
package seaportprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Hess
 */
public class World extends Thing {
    HashMap<Integer, SeaPort> ports;
    HashMap<Integer, Dock> docks = new HashMap();
    HashMap<Integer, Ship> ships = new HashMap();
    PortTime time;
    public World(){
           ports = new HashMap<Integer, SeaPort>();
    }

    public void process (Scanner sc) {
        // System.out.println ("Processing >" + st + "<");
        
        if (!sc.hasNext())
            return;
        switch (sc.next()) {
            case "port" : addPort (sc);
            break;
        }
        switch (sc.next()) {
            case "dock" : addDock (sc, docks);
            break;
        }
        switch (sc.next()) {
            case "pship" : addPassengerShip (sc, docks, ships);
            break;
        }
        switch (sc.next()) {
            case "cship" : addCargoShip (sc, docks, ships);
            break;
        }
        switch (sc.next()) {
            case "person" : addPerson (sc);
            break;
        }
    }
    /*Ship getShipByIndex (int x) {
        for (SeaPort msp: ports)
        for (Ship ms: msp.ships)
        if (ms.index == x)
        return ms;
        return null;
    } // end getShipByIndex*/
    public void addPort(Scanner sc){
        SeaPort seaPort = new SeaPort(sc);
        ports.put(seaPort.getIndex(), seaPort);
    }
    public void addDock(Scanner sc, HashMap<Integer, Dock> docks){
        Dock dock = new Dock(sc);
        dock.setThingObject(ports);
        ports.get(dock.getIndex()).addDock(dock);
    }
    public void addPassengerShip(Scanner sc, HashMap<Integer, Dock> docks, HashMap<Integer, Ship> ships){
        PassengerShip passengerShip = new PassengerShip(sc);
        ships.put(passengerShip.getIndex(), passengerShip);
        assignShip(passengerShip, docks);
    }
    public void addCargoShip(Scanner sc, HashMap<Integer, Dock> docks, HashMap<Integer, Ship> ships){
        CargoShip cargoShip = new CargoShip(sc);
        ships.put(cargoShip.getIndex(), cargoShip);
        assignShip(cargoShip, docks);
    }
    public void addPerson(Scanner sc){
        Person person = new Person(sc);
        ports.get(person.getParent()).addPerson(person);
    }

    private void assignShip(Ship ship, HashMap<Integer, Dock> docks) {
        Dock dock = docks.get(ship.getParent());
        if (dock == null){
            ship.setThingObject(ports);
            ports.get(ship.getParent()).addShip(ship);
            ports.get(ship.getParent()).addShipToQue(ship);
            return;
        }
        if (dock.getShip() != null){
            ports.get(ship.getParent()).addShipToQue(ship);
        }
        else{
            dock.setShip(ship);
        }
        ports.get(dock.getParent()).addShip(ship);
    }
    public String toString(){
        String result = "The World: ";
        for (SeaPort sp : ports.values())
            result += sp.toString();
        return result;
    }
}
