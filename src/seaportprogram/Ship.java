/*
 * File: Ship.Java
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
public class Ship extends Thing{
    //variables
    double draft, weight, length, width;
    PortTime arrivalTime, dockTime;
    public final Object lock = new Object();
    public boolean shipInProcess;
    ArrayList<Job> ships = new ArrayList<Job>();
    
    public Ship(Scanner key){
        super(key);
        if(key.hasNextDouble())
            weight = key.nextDouble();
        if(key.hasNextDouble())
            length = key.nextDouble();
        if(key.hasNextDouble())
            width = key.nextDouble();
        if(key.hasNextDouble())
            draft = key.nextDouble();
            dockTime = new PortTime();     
    }
    public boolean isShipDocked(){
        if (thingObject instanceof SeaPort)
            ((SeaPort) thingObject).checkDocksAtSeaPort();
        else
            ((Dock)thingObject).checkDocksAtDock();
        return thingObject instanceof Dock && ((Dock)thingObject).getShip() ==  this;
    }
    public boolean processShip(){
        boolean process = false;
        synchronized (lock){
            if(!shipInProcess){
                shipInProcess = true;
                process = true;
            }
        }
        return process;
    }
    public void removeShip(Job ship){
        synchronized (lock){
            shipInProcess = false;
        }
        if (ships.size() > 0 && ships.contains(ship))
            ships.remove(ship);
        if (ships.size() == 0){
            ((Dock) thingObject).leaveShipFromDock();
        }
    }
    boolean askForPersonnel(ArrayList<String> requirements) {
        return ((Dock) thingObject).askForPersonnel(requirements);
    }
    public ArrayList<Person> requestWorkers(ArrayList<String> requirements, Job job) {
        return ((Dock) thingObject).requestWorkers(requirements, job);
    }
    public void releaseWorkers(ArrayList<Person> workers) {
        ((Dock) thingObject).releaseWorkers(workers);
    }
    public String toString(){
        String returns = (this instanceof PassengerShip ? "Passenger " : "Cargo ");
        returns += "Ship: " + super.toString();
        if (ships.size() == 0)
            return returns;
        for (Job job : ships)
            returns += "\n - " + job.toString();
        return returns;
    }
}
