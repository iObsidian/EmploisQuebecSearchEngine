package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringUtils;

import utils.StringUtil;

public class JobTable extends JPanel implements ActionListener {

	JobVisualiser j;

	/** currently selected File. */
	public ArrayList<Job> selectedFiles = new ArrayList<Job>();

	/** Directory listing */
	private JTable table;

	/** Table model for File array. */
	private EmploiTableModel emploiTableModel;
	private ListSelectionListener listSelectionListener;
	private boolean cellSizesSet = false;

	/** Popup menu */

	private JPopupMenu popupMenu = new JPopupMenu();

	List<Job> emplois;

	public JobTable(JobVisualiser j) {

		this.j = j;

		setLayout(new BorderLayout(3, 3));
		setBorder(new EmptyBorder(5, 5, 5, 5));

		table = new JTable() {

			//Allows for deslection on click
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
				super.changeSelection(rowIndex, columnIndex, !extend, extend);
			}

		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.setShowVerticalLines(false);
		/**table.setSelectionBackground(Color.CYAN);
		table.setSelectionForeground(Color.ORANGE);*/

		table.setComponentPopupMenu(popupMenu);

		emploiTableModel = new EmploiTableModel();
		table.setModel(emploiTableModel);

		//set font bold for column 1 (see CellRenderer at the bottom of this class)
		//table.getColumnModel().getColumn(1).setCellRenderer(new CellRenderer());

		listSelectionListener = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();

				boolean isAdjusting = e.getValueIsAdjusting();

				if (!lsm.isSelectionEmpty()) {
					// Find out which indexes are selected.
					int minIndex = lsm.getMinSelectionIndex();
					int maxIndex = lsm.getMaxSelectionIndex();

					selectedFiles.clear();

					for (int i = minIndex; i <= maxIndex; i++) {
						if (lsm.isSelectedIndex(i)) {

							//Fixes row being incorrect after sortings
							int row = table.convertRowIndexToModel(i);

							selectedFiles.add(emploiTableModel.getFile(row));
						}
					}

					if (!isAdjusting) { //avoids performing twice
						if (selectedFiles.size() == 1) { //amount of selected emplois == 1

							System.out.println("Selection...");

							j.setJob(selectedFiles.get(0));
						}

					}

				}

			}

		};
		table.getSelectionModel().addListSelectionListener(listSelectionListener);

		JScrollPane tableScroll = new JScrollPane(table);
		Dimension d = tableScroll.getPreferredSize();
		tableScroll.setPreferredSize(new Dimension((int) d.getWidth(), (int) d.getHeight() / 2));
		add(tableScroll, BorderLayout.CENTER);

		/**List<Emploi> e = new ArrayList<Emploi>();
		
		Emploi a = new Emploi();
		a.annesDexperience = "1";
		
		e.add(a);
		setTableData(e, false);*/

	}

	/** Update the table on the EDT
	 * 
	 * isFiltered = is not the actual files, but a variant that is filtered
	 *  
	 *  */
	public void setTableData(List<Job> newFiles, boolean isFiltered) {

		if (!isFiltered) {
			emplois = newFiles;
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (emploiTableModel == null) {
					emploiTableModel = new EmploiTableModel();
					table.setModel(emploiTableModel);
				}
				table.getSelectionModel().removeListSelectionListener(listSelectionListener);
				emploiTableModel.setFiles(newFiles);
				table.getSelectionModel().addListSelectionListener(listSelectionListener);
				if (!cellSizesSet) {

					table.setRowHeight(30);
					setColumnWidth(0, -1);
					cellSizesSet = true;
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() instanceof JMenuItem) {
			JMenuItem menu = (JMenuItem) event.getSource();

		}

	}

	private void setColumnWidth(int column, int width) {
		TableColumn tableColumn = table.getColumnModel().getColumn(column);
		if (width < 0) {
			// use the preferred width of the header..
			JLabel label = new JLabel((String) tableColumn.getHeaderValue());
			Dimension preferred = label.getPreferredSize();
			// altered 10->14 as per camickr comment.
			width = (int) preferred.getWidth() + 14;
		}
		tableColumn.setPreferredWidth(width);
		tableColumn.setMaxWidth(width);
		tableColumn.setMinWidth(width);
	}

	public ArrayList<Job> updateAdvancedSearchChanged(AdvancedSearch advancedSearch) {

		ArrayList<Job> newEmplois = new ArrayList<Job>();

		for (Job emploi : emplois) {
			if (advancedSearch.accept(emploi)) {
				newEmplois.add(emploi);
			}
		}

		setTableData(newEmplois, true);

		return newEmplois;
	}

}

class EmploiTableModel extends AbstractTableModel {

	private static final String TAG = "FileTableModel";
	private List<Job> emplois;
	private String[] columns = { "N° de l'offre", "Appellation d'emploi", "Employeur", "Nombre de poste(s)", "Scolarité", "Années d'expérience", "Lieu de travail" };

	EmploiTableModel() {
		emplois = new ArrayList<Job>();
	}

	public Object getValueAt(int row, int column) {

		/**
		 * 0 = N° de l'offre
		 * 1 = Appellation d'emploi (& URL)
		 * 2 = Employeur
		 * 3 = Nombre de poste(s)
		 * 4 = Scolarité
		 * 5 = Années d'expérience
		 * 6 = Lieu de travail
		 */

		Job emploi = emplois.get(row);
		switch (column) {
		case 0:
			return emploi.numeroDeLoffre;
		case 1:
			return StringUtil.capitalise(emploi.appellationDeLemploi);
		case 2:
			return emploi.employeur;
		case 3:
			return emploi.nombreDePostes;
		case 4:
			return emploi.scolarite;
		case 5:
			return emploi.annesDexperience;
		case 6:
			return emploi.lieuDeTravail;
		default:
			System.err.println("Logic Error");
		}
		return "";
	}

	public int getColumnCount() {
		return columns.length;
	}

	public Class<?> getColumnClass(int column) {
		/**switch (column) {
		case 0:
			return ImageIcon.class;
		case 3:
			return Long.class;
		case 4:
			return Date.class;
		}
		return String.class;*/

		return String.class;
	}

	public String getColumnName(int column) {
		return columns[column];
	}

	public int getRowCount() {
		return emplois.size();
	}

	public Job getFile(int row) {
		return emplois.get(row);
	}

	public void setFiles(List<Job> emplois) {
		this.emplois = emplois;
		fireTableDataChanged();
	}

	public List<Job> getFiles() {
		return emplois;
	}

	public void addFile(Job emploi) {
		emplois.add(emploi);
		fireTableDataChanged();
	}

}

class CellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// if (value>17 value<26) {
		this.setValue(table.getValueAt(row, column));
		this.setFont(this.getFont().deriveFont(Font.ROMAN_BASELINE));
		//}
		return this;
	}
}