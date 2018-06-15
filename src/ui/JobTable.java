package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.region.city.job.JobDTO;

public class JobTable extends JPanel implements ActionListener {

	private static final Logger log = LoggerFactory.getLogger(JobTable.class);

	public JobDTO selectedJob = null;

	/** Directory listing */
	private JTable table;

	/** Table model for Job array. */
	private JobTableModel jobTableModel;
	private ListSelectionListener listSelectionListener;
	private boolean cellSizesSet = false;

	/** Popup menu */

	private JPopupMenu popupMenu = new JPopupMenu();

	//


	public JobTable(List<JobDTO> jobs) {

		setLayout(new BorderLayout(3, 3));
		setBorder(new EmptyBorder(5, 5, 5, 5));

		table = new JTable();
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.setShowVerticalLines(false);

		final JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem deleteItem = new JMenuItem("Visit");
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					if (selectedJob != null && selectedJob.getUrl() != null) {
						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().browse(new URI(selectedJob.getUrl()));
						} else {
							log.error(
									"Could not automatically open browser with Job URL. Please visit this website manually : ");
							log.error(selectedJob.getUrl());
						}
					} else {
						log.error("Could not visit null job. Please select a job first.");
					}

				} catch (IOException e1) {
					log.error("Error while trying to open Job URL.");
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					log.error("Error while trying to open Job URL, invalid URL syntax.");
					e1.printStackTrace();
				}

			}
		});
		popupMenu.add(deleteItem);
		table.setComponentPopupMenu(popupMenu);

		table.setComponentPopupMenu(popupMenu);

		jobTableModel = new JobTableModel();
		table.setModel(jobTableModel);

		//set font bold for column 1 (see CellRenderer at the bottom of this class)
		table.getColumnModel().getColumn(1).setCellRenderer(new CellRenderer());

		listSelectionListener = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();

				boolean isAdjusting = e.getValueIsAdjusting();

				if (!lsm.isSelectionEmpty()) {
					// Find out which indexes are selected.
					int minIndex = lsm.getMinSelectionIndex();
					int maxIndex = lsm.getMaxSelectionIndex();

					for (int i = minIndex; i <= maxIndex; i++) {
						if (lsm.isSelectedIndex(i)) {

							//Fixes row being incorrect after sortings
							int row = table.convertRowIndexToModel(i);

							selectedJob = jobTableModel.getJob(row);
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

		//Search field

		

		setTableData(jobs);

	}

	/** Update the table on the EDT */
	void setTableData(List<JobDTO> jobs) {

		for (JobDTO j : jobs) {
			for (String data : j.getValues()) {
				log.info(data);

				//autoCompleteService.addData(data); //TODO make this accept arraylists of data
			}
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (jobTableModel == null) {
					jobTableModel = new JobTableModel();
					table.setModel(jobTableModel);
				}
				table.getSelectionModel().removeListSelectionListener(listSelectionListener);
				jobTableModel.setJobs(jobs);
				table.getSelectionModel().addListSelectionListener(listSelectionListener);
				if (!cellSizesSet) {

					table.setRowHeight(30);
					//setColumnWidth(0, -1);
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

	public void addJobs(ArrayList<JobDTO> filesToAdd) {
		final List<JobDTO> files = jobTableModel.getJobs();

		for (Iterator<JobDTO> iterator = filesToAdd.iterator(); iterator.hasNext();) {
			files.add(iterator.next());
		}

		setTableData(files);

	}

	public void removeJobs(ArrayList<JobDTO> filesToRemove) {
		final List<JobDTO> files = jobTableModel.getJobs();

		for (Iterator<JobDTO> iterator = files.iterator(); iterator.hasNext();) {
			JobDTO file = iterator.next();

			if (filesToRemove.contains(file)) {
				iterator.remove();
			}
		}

		setTableData(files);

	}

}

class JobTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<JobDTO> jobs;

	private String[] columns = { "Titre", "Employeur", "Position(s)", "Éducation", "Année(s) d'experience", "Lieu" };

	JobTableModel() {
		jobs = new ArrayList<JobDTO>();
	}

	public Object getValueAt(int row, int column) {
		JobDTO job = jobs.get(row);
		switch (column) {
		case 0:
			return job.getNameOfTheJob();
		case 1:
			return job.getEmployer();
		case 2:
			return job.getNumberOfPositions();
		case 3:
			return job.getEducation();
		case 4:
			return job.getYearsOfExperience();
		case 5:
			return job.getWorkPlace();
		default:
			return "Unknown column : " + column;
		}
	}

	public int getColumnCount() {
		return columns.length;
	}

	public Class<?> getColumnClass(int column) {
		return String.class;
	}

	public String getColumnName(int column) {
		return columns[column];
	}

	public int getRowCount() {
		return jobs.size();
	}

	public JobDTO getJob(int row) {
		return jobs.get(row);
	}

	public void setJobs(List<JobDTO> files) {
		this.jobs = files;
		fireTableDataChanged();
	}

	public List<JobDTO> getJobs() {
		return jobs;
	}

	public void addJob(JobDTO file) {
		jobs.add(file);
		fireTableDataChanged();
	}

}

class CellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// if (value>17 value<26) {
		this.setValue(table.getValueAt(row, column));
		this.setFont(this.getFont().deriveFont(Font.ROMAN_BASELINE));
		//}
		return this;
	}
}
