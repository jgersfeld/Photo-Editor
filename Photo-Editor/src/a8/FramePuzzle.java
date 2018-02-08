package a8;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FramePuzzle {

	public static void main(String[] args) throws IOException {

		// creates picture for use in program

		Picture p = A8Helper.readFromURL("http://a.abcnews.com/images/Entertainment/HT-borat2-ml-161103_4x3_992.jpg");

		FramePuzzleView doi = new FramePuzzleView(p);

		// sets up JFrame which holds the FramePuzzle widget

		JFrame main_frame = new JFrame();
		main_frame.setTitle("Assignment 8 Frame Puzzle");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// sets up JPanel which holds the FramePuzzle widget

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(doi, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);

		// makes main frame visible

		main_frame.pack();
		main_frame.setVisible(true);

	}

}
