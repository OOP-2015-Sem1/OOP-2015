package model;

import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;

import mainControllerPack.Constants;
import userInterfacePack.GamePanelP;

import java.awt.*;

public class ImageCreator extends Component {

	private Image image;
	private BufferedImage originalImage;
	private BufferedImage resizedImage;

	public ImageCreator(String pathImage, ArrayList<ButtonSegments> buttons, ButtonSegments lastButton)
			throws URISyntaxException {
		creatingSegments(pathImage, buttons, lastButton);
	}

	private void creatingSegments(String pathImage, ArrayList<ButtonSegments> buttons, ButtonSegments lastButton)
			throws URISyntaxException {

		try {
			originalImage = loadImage(pathImage);
			int height = getNewHeight(originalImage.getWidth(), originalImage.getHeight());
			resizedImage = resizeImage(originalImage, Constants.DESIRED_WIDTH, height, BufferedImage.TYPE_INT_ARGB);
		} catch (IOException ex) {
			System.out.println("Somtehing went wrong with the images" + ex.getStackTrace());

		}

		for (int i = 0; i < GamePanelP.rows; i++) {
			for (int j = 0; j < GamePanelP.cols; j++) {

				ImageProducer imageProducer = new FilteredImageSource(resizedImage.getSource(),
						new CropImageFilter(j * resizedImage.getWidth() / GamePanelP.rows,
								i * resizedImage.getHeight() / GamePanelP.cols,
								resizedImage.getWidth() / GamePanelP.rows, resizedImage.getHeight() / GamePanelP.cols));

				image = createImage(imageProducer);

				ButtonSegments button = new ButtonSegments(image);
				button.putClientProperty("position", new Point(i, j));

				if (i == (GamePanelP.rows - 1) && j == (GamePanelP.cols - 1)) {

					lastButton.setBorderPainted(false);
					lastButton.setContentAreaFilled(false);
					lastButton.setLastButton();
					lastButton.putClientProperty("position", new Point(i, j));
				} else {
					buttons.add(button);
				}
			}
		}

		shuffleButtons(buttons, lastButton);

	}

	private void shuffleButtons(ArrayList<ButtonSegments> buttons, ButtonSegments lastButton) {
		Collections.shuffle(buttons);
		buttons.add(lastButton);

	}

	private int getNewHeight(int width, int height) {

		double ratio = Constants.DESIRED_WIDTH / (double) width;
		int newHeight = (int) (height * ratio);
		return newHeight;
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) throws IOException {

		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D graphics = resizedImage.createGraphics();
		graphics.drawImage(originalImage, 0, 0, width, height, null);
		graphics.dispose();

		return resizedImage;
	}

	private BufferedImage loadImage(String pathImage) throws IOException, URISyntaxException {

		BufferedImage image = null;
		try {

			// image = ImageIO.read(new
			// File(getClass().getResource(pathImage).toURI()));

			image = ImageIO.read(new File(pathImage));

		} catch (IOException e) {
			System.out.println("" + e.getMessage());
		}

		return image;
	}

}