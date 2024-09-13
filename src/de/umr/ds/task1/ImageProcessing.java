package de.umr.ds.task1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ImageProcessing {

	/**
	 * Loads an image from the given file path
	 *
	 * @param path The path to load the image from
	 * @return BufferedImage
	 */
	private static BufferedImage loadImage(String path) throws IOException {

		// TODO Task 1a)

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
			System.out.println("Image loaded successfully");
		} catch (IOException e) {
			System.out.println("failed to load image");
			e.printStackTrace();
		}
		return img;
	}


	/**
	 * Converts the input RGB image to a single-channel gray scale array.
	 *
	 * @param img The input RGB image
	 * @return A 2-D array with intensities
	 */
	private static int[][] convertToGrayScaleArray(BufferedImage img) {

		// TODO Task 1b)
		int width = img.getWidth();
		int height = img.getHeight();
		int[][] grayScaleArray = new int[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				int rgb = img.getRGB(x, y);
				Color color = new Color(rgb, true);

				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();

				int gray = (int)(0.299 * red + 0.587 * green + 0.114 * blue);

				grayScaleArray[x][y] = gray;
			}
		}
		return grayScaleArray;
	}

	/**
	 * Converts a single-channel (gray scale) array to an RGB image.
	 *
	 * @param img The input image array
	 * @return BufferedImage
	 */
	public static BufferedImage convertToBufferedImage(int[][] img) {

		// TODO Task 1c)
		int width = img.length;
		int height = img[0].length;
		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				int gray = img[x][y];
				int rgb = new Color(gray, gray, gray).getRGB();

				grayImage.setRGB(x, y, rgb);
			}
		}

		return grayImage;
	}

	/**
	 * Saves an image to the given file path
	 *
	 * @param img The RGB image
	 * @param path The path to save the image to
	 */
	private static void saveImage(BufferedImage img, String path) throws IOException {

		// TODO Task 1c)
		try {
			File outputfile = new File(path);
			ImageIO.write(img, "jpg", outputfile);
			System.out.println("Image saved successfully to " + path);
		} catch (IOException e) {
			System.out.println("Failed to save image");
			e.printStackTrace();
		}
	}

	/**
	 * Converts input image to gray scale and applies the kernel.
	 *
	 * @param img The RGB input image
	 * @param kernel The kernel to apply
	 * @return The convolved gray-scale image
	 */
	private static BufferedImage filter(BufferedImage img, Kernel kernel) {
		int[][] grayScaleArray = convertToGrayScaleArray(img);
		int imgWidth = grayScaleArray.length;
		int imgHeight = grayScaleArray[0].length;
		int padWidth = kernel.getWidth() / 2;
		int padHeight = kernel.getHeight() / 2;

		// Create a padded image
		int[][] paddedImg = new int[imgWidth + 2 * padWidth][imgHeight + 2 * padHeight];
		for (int x = 0; x < imgWidth; x++) {
			for (int y = 0; y < imgHeight; y++) {
				paddedImg[x + padWidth][y + padHeight] = grayScaleArray[x][y];
			}
		}
		int[][] convolvedArray = kernel.convolve(paddedImg);
		return convertToBufferedImage(convolvedArray);
	}


	public static void main(String[] args) throws IOException {

		String inputPath = Path.of("example.jpg").toAbsolutePath().toString();
		String outputPath = Path.of("output_example.jpg").toAbsolutePath().toString();

		BufferedImage img = ImageProcessing.loadImage(inputPath);

		BufferedImage convolvedImage = ImageProcessing.filter(img, Kernels.Embossing());

		ImageProcessing.saveImage(convolvedImage, outputPath);

	}

}