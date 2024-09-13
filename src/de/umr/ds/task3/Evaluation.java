package de.umr.ds.task3;

public class Evaluation {

	/**
	 * Applies the model to each data vector in the dataset and computes the
	 * accuracy.
	 * 
	 * @return accuracy
	 */
	public static double accuracy(Perceptron model, Dataset dataset) {
		int correctPredictions = 0;

		for (DataPoint dp : dataset) {
			if (model.predict(dp) == dp.getLabel()) {
				correctPredictions++;
			}
		}

		return (double) correctPredictions / dataset.size();
	}

}
