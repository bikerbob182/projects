/*
 * File: Thing.Java
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
public class Thing implements Comparable<Thing> {

    @Override
    public int compareTo(Thing o) {
        return name.compareTo(o.getName());
    }
    //declare variables
    Thing thingObject;
    HashMap<Integer, Thing> parentHashMap;
    int index;
    int parent;
    String name;
    //no argument constructor
    public Thing(){
        //no code
    }
    //constructor
    public Thing(Scanner sc){
        this.name = sc.next();
        this.index = sc.nextInt();
        this.parent = sc.nextInt();
    }
    //setter
    public void setThingObject(HashMap parentHashmap){
        if(parentHashMap.get(parent) !=null)
            thingObject = (Thing) parentHashMap.get(parent);
    }
    //getters
    public String getName(){
        return name;
    }
    public int getIndex(){
        return index;
    }
    public int getParent(){
        return parent;
    }
    //toString method
    public String toString(){
        return name + " " + index;
    }
    
   
}
