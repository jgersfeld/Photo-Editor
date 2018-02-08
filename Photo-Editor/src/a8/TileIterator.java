package a8;

import java.util.Iterator;

public class TileIterator implements Iterator<SubPicture> {

	private Picture p;
	private int tileWidth;
	private int tileHeight;
	private int countX;
	private int countY;
	private int xMultiplier;
	private int yMultiplier;

	// the TileIterator extracts discrete SubPicture objects of a certain size
	// and shape. The WindowIterator overlaps pixels while this does not

	public TileIterator(Picture p, int tileWidth, int tileHeight) {
		this.p = p;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		xMultiplier = p.getWidth() / tileWidth;
		yMultiplier = p.getHeight() / tileHeight;
	}

	// uses a Multiplier to make sure tile extractions stay within photo
	// boundaries

	public boolean hasNext() {
		if (countY < yMultiplier - 1) {
			return true;
		} else {
			if (countX < xMultiplier) {
				return true;
			} else {
				return false;
			}
		}
	}

	public SubPicture next() {

		if (countX < xMultiplier) {
			countX++;
		} else if (countX >= xMultiplier && countY < yMultiplier - 1) {
			countY++;
			countX = 1;
		} else {
			throw new java.util.NoSuchElementException("there are no more elements to traverse through");
		}

		return p.extract((countX - 1) * tileWidth, countY * tileHeight, tileWidth, tileHeight);

	}

}
