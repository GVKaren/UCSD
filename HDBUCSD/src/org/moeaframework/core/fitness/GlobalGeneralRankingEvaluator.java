/* Copyright 2018 Juan Luis Valle
 * GGR Ranking Evaluator
 */
package org.moeaframework.core.fitness;

//import org.moeaframework.core.FastNondominatedSorting;
import org.moeaframework.core.FitnessEvaluator;
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;

import java.lang.Math;

/**
 * Assigns ranking values based on the Global General Ranking
 */
public class GlobalGeneralRankingEvaluator implements FitnessEvaluator {
	
	public static final String GMR_ATTRIBUTE = "gmr";
	public static final String GD_ATTRIBUTE = "gd";
	public static final String GGR_ATTRIBUTE = "ggr";
	
	/**
	 * Constructs a new Global General Ranking evaluator.
	 */
	
	public GlobalGeneralRankingEvaluator() {
		super();
	}

	@Override
	public void evaluate(Population population) {
		//new FastNondominatedSorting().updateCrowdingDistance(copy(population)); //priority 1 / CHECK THIS! / looks like archive's trim
		
		for (Solution solution : population) {
			solution.setAttribute(GMR_ATTRIBUTE, (Double)global_margin_ranking(population, solution));
			solution.setAttribute(GD_ATTRIBUTE, (Double)global_density(population, solution));
			solution.setAttribute(GGR_ATTRIBUTE, (Double)global_general_ranking(solution));
			solution.setAttribute(FITNESS_ATTRIBUTE, (Double)global_general_ranking(solution));
		}
	}
	
	/**
	 * Returns a copy of the population.  The fast non-dominated sorting
	 * routine reorders solutions in the population, so creating a copy allows
	 * the original population to remain unchanged.  
	 * 
	 * @param population the original population
	 * @return a copy of the population
	 */
	private Population copy(Population population) {
		Population result = new Population();
		
		for (Solution solution : population) {
			result.add(solution);
		}
		
		return result;
	}
	
	private double global_margin_ranking(Population population, Solution solution1) {
		Population pop = copy(population);
		double gmr_index = 0.0, prod1 = 1, prod2;
		
		if (pop.contains(solution1)) { pop.remove(solution1); }
		
		for (int i = 0; i < solution1.getNumberOfObjectives(); i++) {
			prod1 *= (double)solution1.getObjective(i);
		}
		
		for (Solution solution2 : pop) {
			prod2 = 1;
			
			for (int j = 0; j < solution2.getNumberOfObjectives(); j++) {
				prod2 *= (double)solution2.getObjective(j);
			}
			
			gmr_index += (double)Math.max((prod1 - prod2), 0.0);
		}
		
		return gmr_index;
	}
	
	private double global_density(Population population, Solution solution1) {
		Population pop = copy(population);
		double gd_index = 0;
		
		if (pop.contains(solution1)) { pop.remove(solution1); }
		
		for (Solution solution2 : pop) {
			double distance = 0.0;

			for (int i = 0; i < solution1.getNumberOfObjectives(); i++) {
				distance += Math.pow(solution1.getObjective(i) - solution2.getObjective(i), 2.0);
			}

			gd_index +=  (double)Math.sqrt(distance);
		}
		
		return gd_index;
	}
	
	private double global_general_ranking(Solution solution) {
		double gmr_index = (double)solution.getAttribute(GMR_ATTRIBUTE);
		double gd_index = (double)solution.getAttribute(GD_ATTRIBUTE);
		
		if (gmr_index == 0) { return 0.0; }
		
		return (gmr_index / gd_index);
	}
	
	@Override
	public boolean areLargerValuesPreferred() {
		return false;
	}

}