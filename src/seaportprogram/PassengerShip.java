/*
 * File: PassengerShip.Java
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
public class PassengerShip extends Ship{
    int numberOfPassengers = 0;
    int numberOfRooms = 0;
    int numberOfOccupiedRooms = 0;
    public PassengerShip (Scanner sc) {
        super (sc);
        if (sc.hasNextInt()) numberOfPassengers = sc.nextInt();
        if (sc.hasNextInt()) numberOfRooms = sc.nextInt();
        if (sc.hasNextInt()) numberOfOccupiedRooms = sc.nextInt();
    } // end end Scanner constructor
    
}
