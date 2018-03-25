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
class Person extends Thing {
    String skill;
    public Person(Scanner sc) {
        super(sc);
        if (sc.hasNext())
            skill = sc.next();
    }
    public String getSkill(){
        return skill;
    }
    public String toString(){
        return "Person: " + super.toString() + " " + skill;
    }
}
