/*
 * File: World.Java
 * Author: Robert Hess 
 * Date: 3/21/2018
 * Purpose: Build a data stucture from text file and display in GUI 
 */
package seaportprogram;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Hess
 */
public class World extends Thing {
    
    HashMap<Integer, SeaPort> portsMap;
    HashMap<Integer, Dock> docksMap = new HashMap<>();
    HashMap<Integer, Ship> shipsMap = new HashMap<>();
    PortTime time;
    static boolean value = false;
    public World(){
           portsMap = new HashMap<Integer, SeaPort>();
    }
    public static void setValue(){     
        value = true;
    }
    public static boolean getValue(){
        return value;
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
                    case "dock" : addDock (sc, docksMap);
                        break;
                    case "pship" : addPassengerShip (sc, docksMap, shipsMap);
                        break;
                    case "cship" : addCargoShip (sc, docksMap, shipsMap);
                        break;
                    case "person" : addPerson (sc);
                        break;
                    case "job" : addJob (sc, shipsMap);
                        break;
                }            
            }
        }      
    }
    public void addPort(Scanner sc){
        SeaPort seaPort = new SeaPort(sc);
        portsMap.put(seaPort.getIndex(), seaPort);
    }
    public void addDock(Scanner sc, HashMap<Integer, Dock> docks){
        Dock dock = new Dock(sc);
        dock.setThingObject(portsMap);
        docks.put(dock.getIndex(), dock);
        portsMap.get(dock.getParent()).addDock(dock);
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
        portsMap.get(person.getParent()).addPerson(person);
    }
    private void addJob(Scanner sc, HashMap<Integer, Ship> ships) {
        Job job = new Job(sc);
        if (ships.get(job.getParent()) != null){
            job.setThingObject(shipsMap);
            shipsMap.get(job.getParent()).ships.add(job);
        }
    }

    private void assignShip(Ship ship, HashMap<Integer, Dock> docks) {
        Dock dock = docks.get(ship.getParent());
        if (dock == null){
            ship.setThingObject(portsMap);
            portsMap.get(ship.getParent()).addShip(ship);
            portsMap.get(ship.getParent()).addShipToQue(ship);
            return;
        }
        if (dock.getShip() != null){
            portsMap.get(ship.getParent()).addShipToQue(ship);
        }
        else{
            dock.setShip(ship);
        }
        portsMap.get(dock.getParent()).addShip(ship);
    }
    Ship getShipByIndex (int x) {
        for (SeaPort msp: portsMap.values())
            for (Ship ms: msp.ships)
                if (ms.index == x)
                return ms;
            return null;
    } 
    Dock getDockByIndex (int x) {
        for (SeaPort msp: portsMap.values())
            for (Dock ms: msp.docks)
                if (ms.index == x)
                return ms;
            return null;
    }
    Person getPersonByIndex (int x) {
        for (SeaPort msp: portsMap.values())
            for (Person ms: msp.persons)
                if (ms.index == x)
                return ms;
            return null;
    } // end getShipByIndex 
    public String searchByName(String str){
        String result ="";
        for (SeaPort port : portsMap.values()) {
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
        result += (portsMap.get(index) != null) ? portsMap.get(index).toString() : "";
        return result;
    }
    public String searchBySkill(String str){
        String result = "";
        for (SeaPort port : portsMap.values()) {
            for (Person person : port.persons) {
                if (person.getSkill().equalsIgnoreCase(str))
                    result += person.toString();
            }
        }
        return result;
    }
    public String sortPersonByName(){
        List<SeaPort> list = new ArrayList<SeaPort>();
        String result = "";
        for (SeaPort seaPort : portsMap.values()) {
            seaPort.sortAllListsByName();
            list.add(seaPort);  
        }   
        Collections.sort(list);
        for (SeaPort seaPort : list) {
            result += "\n People:\n ";
            for (Person person : seaPort.persons) {
                result += person.getName() + "\n ";
            }
        }
        return result;
    }
    //Cant figure out how to do without sorting into more data structures?
    public String sortPersonBySkill(){
        List<SeaPort> list = new ArrayList<SeaPort>();
        String result = "";
        for (SeaPort seaPort : portsMap.values()) {
            seaPort.sortAllListsByName();
            list.add(seaPort);  
        }   
        Collections.sort(list);
        for (SeaPort seaPort : list) {
            result += "\n People:\n ";
            for (Person person : seaPort.persons) {
                result += person.getSkill() + "\n ";
            }
        }
        return result;
    }
    public String sortDockByName(){
    List<SeaPort> list = new ArrayList<SeaPort>();
        String result = "";
        for (SeaPort seaPort : portsMap.values()) {
            seaPort.sortAllListsByName();
            list.add(seaPort);  
        }   
        Collections.sort(list);
        for (SeaPort seaPort : list) {
            result += "\n Docks:\n ";
            for (Dock dock : seaPort.docks) {
                result += dock.getName() + "\n ";
            }
        }
        return result;
    }
    public String sortShipByName() {
        List<SeaPort> list = new ArrayList<SeaPort>();
        String result = "";
        for (SeaPort seaPort : portsMap.values()) {
            seaPort.sortAllListsByName();
            list.add(seaPort);  
        }   
        Collections.sort(list);
        for (SeaPort seaPort : list) {
            result += seaPort.getName() + "\n Ships:\n ";
            for (Ship ship : seaPort.ships) {
                result += ship.getName() + "\n ";
            }
        }
        return result;
    }

    public String sortShipByWeight() {  
        String result = "";
        for (SeaPort seaPort : portsMap.values()) {
            seaPort.sortByWeight();
            result += "Port: ";
            result += seaPort.getName() + "\n Ships:\n ";
            for (Ship ship : seaPort.que) {
                result += ship.getName() + ": " + ship.weight + "\n ";
            }
            result += "\n";
        }
        return result;
    }

    public String sortShipByLength() {
        String result = ""; 
        for (SeaPort seaPort : portsMap.values()) {
            seaPort.sortByLength();
            result += "Port: ";
            result += seaPort.getName() + "\n Ships:\n ";
            for (Ship ship : seaPort.que) {
                result += ship.getName() + ": " + ship.length + "\n ";
            }
            result += "\n";
        }
        return result;
    }

    public String sortShipByWidth() {
        String result = "";
        for (SeaPort seaPort : portsMap.values()) {
            seaPort.sortByWidth();
            result += "Port: ";
            result += seaPort.getName() + "\n Ships:\n ";
            for (Ship ship : seaPort.que) {
                result += ship.getName() + ": " + ship.width + "\n ";
            }
            result += "\n";
        }
        return result;
    }

    public String sortShipByDraft() {
        String result = "";
        for (SeaPort seaPort : portsMap.values()) {
            seaPort.sortByDraft();
            result += "Port: ";
            result += seaPort.getName() + "\n Ships:\n ";
            for (Ship ship : seaPort.que) {
                result += ship.getName() + ": " + ship.draft + "\n ";
            }
            result += "\n";
        }
        return result;
    }
    public String toString(){
        String result = "The World: ";
        for (SeaPort sp : portsMap.values())
            result += sp.toString();
        return result;
    }
}
