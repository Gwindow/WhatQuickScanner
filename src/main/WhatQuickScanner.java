package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import api.soup.MySoup;
import api.util.CouldNotLoadException;

/**
 * The Class Main.
 * 
 * @author Gwindow
 */
public final class WhatQuickScanner {

	/** The Constant SITE. */
	private static final String SITE = "ssl.what.cd";

	/** The frame. */
	private JFrame frame;

	/**
	 * Instantiates a new main.
	 */
	public WhatQuickScanner() {
		init();
		showLoginPrompt();
	}

	/**
	 * Inits the.
	 */
	private void init() {
		frame = new JFrame("WhatQuickScanner");
		frame.setJMenuBar(new Menu());
		frame.setLayout(new BorderLayout());
		frame.add(new TablePanel(), BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Show login prompt.
	 */
	private void showLoginPrompt() {
		String username = JOptionPane.showInputDialog("Enter Username");
		String password = JOptionPane.showInputDialog("Enter Password");
		try {
			MySoup.setSite(SITE);
			MySoup.login("login.php", username, password);
			JOptionPane.showMessageDialog(null, "Logged in");
		} catch (CouldNotLoadException e) {
			JOptionPane.showMessageDialog(null, "Could not login");
			int selection = JOptionPane.showConfirmDialog(null, "Try again?");
			if (selection == JOptionPane.YES_OPTION) {
				showLoginPrompt();
			} else {
				System.exit(0);
			}
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		new WhatQuickScanner();
	}
}
