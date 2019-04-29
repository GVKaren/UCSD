/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EquipmentAssignation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import org.moeaframework.Analyzer;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.comparator.LexicographicalComparator;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.util.ReferenceSetMerger;
import org.moeaframework.util.CommandLineUtility;

/**
 *
 * @author Karen
 */
public class Main {
    
        static String Nombre;
        static int m;
        static int Repeticiones;
        static int Evaluaciones=100;	
        static int Poblacion;	
        static double Vecindario;
        static double Cruza;	
        static double Paso;	
        static double PMR;
        static int lInf;
        static int lSup;
        public static int lambda1;
       public static int lambda2;

        public static void main(String[] args)  throws FileNotFoundException, IOException, Exception{
            
        ReferenceSetMerger rSM = new ReferenceSetMerger();
        int n;
        Solution result;

        
           
            // MOEA/D 
            result=CMAES(Problem.class);
           
            System.out.println(result.getObjective(0));
            System.out.println(result.getObjective(1));
            System.out.println(Arrays.toString(EncodingUtils.getPermutation(result.getVariable(0))));
          
            
        }
        
        public static Solution CMAES(Class<?> problema)throws FileNotFoundException, IOException
     {
         Executor executor  = new Executor()
				
				
				.withAlgorithm("IBEA")
                                .withProblemClass(problema, Variables.variables,2)
				.withMaxEvaluations(Evaluaciones)
                 .withProperty("populationSize", 200);
         
        
            NondominatedPopulation result = executor.run();
		result.sort(new LexicographicalComparator());
                Solution finalResult=result.get(0);
                double besta= Double.MAX_VALUE;
                for (int r=0; r<result.size();r++)
                {
                    double val= 0.2*result.get(r).getObjective(0)+0.8*result.get(r).getObjective(1);
                    if (val>besta)
                    {
                        besta=val;
                        finalResult=result.get(r);
                    }
                }
                return finalResult;
                
     }   
        
    
    
}
