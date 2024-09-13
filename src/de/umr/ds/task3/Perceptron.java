package de.umr.ds.task3;

import java.util.Random;

/**
 * A Perceptron holds weights and bias and can be applied to a data vector to
 * predict its class. Weights and bias are initialized randomly.
 */
public class Perceptron {

	private Vector weightVector;
    private double bias;

    public Perceptron() {
        Random random = new Random();
        bias = random.nextDouble();
        weightVector = new Vector(random.doubles(2).toArray());
    }

    public Vector getWeightVector() {
        return weightVector;
    }

    public double getBias() {
        return bias;
    }

    public void setWeightVector(Vector weightVector) {
        this.weightVector = weightVector;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    /**
     * Apply the perceptron to classify a data vector.
     *
     * @param x An input vector
     * @return 0 or 1
     */
    public int predict(Vector x) {
        return (weightVector.dot(x) + bias) > 0 ? 1 : 0;
    }
}
