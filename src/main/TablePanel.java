package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import api.products.ProductSearch;
import api.search.crossreference.CrossReference;
import api.search.requests.RequestsSearch;
import api.search.torrents.TorrentSearch;

/**
 * The Class TablePanel.
 * 
 * @author Gwindow
 */
public final class TablePanel extends JPanel implements MouseListener {

	/** The Constant COLUMN_NAMES. */
	private static final String COLUMN_NAMES[] = { "Barcode", "Product Name", "Torrents", "Requests" };

	/** The table. */
	private final JTable table;

	/** The header. */
	private final JTableHeader header;

	/** The default table model. */
	private final DefaultTableModel defaultTableModel;

	/** The pane. */
	private final JScrollPane pane;

	private JButton loadBarcodesButton;

	private JButton lookUpBarcodesButton;

	private JButton crossReferenceButton;

	private JButton barcodeButton;

	/**
	 * Instantiates a new table panel.
	 */
	public TablePanel() {
		defaultTableModel = new DefaultTableModel(COLUMN_NAMES, 0);
		table = new JTable(defaultTableModel);
		header = table.getTableHeader();
		pane = new JScrollPane(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.addMouseListener(this);
		setLayout(new BorderLayout());
		add(pane, BorderLayout.CENTER);
		initButtons();
	}

	/**
	 * Inits the buttons.
	 */
	private final void initButtons() {
		final JPanel buttonPanel = new JPanel();
		loadBarcodesButton = new JButton("Load Barcodes");
		loadBarcodesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadBarcodes();
			}

		});
		lookUpBarcodesButton = new JButton("Look Up Barcodes");
		lookUpBarcodesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				lookUpBarcodes();
			}

		});
		crossReferenceButton = new JButton("Cross Reference");
		crossReferenceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				crossReference();
			}

		});
		barcodeButton = new JButton("Enter barcode");
		barcodeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String barcode="";
				do{
					barcode = JOptionPane.showInputDialog("Enter Barcode");
					
					if(!barcode.equals("")){
						defaultTableModel.addRow(new Object[] { barcode });
					}
				}while(!barcode.equals(""));
			}
		});
		
		buttonPanel.add(loadBarcodesButton);
		buttonPanel.add(lookUpBarcodesButton);
		buttonPanel.add(crossReferenceButton);
		buttonPanel.add(barcodeButton);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Load barcodes.
	 */
	private final void loadBarcodes() {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(this);
		final FileReader fileReader = new FileReader();
		try {
			final List<String> list = fileReader.readBarcodes(fileChooser.getSelectedFile());
			for (int i = 0; i < list.size(); i++) {
				defaultTableModel.addRow(new Object[] { list.get(i) });
			}
		} catch (IOException e) {
		}
		defaultTableModel.fireTableDataChanged();
	}

	/**
	 * Look up barcodes.
	 */
	private final void lookUpBarcodes() {
		lookUpBarcodesButton.setText("Looking...");
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				final int size = defaultTableModel.getRowCount();
				for (int i = 0; i < size; i++) {
					final ProductSearch ps = ProductSearch.productSearchFromUPC(defaultTableModel.getValueAt(i, 0).toString());
					defaultTableModel.setValueAt(CrossReference.determineSearchString(ps.getItems()), i, 1);
				}
				lookUpBarcodesButton.setText("Look Up Barcodes");
			}
		});
		thread.start();
	}

	/**
	 * Cross reference.
	 */
	private final void crossReference() {
		crossReferenceButton.setText("Crossing...");
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				final int size = defaultTableModel.getRowCount();
				for (int i = 0; i < size; i++) {
					final String searchTerm = defaultTableModel.getValueAt(i, 1).toString();
					final TorrentSearch ts = TorrentSearch.torrentSearchFromSearchTerm(searchTerm);
					final RequestsSearch rs = RequestsSearch.requestSearchFromSearchTerm(searchTerm);
					if (ts.getStatus() == true) {
						if (ts.getResponse().getResults() != null) {
							defaultTableModel.setValueAt("Found: " + ts.getResponse().getResults().size(), i, 2);
						}
					}
					if (rs.getStatus() == true) {
						if (rs.getResponse().getResults() != null) {
							defaultTableModel.setValueAt("Found: " + rs.getResponse().getResults().size(), i, 3);
						}
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
				}
				crossReferenceButton.setText("Cross Reference");
			}
		});
		thread.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
