package net.osomahe.photorenamer;

import javax.swing.UIManager;

import net.osomahe.photorenamer.gui.MainFrame;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new MainFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
