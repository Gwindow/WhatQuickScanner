package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import api.products.ProductSearch;

/**
 * The Class Menu.
 * 
 * @author Gwindow
 */
public final class Menu extends JMenuBar {
	/** The exit item. */
	private JMenuItem exitItem;

	/** The override item. */
	private JMenuItem overrideItem;

	private JMenuItem helpItem;

	/** The file menu. */
	private JMenu fileMenu;

	private JMenu helpMenu;

	/**
	 * Instantiates a new menu.
	 */
	public Menu() {
		init();
	}

	/**
	 * Inits the.
	 */
	private void init() {
		fileMenu = new JMenu("File");
		overrideItem = new JMenuItem("Override API Key");
		overrideItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ProductSearch.overrideAPIKey(JOptionPane.showInputDialog("Enter API Key"));
			}

		});
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		fileMenu.add(overrideItem);
		fileMenu.add(exitItem);
		add(fileMenu);

		helpMenu = new JMenu("Help");
		helpItem = new JMenuItem("Instructions");
		helpItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "1. Load Barcodes\n2. Look up Barcodes\n3. Cross Reference\n4. Enjoy");
			}

		});
		helpMenu.add(helpItem);
		add(helpMenu);
	}
}
