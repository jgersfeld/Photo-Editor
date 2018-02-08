package a8;

import java.util.Iterator;

public interface Picture {

	int getWidth();

	int getHeight();

	Pixel getPixel(int x, int y);

	Pixel getPixel(Coordinate c);

	void setPixel(int x, int y, Pixel p);

	void setPixel(Coordinate c, Pixel p);

	SubPicture extract(int xoff, int yoff, int width, int height);

	SubPicture extract(Coordinate a, Coordinate b);

	SubPicture extract(Region r);

	ObservablePicture createObservable();

	public Iterator<SubPicture> tile(int i, int j);

	boolean equalss(Picture p);
}
