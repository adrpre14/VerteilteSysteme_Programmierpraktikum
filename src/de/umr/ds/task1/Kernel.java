package de.umr.ds.task1;

/**
 * A kernel is a 2D-array. The array is transposed after initialization which
 * enables a more intuitive way of initializing a kernel. E.g a non-symmetric
 * kernel can be initialized by Kernel({{0,0,1} {0,1,0} {1,0,0}}) although the
 * array dimensions are actually [height][width].
 *
 */
public class Kernel {

	private double[][] k;
	private int height;
	private int width;

	/**
	 * Initializes the kernel by its transpose.
	 *
	 * @param k 2D array
	 */
	Kernel(double[][] k) {
		// transpose
		this.k = new double[k[0].length][k.length];
		for (int x = 0; x < k[0].length; x++)
			for (int y = 0; y < k.length; y++)
				this.k[x][y] = k[y][x];
		this.width = this.k.length;
		this.height = this.k[0].length;

		if (this.width % 2 != 1 || this.height % 2 != 1)
			throw new IllegalArgumentException("Kernel size need to be odd-numbered");
		if (this.width < 3 || this.height < 3)
			throw new IllegalArgumentException("Minimum dimension is 3");
	}

	/**
	 * Convolves a single-channel image with the kernel.
	 *
	 * @param img A single-channel image
	 * @return The convolved image
	 */
	public int[][] convolve(int[][] img) {
		int imgWidth = img.length;
		int imgHeight = img[0].length;
		int[][] output = new int[imgWidth - width + 1][imgHeight - height + 1];

		// Apply the kernel to each pixel in the output image
		for (int x = 0; x < output.length; x++) {
			for (int y = 0; y < output[0].length; y++) {
				double sum = 0.0;

				// Multiply the kernel with the corresponding image values
				for (int i = 0; i < width; i++) {
					for (int j = 0; j < height; j++) {
						int imgX = x + i;
						int imgY = y + j;
						sum += img[imgX][imgY] * k[i][j];
					}
				}

				// Clamp the value to the 0-255 range to prevent overflow
				int value = (int) Math.round(sum);
				value = Math.max(0, Math.min(255, value));

				output[x][y] = value;
			}
		}

		return output;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}