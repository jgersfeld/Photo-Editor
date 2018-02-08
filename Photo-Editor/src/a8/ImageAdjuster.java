package a8;

import java.io.IOException;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageAdjuster {

	public static void main(String[] args) throws IOException {

		// sets picture from google chrome to variable p
		Picture p = A8Helper
				.readFromURL("https://target.scene7.com/is/image/Target/50756361?wid=520&hei=520&fmt=pjpeg");

		ImageAdjusterView imaj = new ImageAdjusterView(p);

		// JFrame is the window that opens when the application is run
		// it contains JPanels and JLabels
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Assignment 8 Simple Image Adjuster");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(imaj, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);

		main_frame.pack();
		main_frame.setVisible(true);

	}

}
