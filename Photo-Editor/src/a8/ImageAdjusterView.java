package a8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageAdjusterView extends JPanel implements ChangeListener {

	private PictureView view;
	private Picture pic;
	private JSlider blur;
	private JSlider saturation;
	private JSlider brightness;
	private static double red;
	private static double green;
	private static double blue;

	public ImageAdjusterView(Picture p) {

		pic = p;
		setLayout(new BorderLayout());
		view = new PictureView(pic.createObservable());
		add(view, BorderLayout.CENTER);

		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new GridLayout(3, 2));

		JLabel label1 = new JLabel("Blur: ");
		blur = new JSlider(0, 5, 0);
		blur.setPreferredSize(new Dimension(200, 25));
		blur.addChangeListener(this);
		blur.setMajorTickSpacing(1);
		blur.setSnapToTicks(true);
		blur.setPaintTicks(true);
		sliderPanel.add(label1);
		sliderPanel.add(blur);

		JLabel label2 = new JLabel("Saturation: ");
		saturation = new JSlider(-100, 100, 0);
		saturation.setPreferredSize(new Dimension(200, 25));
		saturation.addChangeListener(this);
		saturation.setMajorTickSpacing(25);
		saturation.setPaintTicks(true);
		sliderPanel.add(label2);
		sliderPanel.add(saturation);

		JLabel label3 = new JLabel("Brightness: ");
		brightness = new JSlider(-100, 100, 0);
		brightness.setPreferredSize(new Dimension(200, 25));
		brightness.addChangeListener(this);
		brightness.setMajorTickSpacing(25);
		brightness.setPaintTicks(true);
		sliderPanel.add(label3);
		sliderPanel.add(brightness);

		add(sliderPanel, BorderLayout.SOUTH);

	}

	public void stateChanged(ChangeEvent e) {

		if (((JSlider) e.getSource()).getValueIsAdjusting()) {
			return;
		}

		int blur_factor = blur.getValue();
		double brightness_factor = brightness.getValue() / 100.0;
		double saturation_factor = saturation.getValue();
		Picture modpic = blur(pic, blur_factor);
		modpic = brighten(modpic, brightness_factor);
		modpic = saturate(modpic, saturation_factor);
		view.setPicture(modpic.createObservable());

	}

	public static Picture blur(Picture p, int value) {
		Picture modpic = new PictureImpl(p.getWidth(), p.getHeight());

		for (int i = 0; i < p.getWidth(); i++) {
			for (int z = 0; z < p.getHeight(); z++) {

				int count = 0;

				for (int q = i - value; q <= i + value; q++) {
					for (int x = z - value; x <= z + value; x++) {

						try {
							red += p.getPixel(q, x).getRed();
							green += p.getPixel(q, x).getGreen();
							blue += p.getPixel(q, x).getBlue();
							count++;
						} catch (RuntimeException e) {
							continue;
						}

					}
				}

				double avgRed = red / count;
				double avgGreen = green / count;
				double avgBlue = blue / count;

				modpic.setPixel(i, z, new ColorPixel(avgRed, avgGreen, avgBlue));
				avgRed = 0;
				avgGreen = 0;
				avgBlue = 0;
				count = 0;
				red = 0;
				green = 0;
				blue = 0;

			}

		}
		return modpic;

	}

	public static Picture brighten(Picture p, double value) {

		if (value >= 0) {

			Picture modpic = new PictureImpl(p.getWidth(), p.getHeight());

			for (int i = 0; i < p.getWidth(); i++) {
				for (int z = 0; z < p.getHeight(); z++) {

					modpic.setPixel(i, z, p.getPixel(i, z).lighten(value));

				}
			}
			return modpic;
		} else {

			Picture modpic = new PictureImpl(p.getWidth(), p.getHeight());
			for (int i = 0; i < p.getWidth(); i++) {
				for (int z = 0; z < p.getHeight(); z++) {
					modpic.setPixel(i, z, p.getPixel(i, z).darken(Math.abs(value)));
				}
			}

			return modpic;

		}

	}

	public static Picture saturate(Picture p, double value) {

		Picture modpic = new PictureImpl(p.getWidth(), p.getHeight());

		if (value < 0) {

			for (int i = 0; i < p.getWidth(); i++) {
				for (int z = 0; z < p.getHeight(); z++) {
					Pixel current = p.getPixel(i, z);
					double newRed = current.getRed()
							* ((1.0 + (value / 100.0)) - (current.getIntensity() * value / 100.0));
					double newGreen = current.getGreen()
							* ((1.0 + (value / 100.0)) - (current.getIntensity() * value / 100.0));
					double newBlue = current.getBlue()
							* ((1.0 + (value / 100.0)) - (current.getIntensity() * value / 100.0));
					modpic.setPixel(i, z, new ColorPixel(newRed, newGreen, newBlue));
				}
			}
		} else {
			for (int i = 0; i < p.getWidth(); i++) {
				for (int z = 0; z < p.getHeight(); z++) {
					Pixel current = p.getPixel(i, z);

					double winner = current.getRed() >= current.getGreen() ? current.getRed() : current.getGreen();
					double alpha = winner >= current.getBlue() ? winner : current.getBlue();

					double newRed = current.getRed() * (((alpha + ((1.0 - alpha) * (value / 100.0))) / alpha));
					double newGreen = current.getGreen() * (((alpha + ((1.0 - alpha) * (value / 100.0))) / alpha));
					double newBlue = current.getBlue() * (((alpha + ((1.0 - alpha) * (value / 100.0))) / alpha));
					modpic.setPixel(i, z, new ColorPixel(newRed, newGreen, newBlue));
				}
			}
		}
		return modpic;

	}

}
