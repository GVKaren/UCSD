/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EquipmentAssignation;

import static EquipmentAssignation.Main.Evaluaciones;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.comparator.LexicographicalComparator;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.util.ReferenceSetMerger;

/**
 *
 * @author Karen
 */
public class Run {
    
     public int[] Start(Problem p)  throws FileNotFoundException, IOException, Exception{
            
       Solution result;
            result=CMAES(p.getClass());
           
            System.out.println(result.getObjective(0));
            System.out.println(result.getObjective(1));
            return (EncodingUtils.getPermutation(result.getVariable(0)));
          
            
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
