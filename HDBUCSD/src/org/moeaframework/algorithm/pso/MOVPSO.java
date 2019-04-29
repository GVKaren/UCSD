/* Copyright 2009-2016 David Hadka
 *
 * This file is part of the MOEA Framework.
 *
 * The MOEA Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The MOEA Framework is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.moeaframework.algorithm.pso;

import org.moeaframework.core.PRNG;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.comparator.CrowdingComparator;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.fitness.CrowdingDistanceFitnessEvaluator;
import org.moeaframework.core.fitness.FitnessBasedArchive;
import org.moeaframework.core.operator.real.PM;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.util.Vector;

//NOTE: This implementation is derived from the original manuscripts and the
//JMetal implementation.

/**
 * Implementation of MOVPSO, the vortex multi-objective particle
 * swarm optimizer.
 * <p>
 * References:
 * <ol>
 *   <li>Meza, J., Espitia, H., Montenegro, C., Giménez, E., & González-Crespo, 
 *      R. (2017). MOVPSO: vortex multi-objective particle swarm optimization.
 *      Applied Soft Computing, 52, 1042-1057.
 *   
 * </ol>
 */
public class MOVPSO extends AbstractPSOAlgorithm {
	
	/**
	 * The minimum velocity for each variable.
	 */
	private double[] minimumVelocity;
	
	/**
	 * The maximum velocity for each variable.
	 */
	private double[] maximumVelocity;
        
        /**
         * 
         */
        
        private final double delta_t=0.1;
        /**
         * 
         */
        private final double ConvergenceRatio=10;
        private final double beta0=0.4;
        private  double beta=0.4;
        private  double alpha=0;
        private  double mi=1;
        private  double g=0;
        private double a=1;
        private int N=0;
        protected double[] sums= new double[problem.getNumberOfVariables()];
	
	public MOVPSO(Problem problem, int swarmSize, int leaderSize,
			double mutationProbability, double distributionIndex) {
		super(problem, swarmSize, leaderSize, new CrowdingComparator(),
				new ParetoDominanceComparator(),
				new FitnessBasedArchive(new CrowdingDistanceFitnessEvaluator(), leaderSize),
				null,
				new PM(mutationProbability, distributionIndex));

		// initialize the minimum and maximum velocities
		minimumVelocity = new double[problem.getNumberOfVariables()];
		maximumVelocity = new double[problem.getNumberOfVariables()];
		
		Solution prototypeSolution = problem.newSolution();
		
		for (int i = 0; i < problem.getNumberOfVariables(); i++) {
			RealVariable variable = (RealVariable)prototypeSolution.getVariable(i);
			maximumVelocity[i] = (variable.getUpperBound() - variable.getLowerBound()) / 2.0;
			minimumVelocity[i] = -maximumVelocity[i];
		}
	}
        /**
         * 
         */
       /* protected double movNewVelocity(double vn, double alpha, double beta, int i, int j,double a, int N)
        {
           double velocity = velocities[i][j]+(alpha-beta* Math.sqrt(velocities[i][j])*velocities[i][j]- (a/N)* sumR());
           
            
           return velocity; 
        }*/

        /**
         * Define the set of distances between neighbors sample.
        * @return sample area of the full swarm
         */
        protected Solution[] sampleArea()
        {
            int samples= (int)(swarmSize*.1);
            Solution[] sample= new Solution[samples];
            for (int i=0; i<samples; i++){
            sample[i]= particles[PRNG.nextInt(0, swarmSize-1)];
            }
            return sample;
        }
        double[] takeArrayVariable(Solution solution)
        {
            double[] r = new double[problem.getNumberOfVariables()];
            for (int k=0; k<problem.getNumberOfVariables(); k++){
                        r[k]= Double.parseDouble(solution.getVariable(k).toString())+Double.parseDouble(solution.getVariable(k).toString());
                    }
            return r;
        }
        /**
         * Farthest particle in the ratio rest
         * @return 
         */
        protected double[] find_rest()
        {
            Solution[] sample= sampleArea();
            double[] resp = new double[problem.getNumberOfVariables()];
            double[] r = new double[problem.getNumberOfVariables()];
            double min= 9999;
            double max=0;
            double[] rmin= new double[problem.getNumberOfVariables()];
            
            double magnitude;
            for (int i=0; i<swarmSize*.1;i++){
                min =9999;
                for (int j=i+1; j<swarmSize*.1; j++){
                    for (int k=0; k<problem.getNumberOfVariables(); k++){
                        r[k]= Double.parseDouble(sample[i].getVariable(k).toString())+Double.parseDouble(sample[j].getVariable(k).toString());
                    }
                    magnitude=Vector.magnitude(r);
                    if (magnitude<min)
                    {
                        min = magnitude;
                        rmin=r;
                    }
                }
                if (min>max)
                {
                    max=min;
                    resp=rmin;
                }
                
            }
            return resp;
        }
        
        /**
         * rale variable. 
         * @param rest
         * @return 
         */
        protected double[]  findFarest(double[] rest)
        {
            double[] fartherst= new double[problem.getNumberOfVariables()];
            for (int j = 1; j < swarmSize; j++) {
                        double[] actual=takeArrayVariable(particles[j]);
			if(Vector.magnitude(Vector.subtract(actual, rest))>Vector.magnitude(fartherst))
                            fartherst= actual;
		}
            return fartherst;
        }
        /**
         *Defines if the actual state is convergence or divergence 
         * 
         * @return 
         * True; Convergence
         * False; Divergence
         */
        protected boolean setState()
        {
            double[] rest= find_rest();
            double[] rale= findFarest(rest);
            
            
            return (Vector.magnitude(Vector.subtract(rest, rale))>ConvergenceRatio);
            
        }
        
        protected void alpha_beta_asigminet()
        {
            if (setState()){
                beta=0;
                alpha= -mi/delta_t;
            }
            else{
                beta=beta0;
                alpha= alpha +g*delta_t;
            }
           if (N==2000)
           {
               g=1;
           }
           
        }
	
        
        @Override
	protected void iterate() {
                alpha_beta_asigminet();
		updateVelocities();
		updatePositions();
		mutate();
		
		evaluateAll(particles);
		
		updateLocalBest();
		leaders.addAll(particles);
		leaders.update();
                sumR();
		
		if (archive != null) {
			archive.addAll(particles);
		}
	}
        
        @Override
	protected void updateVelocity(int i) {
		Solution particle = particles[i];
		Solution localBestParticle = localBestParticles[i];
		Solution leader = selectLeader();
		
                
                double alpha=0;
                double beta=0;
                
                
		double r1 = PRNG.nextDouble();
		double r2 = PRNG.nextDouble();
		double C1 = PRNG.nextDouble(1.5, 2.5);
		double C2 = PRNG.nextDouble(1.5, 2.5);
		double W = PRNG.nextDouble(0.1, 0.1);

		for (int j = 0; j < problem.getNumberOfVariables(); j++) {
			double particleValue = EncodingUtils.getReal(particle.getVariable(j));
			double localBestValue = EncodingUtils.getReal(localBestParticle.getVariable(j));
			double leaderValue = EncodingUtils.getReal(leader.getVariable(j));
			
			double velocity = constrictionCoefficient(C1, C2) * 
					(W * velocities[i][j] + 
					C1*r1*(localBestValue - particleValue) +
					C2*r2*(leaderValue - particleValue));
                        
			velocity= (alpha- beta*Math.pow(velocities[i][j],2))*velocities[i][j];
                        velocity +=(-(a*(particleValue- (1/swarmSize)*sums[j]))- 1*C2*r2*(leaderValue - particleValue) );
                        velocity *=delta_t/mi;
                        velocity= velocities[i][j]+constrictionCoefficient(C1, C2) * velocity;
                        if (Double.isNaN(velocity)) {
                            velocity=0;

                        }
			if (velocity > maximumVelocity[j]) {
				velocity = maximumVelocity[j];
			} else if (velocity < minimumVelocity[j]) {
				velocity = minimumVelocity[j];
			}
			
			velocities[i][j] = velocity;
		}
	}
	
	/**
	 * Returns the velocity constriction coefficient.
	 * 
	 * @param c1 the velocity coefficient for the local best
	 * @param c2 the velocity coefficient for the leader
	 * @return the velocity constriction coefficient
	 */
	protected double constrictionCoefficient(double c1, double c2) {
		double rho = c1 + c2;
		
		if (rho <= 4) {
			return 1.0;
		} else {
			return 2.0 / (2.0 - rho - Math.sqrt(Math.pow(rho, 2.0) - 4.0 * rho));
		}
	}

	@Override
	protected void mutate(int i) {
		// The SMPSO paper [1] states that mutation is applied 15% of the time,
		// but the JMetal implementation applies to every 6th particle.  Should
		// the application of mutation be random instead?
		if (i % 6 == 0) {
			particles[i] = mutation.evolve(new Solution[] { particles[i] })[0];
		}
	}

    private void sumR() {
        double []sum=new double[problem.getNumberOfVariables()];
        
        for (int i=0; i<swarmSize; i++) {
            for (int j=0; j<problem.getNumberOfVariables(); j++){
                sum[j]+= EncodingUtils.getReal(particles[i].getVariable(j));
            }
          
        }
        sums=sum;
    }


}
