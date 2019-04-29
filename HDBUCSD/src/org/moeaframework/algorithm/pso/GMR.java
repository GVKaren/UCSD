/* Copyright 2018 Juan Luis Valle
 * MOPSO/GMR implementation
 */
package org.moeaframework.algorithm.pso;

import java.util.Arrays;

import org.moeaframework.core.FitnessEvaluator;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.comparator.FitnessComparator;
import org.moeaframework.core.comparator.GlobalGeneralRankingComparator;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.fitness.FitnessBasedArchive;
import org.moeaframework.core.fitness.GlobalGeneralRankingEvaluator;
import org.moeaframework.core.operator.RandomInitialization;
import org.moeaframework.core.operator.real.PM;
import org.moeaframework.core.variable.RealVariable;


public class GMR extends AbstractPSOAlgorithm {

	/**
	 * The minimum velocity for each variable.
	 */
	private double[] minimumVelocity;
	
	/**
	 * The maximum velocity for each variable.
	 */
	private double[] maximumVelocity;
	
	public GMR(Problem problem, int swarmSize, int leaderSize,
			double mutationProbability, double distributionIndex) {
		super(problem, swarmSize, leaderSize, new GlobalGeneralRankingComparator(),
				new GlobalGeneralRankingComparator(),
				new FitnessBasedArchive(new GlobalGeneralRankingEvaluator(), leaderSize, 
						new ParetoDominanceComparator()), // new GlobalGeneralRankingComparator()
				new FitnessBasedArchive(new GlobalGeneralRankingEvaluator(), leaderSize, 
						new ParetoDominanceComparator()),
				null); //new PM(mutationProbability, distributionIndex)); // without mutation
		
		// initialize the minimum and maximum velocities
		minimumVelocity = new double[problem.getNumberOfVariables()];
		maximumVelocity = new double[problem.getNumberOfVariables()];
				
		Solution prototypeSolution = problem.newSolution();
				
		for (int i = 0; i < problem.getNumberOfVariables(); i++) {
			RealVariable variable = (RealVariable)prototypeSolution.getVariable(i);
			maximumVelocity[i] = (variable.getUpperBound() - variable.getLowerBound()) / 2.0;
			minimumVelocity[i] = -maximumVelocity[i];
		}	
		
		prototypeSolution.setAttribute(FitnessEvaluator.FITNESS_ATTRIBUTE, 0.00);
		prototypeSolution.setAttribute(GlobalGeneralRankingEvaluator.GGR_ATTRIBUTE, 0.00);
		prototypeSolution.setAttribute(GlobalGeneralRankingEvaluator.GMR_ATTRIBUTE, 0.00);
		prototypeSolution.setAttribute(GlobalGeneralRankingEvaluator.GD_ATTRIBUTE, 0.00);
	}
}
