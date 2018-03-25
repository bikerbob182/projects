/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seaportprogram;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Hess
 */
class SeaPort extends Thing {
    ArrayList<Dock> docks = new ArrayList();
    ArrayList<Ship> que = new ArrayList();
    ArrayList<Ship> ships = new ArrayList();
    ArrayList<Person> persons = new ArrayList();
    
    public SeaPort (Scanner sc){
        super(sc);
    }
    public void addShip(Ship ship) {
        ships.add(ship);
    }
    public void addShipToQue(Ship ship) {
        que.add(ship);
    }

   
}
