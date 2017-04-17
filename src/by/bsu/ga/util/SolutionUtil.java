package by.bsu.ga.util;

import by.bsu.ga.entity.Solution;

import java.util.List;
import java.util.Random;

/**
 * Created by anyab on 15.04.2017.
 */
public class SolutionUtil {
    private static Random rand = new Random();

    public static int[] generateRandomPopulation(int min, int max) {
        int variables[] = new int[3];
        variables[0] = rand.nextInt(max - min + 1) + min;
        variables[1] = rand.nextInt(max - min + 1) + min;
        variables[2] = rand.nextInt(max - min + 1) + min;
        return variables;
    }

    public static Solution getFittest(List<Solution> population) {
        Solution fittest = population.get(0);
        for (Solution solution : population) {
            if (calculateFitness(fittest) <= calculateFitness(solution)) {
                fittest = solution;
            }
        }
        return fittest;
    }

    public static int calculateFitness(Solution solution) {
        int[] variables = solution.getVariables();
        int fitness = (int) (2*Math.pow(variables[0], 2) -
                3*Math.pow(variables[1], 3) + 4*Math.pow(variables[2], 2) - 30);
        return Math.abs(fitness);
    }
}
