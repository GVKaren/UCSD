/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EquipmentAssignation;

import java.util.Arrays;
import org.moeaframework.problem.AbstractProblem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
/**
 *
 * @author Karen
 */
public class Problem extends AbstractProblem{
 
    
    public  Equipement[] equipements;
    Patient[][] assignation;
    public  Patient[] patients;
    
    public String[] printData()
    {
        String[] pa= new String[Variables.npatients];
        int i=0;
        for(Patient p: patients)
        {
            pa[i]=Integer.toString(p.ta)+","+Integer.toString(p.urgency)+","+Integer.toString(p.location);
            i++;
        }
        
        return pa;
    }
    
    
    public void AssignEquipment(Patient[] p)
    {
        patients=p;
    }
    public void AssignPatients(Equipement[] e)
    {
        equipements=e;
    }
    public Problem(int variables, int objetives)
    {
        super(Variables.variables, Variables.objetives);
        
        equipements=new Equipement[Variables.nequipments];
        assignation= new Patient[Variables.maxTime][Variables.nequipments];
        patients= new Patient[Variables.npatients];
        
        for (int i=0; i<Variables.npatients;i++)
        {
            patients[i]=new Patient();
        }
        Variables.patients= patients;
        for (int i=0; i<Variables.nequipments;i++)
        {
            equipements[i]=new Equipement();
        }
        Variables.equipements= equipements;
    }
    
    
    @Override
    public void evaluate(Solution solution) {
        
        double Score=0;
        double Penalization=0;
        Patient patient;
        int[] assing = EncodingUtils.getPermutation(solution.getVariable(0));
       
        
        
        for (int i=0; i<Variables.npatients; i++)
        {
            double e= (i/Variables.maxTime);
           
            Equipement equip= equipements[(int)Math.floor(e)];
            patient= patients[assing[i]];
           double t= i%Variables.maxTime -patient.ta;
            Score+= Variables.A * Math.abs(i%Variables.maxTime *patient.ta )+ Variables.C*Math.abs(patient.location-equip.localization); 
            if (i%Variables.maxTime>0)
             //if(patient.urgency>patients[assing[i-1]].urgency )
            {
               // Penalization=patient.urgency-patients[assing[i-1]].urgency;
                
                double pen=0;
                for (int j=0; j<i%Variables.maxTime;j++)
                {
                   if(patient.urgency>patients[assing[(int)Math.floor(e)+j]].urgency)
                     pen+=patient.urgency-patients[assing[(int)Math.floor(e)+j]].urgency;
                }
                Penalization+=pen;
            }
        }
        
        solution.setObjective(0, Score);
        solution.setObjective(1, Penalization);
        
       
       
    }

    @Override
    public Solution newSolution() {
        
        Solution solution = new Solution(Variables.variables, Variables.objetives, Variables.constrains);
        solution.setVariable(0, EncodingUtils.newPermutation(  Variables.npatients  ));
        return solution;
    }
}
