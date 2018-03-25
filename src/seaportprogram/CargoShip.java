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
