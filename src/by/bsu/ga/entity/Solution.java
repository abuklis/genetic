package by.bsu.ga.entity;

import by.bsu.ga.util.SolutionUtil;

import java.util.Arrays;
/**
 * Created by anyab on 11.04.2017.
 */
public class Solution implements Comparable<Solution>{
    private static final int SIZE = 3;
    private int [] variables = new int [SIZE];

    public Solution() {
        super();
    }

    public int[] getVariables() {
        return variables;
    }

    public void setVariables(int[] variables) {
        this.variables = variables;
    }

    public void setVariable(int index, int variable) {
        this.variables[index] = variable;
    }

    public int compareTo(Solution solution) {
        int f1 = SolutionUtil.calculateFitness(this);
        int f2 = SolutionUtil.calculateFitness(solution);
        return f1 - f2;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "variables=" + Arrays.toString(variables) +
                ", fitness = " + SolutionUtil.calculateFitness(this) + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Solution)) return false;
        Solution solution = (Solution) o;
        return Arrays.equals(variables, solution.variables);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(variables);
    }
}
