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
