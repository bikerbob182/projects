/*
 * File: Person.Java
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
