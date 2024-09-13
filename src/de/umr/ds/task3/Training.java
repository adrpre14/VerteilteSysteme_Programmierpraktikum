package de.umr.ds.task3;

import java.util.Collections;

public class Training {

	private static final double alpha = 0.05; // learning rate
	private static final int epochs = 100; // number of epochs

	/**
	 * A perceptron is trained on a dataset. After each epoch the perceptron's
	 * parameters are updated, the dataset is shuffled and the accuracy is computed.
	 * 
	 * @param perceptron the perceptron to train
	 * @param dataset the training dataset
	 */
	private static void train(Perceptron perceptron, Dataset dataset) {
		Visualization visualization = new Visualization(dataset, perceptron.getWeightVector(), perceptron.getBias());
		for (int epoch = 0; epoch < epochs; epoch++) {
			Vector wVector = perceptron.getWeightVector();
			double b = perceptron.getBias();
			for (DataPoint x : dataset) {
				int t = x.getLabel();
				int p = perceptron.predict(x);
				if (t != p) {
					double[] newWeights = new double[wVector.numDims()];
					for (int i = 0; i < wVector.numDims(); i++) {
						newWeights[i] = wVector.getDim(i) + (alpha * (t - p) * x.getDim(i));
					}
					wVector = new Vector(newWeights);
					b += alpha * (t - p);
				}
			}

			perceptron.setWeightVector(wVector);
			perceptron.setBias(b);
			visualization.update(perceptron.getWeightVector(), perceptron.getBias(), epoch);
			System.out.println("Epoch " + epoch + " Accuracy: " + Evaluation.accuracy(perceptron, dataset) * 100 + "%");
			Collections.shuffle(dataset);
		}
	}

	public static void main(String[] args) {

		Dataset dataset = new Dataset(1000);
		Perceptron perceptron = new Perceptron();
		train(perceptron, dataset);

	}

}
