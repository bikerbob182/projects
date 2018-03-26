/*
 * File: Ship.Java
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
public class Ship extends Thing{
    //variables
    double draft, weight, length, width;
    PortTime arrivalTime, dockTime;
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
}
