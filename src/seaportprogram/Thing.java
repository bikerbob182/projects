/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
