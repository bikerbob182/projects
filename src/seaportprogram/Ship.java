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
