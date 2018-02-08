package a8;

public class GrayPixel implements Pixel {

	private double intensity;

	private static final char[] PIXEL_CHAR_MAP = { '#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' ' };

	public GrayPixel(double intensity) {
		if (intensity < 0.0 || intensity > 1.0) {
			throw new IllegalArgumentException("Intensity of gray pixel is out of bounds.");
		}
		this.intensity = intensity;
	}

	@Override
	public double getRed() {
		return getIntensity();
	}

	@Override
	public double getBlue() {
		return getIntensity();
	}

	@Override
	public double getGreen() {
		return getIntensity();
	}

	@Override
	public double getIntensity() {
		return intensity;
	}

	@Override
	public char getChar() {
		return PIXEL_CHAR_MAP[(int) (getIntensity() * 10.0)];
	}

	public Pixel blend(Pixel p, double weight) {

		if (weight > 1.0 || weight < 0.0) {
			throw new RuntimeException("Explanation string");
		}

		double intensity = (p.getIntensity() * weight) + (this.getIntensity() * (1 - weight));
		return new GrayPixel(intensity);

	}

	public Pixel lighten(double factor) {

		if (factor == 1.0) {
			return new GrayPixel(1.0);
		} else if (factor == 0.0) {
			return this;
		} else if (factor > 1.0 || factor < 0.0) {
			throw new RuntimeException("Explanation string");
		}

		else {
			double intensity = this.blend(new GrayPixel(1.0), factor).getIntensity();
			return new GrayPixel(intensity);
		}

	}

	public Pixel darken(double factor) {
		if (factor == 1.0) {
			return new GrayPixel(0.0);
		} else if (factor == 0.0) {
			return this;
		} else if (factor > 1.0 || factor < 0.0) {
			throw new RuntimeException("Explanation string");
		} else {
			double intensity = this.blend(new GrayPixel(0.0), factor).getIntensity();
			return new GrayPixel(intensity);
		}

	}

}
