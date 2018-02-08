package a8;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class FramePuzzleView extends JPanel implements MouseListener, KeyListener {

	private PictureView[][] puzzle;
	private Picture pic;
	private Picture white_tile;

	public FramePuzzleView(Picture p) {

		setLayout(new GridLayout(5, 5));
		pic = p;
		puzzle = new PictureView[5][5];
		white_tile = new PictureImpl(p.getWidth() / 5, p.getHeight() / 5);

		int q = 0;
		int j = 0;

		for (int z = 0; z < p.getHeight(); z = z + p.getHeight() / 5) {

			for (int i = 0; i < p.getWidth(); i = i + p.getWidth() / 5) {

				if (i + p.getWidth() / 5 > p.getWidth()) {
					break;
				} else if (z + p.getHeight() / 5 > p.getHeight()) {
					break;
				}

				Picture piece = pic.extract(i, z, p.getWidth() / 5, p.getHeight() / 5);

				puzzle[q][j] = new PictureView(piece.createObservable());
				puzzle[q][j].addMouseListener(this);
				puzzle[q][j].addKeyListener(this);
				add(puzzle[q][j]);
				j++;
			}
			q++;
			j = 0;
		}

		puzzle[4][4].setPicture(white_tile.createObservable());

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		int xpos = 0;
		int ypos = 0;

		for (int i = 0; i < puzzle.length; i++) {
			for (int z = 0; z < puzzle[0].length; z++) {
				if (puzzle[i][z].getPicture().equalss(white_tile)) {
					xpos = i;
					ypos = z;
				}
			}
		}

		if (key == 37) {
			try {

				PictureView temp = puzzle[xpos][ypos - 1];
				puzzle[xpos][ypos].setPicture(temp.getPicture().createObservable());
				puzzle[xpos][ypos - 1].setPicture(white_tile.createObservable());
			} catch (RuntimeException oops) {
				// do nothing

			}

		} else if (key == 38) {
			// up
			try {

				PictureView temp = puzzle[xpos - 1][ypos];
				puzzle[xpos][ypos].setPicture(temp.getPicture().createObservable());
				puzzle[xpos - 1][ypos].setPicture(white_tile.createObservable());
			} catch (RuntimeException oops) {
				// do nothing
			}
		} else if (key == 39) {
			// right
			try {
				PictureView temp = puzzle[xpos][ypos + 1];
				puzzle[xpos][ypos].setPicture(temp.getPicture().createObservable());
				puzzle[xpos][ypos + 1].setPicture(white_tile.createObservable());
			} catch (RuntimeException oops) {
				// do nothing
			}
		} else if (key == 40) {
			// down
			try {
				PictureView temp = puzzle[xpos + 1][ypos];
				puzzle[xpos][ypos].setPicture(temp.getPicture().createObservable());
				puzzle[xpos + 1][ypos].setPicture(white_tile.createObservable());
			} catch (RuntimeException oops) {
				// do nothing
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// code
		int whiteXpos = 0;
		int whiteYpos = 0;
		int clickXpos = 0;
		int clickYpos = 0;

		for (int i = 0; i < puzzle.length; i++) {
			for (int z = 0; z < puzzle[0].length; z++) {
				if (puzzle[i][z].getPicture().equalss(white_tile)) {
					whiteXpos = i;
					whiteYpos = z;

				}
				if (e.getSource().equals(puzzle[i][z])) {
					clickXpos = i;
					clickYpos = z;
				}
			}
		}

		// need to shift tiles
		// can use local variables to gain values
		// need only one for loop
		// might only need one difference value...stay tuned

		// create try catch to prevent diag

		int Xdif = whiteXpos - clickXpos;
		int Ydif = whiteYpos - clickYpos;

		if (Ydif != 0 && Xdif == 0) {
			// moving left and right

			if (Ydif > 0) {

				for (int i = 0; i < Ydif; i++) {
					// moves horizontally left
					PictureView temp = puzzle[whiteXpos][whiteYpos - i - 1];
					puzzle[whiteXpos][whiteYpos - i].setPicture(temp.getPicture().createObservable());

				}
			} else {
				// moves horizontally right
				for (int i = 0; i < Math.abs(Ydif); i++) {
					PictureView temp = puzzle[whiteXpos][whiteYpos + i + 1];
					puzzle[whiteXpos][whiteYpos + i].setPicture(temp.getPicture().createObservable());
				}

			}

		} else if (Xdif != 0 && Ydif == 0) {
			// moving up and down
			if (Xdif > 0) {
				// moves up
				for (int i = 0; i < Xdif; i++) {
					PictureView temp = puzzle[whiteXpos - i - 1][whiteYpos];
					puzzle[whiteXpos - i][whiteYpos].setPicture(temp.getPicture().createObservable());
				}
			} else {
				// moves down
				for (int i = 0; i < Math.abs(Xdif); i++) {
					PictureView temp = puzzle[whiteXpos + i + 1][whiteYpos];
					puzzle[whiteXpos + i][whiteYpos].setPicture(temp.getPicture().createObservable());
				}
			}

		}

		if (Xdif != 0 && Ydif != 0) {

		} else {

			puzzle[clickXpos][clickYpos].setPicture(white_tile.createObservable());
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
