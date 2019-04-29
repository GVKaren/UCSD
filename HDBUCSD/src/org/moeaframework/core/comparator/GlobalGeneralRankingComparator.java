/* Copyright 2018 Juan Luis Valle
 * GGR Comparator
 */
package org.moeaframework.core.comparator;

import static org.moeaframework.core.fitness.GlobalGeneralRankingEvaluator.GGR_ATTRIBUTE;
import static org.moeaframework.core.FitnessEvaluator.FITNESS_ATTRIBUTE;

import java.io.Serializable;
import java.util.Comparator;

import org.moeaframework.core.Solution;

/**
 * Compares solutions using their Global General Ranking. Solutions with lower
 * Global General Ranking are preferred.
 */
public class GlobalGeneralRankingComparator implements DominanceComparator,
Comparator<Solution>, Serializable {

	private static final long serialVersionUID = 4973403102558586390L;
	
	/**
	 * Constructs a dominance comparator for comparing solutions using their 
	 * Global General Ranking.
	 */
	public GlobalGeneralRankingComparator() {
		super();
	}

	/** 
	 * Solutions to be compared using Global General Ranking,
	 * lower GGR indicates more dominance
	 */
	@Override
	public int compare(Solution solution1, Solution solution2) {
		
		if (solution1.getAttribute(FITNESS_ATTRIBUTE) == null || 
				solution2.getAttribute(FITNESS_ATTRIBUTE) == null) {
			solution1.setAttribute(FITNESS_ATTRIBUTE, 0.0);
			solution2.setAttribute(FITNESS_ATTRIBUTE, 0.0);
		}
		
		double ggr1 = (Double)solution1.getAttribute(FITNESS_ATTRIBUTE);
		double ggr2 = (Double)solution2.getAttribute(FITNESS_ATTRIBUTE);

		if (ggr1 < ggr2) {
			return -1;
		} else if (ggr1 > ggr2) {
			return 1;
		} else {
			return 0;
		}
	}

}