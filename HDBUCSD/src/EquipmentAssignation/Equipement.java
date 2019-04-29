/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EquipmentAssignation;
import java.util.Random;
/**
 *
 * @author Karen
 */
public class Equipement {
    String id;
    int localization;
    int type;
    Patient[] calendar;
    
    public Equipement(String id, int loc, int type)
    {
        id= this.id;
        localization= loc;
        type= this.type;
        calendar= new Patient[Variables.maxTime];
        
    }

    public Equipement()
    {
        type= 1;
        calendar= new Patient[Variables.maxTime];
        id= "random";   
        localization= new Random().nextInt(Variables.nLocalidades);
        
        
    }
}
