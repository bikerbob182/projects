/*
 * File: CargoShip.Java
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
class CargoShip extends Ship{
    double cargoValue, cargoVolume, cargoWeight;
    public CargoShip(Scanner sc){
        super(sc);
        if(sc.hasNextDouble())
            cargoValue = sc.nextDouble();
        if(sc.hasNextDouble())
            cargoVolume = sc.nextDouble();
        if(sc.hasNextDouble())
            cargoWeight = sc.nextDouble();
    }
}
