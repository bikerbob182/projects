/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seaportprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Hess
 */
public class World extends Thing {
    HashMap<Integer, SeaPort> ports = new HashMap();
    PortTime time;
    public World(Scanner sc) {
        super(sc);
    }
    void process (String st) {
        // System.out.println ("Processing >" + st + "<");
        Scanner sc = new Scanner (st);
        if (!sc.hasNext())
            return;
        switch (sc.next()) {
            case "port" : addPort (sc);
            break;
        }
    }
    Ship getShipByIndex (int x) {
        for (SeaPort msp: ports)
        for (Ship ms: msp.ships)
        if (ms.index == x)
        return ms;
        return null;
    } // end getShipByIndex
    public void addPort(Scanner sc){
        SeaPort seaPort = new SeaPort(sc);
        ports.put(seaPort.getIndex(), seaPort);
    }
}
