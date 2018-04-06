/*
 * File: World.Java
 * Author: Robert Hess 
 * Date: 3/21/2018
 * Purpose: Build a data stucture from text file and display in GUI 
 */
package seaportprogram;


import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Hess
 */
public class World extends Thing {
    HashMap<Integer, SeaPort> ports;
    HashMap<Integer, Dock> docks = new HashMap<>();
    HashMap<Integer, Ship> ships = new HashMap<>();
    PortTime time;
    public World(){
           ports = new HashMap<Integer, SeaPort>();
    }


    // takes Scanner object and reads the data from the file
    public void process (Scanner scan) {
         //System.out.println ("Processing >" + st + "<");
        while (scan.hasNextLine()) {
            //read the line
            String line = scan.nextLine();
            //replace with space comma
            line = line.replaceAll("^\\s+", "");
            if (line.length() > 0 && line.charAt(0) != '/'){
                Scanner sc = new Scanner(line);
                if (!sc.hasNext())
                    return;
                switch (sc.next()) {
                    case "port" : addPort (sc);
                        break;
                    case "dock" : addDock (sc, docks);
                        break;
                    case "pship" : addPassengerShip (sc, docks, ships);
                        break;
                    case "cship" : addCargoShip (sc, docks, ships);
                        break;
                    case "person" : addPerson (sc);
                        break;
                }            
            }
        } 
    }
    public void addPort(Scanner sc){
        SeaPort seaPort = new SeaPort(sc);
        ports.put(seaPort.getIndex(), seaPort);
    }
    public void addDock(Scanner sc, HashMap<Integer, Dock> docks){
        Dock dock = new Dock(sc);
        dock.setThingObject(ports);
        docks.put(dock.getIndex(), dock);
        ports.get(dock.getParent()).addDock(dock);
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
    Ship getShipByIndex (int x) {
        for (SeaPort msp: ports.values())
            for (Ship ms: msp.ships)
                if (ms.index == x)
                return ms;
            return null;
    } 
    Dock getDockByIndex (int x) {
        for (SeaPort msp: ports.values())
            for (Dock ms: msp.docks)
                if (ms.index == x)
                return ms;
            return null;
    }
    Person getPersonByIndex (int x) {
        for (SeaPort msp: ports.values())
            for (Person ms: msp.persons)
                if (ms.index == x)
                return ms;
            return null;
    } // end getShipByIndex 
    public String searchByName(String str){
        String result ="";
        for (SeaPort port : ports.values()) {
            for (Dock dock : port.docks) {
                if (dock.getName().equalsIgnoreCase(str))
                    result += dock.toString();
            }
            for (Ship ship : port.ships) {
                if (ship.getName().equalsIgnoreCase(str))
                    result += ship.toString();
            }
            for (Person person : port.persons) {
                if (person.getName().equalsIgnoreCase(str))
                    result += person.toString();
            }
            if (port.getName().equalsIgnoreCase(str))
                    result += port.toString();
        }
        return result;
    }
    public String searchByIndex(Integer ind){
        int index = ind;
        String result ="";
        result += (getDockByIndex(index) != null) ? getDockByIndex(index).toString() : "";
        result += (getShipByIndex(index) != null) ? getShipByIndex(index).toString() : "";
        result += (getPersonByIndex(index) != null) ? getPersonByIndex(index).toString() : "";   
        result += (ports.get(index) != null) ? ports.get(index).toString() : "";
        return result;
    }
    public String searchBySkill(String str){
        String result = "";
        for (SeaPort port : ports.values()) {
            for (Person person : port.persons) {
                if (person.getSkill().equalsIgnoreCase(str))
                    result += person.toString();
            }
        }
        return result;
    }
    
    public String toString(){
        String result = "The World: ";
        for (SeaPort sp : ports.values())
            result += sp.toString();
        return result;
    }
}
