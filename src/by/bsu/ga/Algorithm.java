package by.bsu.ga;

import by.bsu.ga.entity.Solution;
import by.bsu.ga.util.SolutionUtil;

import java.util.*;
import static by.bsu.ga.util.Constant.*;

/**
 * Created by anyab on 11.04.2017.
 */
public class Algorithm {
    List<Solution> population = new ArrayList<>();
    private static final Random rand = new Random();

    public Algorithm() {
        for (int i = 0; i < POPULATION_AMOUNT; i++) {
            Solution solution = new Solution();
            int[] variables = SolutionUtil.generateRandomPopulation(MIN_RANDOM, MAX_RANDOM);
            solution.setVariables(variables);
            population.add(solution);
        }
        Collections.sort(population);
    }

    public void doAlgorithm() {
        int generationCount = 0;
        int occurrences = 1;

        while (occurrences != POPULATION_AMOUNT) {
            generationCount++;
            System.out.println("==========================================");
            System.out.println("Generation №" + generationCount);
            growNewGeneration();
            System.out.println("New population :");
            population.forEach(System.out::println);
            System.out.println("==========================================");
            occurrences = Collections.frequency(population, population.get(0));
        }
        System.out.println("\nResult");
        population.forEach(System.out::println);
    }

    private void growNewGeneration() {
        Solution parent1;
        Solution parent2;
        do{
            parent1 = tournamentSelection(population);
            parent2 = tournamentSelection(population);
        } while(parent1.equals(parent2));
        System.out.println("Parents : " + parent1 + ", " + parent2);

        Solution child1, child2;
        child1 = crossover(parent1, parent2);
        child2 = crossover(parent2, parent1);

        System.out.println("Children : " + child1 + ", " + child2);
        mutate(child1);
        mutate(child2);

        substitution(child1, child2, parent1, parent2);
        Collections.sort(population);
    }


    private Solution tournamentSelection(List<Solution> population) {
        List<Solution> tournament = new ArrayList<>();
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int randomIndex = rand.nextInt(POPULATION_AMOUNT);
            tournament.add(i, population.get(randomIndex));
        }
        return SolutionUtil.getFittest(tournament);
    }

    private Solution crossover(Solution parent1, Solution parent2) {
        Solution child = new Solution();
        int [] array = new int[SOLUTION_SIZE];
        System.arraycopy(parent1.getVariables(), 0, array, 0, PIVOT);
        System.arraycopy(parent2.getVariables(), PIVOT, array, PIVOT, child.getVariables().length - 1);
        child.setVariables(array);
        return child;
    }

    private void mutate(Solution solution) {
        if (Math.random() <= MUTATION_PERCENT) {
            System.out.println("Mutation of solution " + solution + " ...");
            int index = rand.nextInt(SOLUTION_SIZE);
            int oneVariable = solution.getVariables()[index];
            while (oneVariable == solution.getVariables()[index]) {
                oneVariable = rand.nextInt(MAX_RANDOM_RANGE + 1);
            }
            solution.setVariable(index, oneVariable);
            System.out.println("Mutation result " + solution);
        }
    }

    private boolean isChildGood(Solution child, Solution parent){
        return SolutionUtil.calculateFitness(child) <= SolutionUtil.calculateFitness(parent);
    }

    private void substitution(Solution child1, Solution child2, Solution parent1, Solution parent2){
        if (isChildGood(child1, parent1) && isChildGood(child2, parent2) ){
            population.set(POPULATION_AMOUNT - 1, child1);
            population.set(POPULATION_AMOUNT - 2, child2);
        } else if (isChildGood(child1, parent1)) {
            population.set(POPULATION_AMOUNT - 1, child1);
        } else if (isChildGood(child2, parent2)){
            population.set(POPULATION_AMOUNT - 1, child2);
        }
    }
}
