/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EquipmentAssignation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Karen
 */
public class Patient {
    String id;
    //patient arrived time
    int ta=0;
    int urgency=0;
    int location =0;
    //equipement ussage time
    int tu=0;
    String da;
    String du;
    
    
    /**
     * Initialization of class patient when it had been already valorated.
     * @param id Patient id
     * @param t Arrive moment
     * @param urg Urgency assigned
     * @param location Place where the patient come from
     */
    public Patient(String id, int t, int urg, int location)
    {
         this.id=id;
        ta=t;
        urgency=urg;
        this.location= location;
         SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        //da = dFormat.format(d);
        Date d= new Date();
        da = dFormat.format(d);
                             
            Calendar c = Calendar.getInstance();
             try {
                 c.setTime(dFormat.parse(da));
             } catch (ParseException ex) {
                 Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
             }
            c.add(Calendar.DATE, ta);  // number of days to add
            da = dFormat.format(c.getTime());
        
    }
    public Patient()
    {
        id= Variables.Fantasma;
        ta= new Random().nextInt(5)-5;
        urgency=new Random().nextInt(Variables.maxUrg);
        location= new Random().nextInt(Variables.nLocalidades);
       
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        //da = dFormat.format(d);
        Date d= new Date();
        da = dFormat.format(d);
                             
            Calendar c = Calendar.getInstance();
             try {
                 c.setTime(dFormat.parse(da));
             } catch (ParseException ex) {
                 Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
             }
            c.add(Calendar.DATE, ta);  // number of days to add
            da = dFormat.format(c.getTime());
    }
    public void addSumition(String date)
    {
        da= date;
    }
    public void addAssigment(String date)
    {
        du= date;
    }
    
}
