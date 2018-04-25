package ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import alde.commons.util.autoComplete.jtextfield.AutoCompleteMemoryJTextField;
import alde.commons.util.autoComplete.jtextfield.AutoCompleteService;
import job.Job;

public class JobTableUI extends JPanel {

	private AutoCompleteService autoCompleteService = new AutoCompleteService();

	private AutoCompleteMemoryJTextField textField;

	/**
	 * Create the application.
	 */
	public JobTableUI(List<Job> jobs) {

		for (Job j : jobs) {
			autoCompleteService.addData(j.getEducation());
		}

		setLayout(new BorderLayout());

		JobTable panel = new JobTable();
		panel.setTableData(jobs);
		add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		textField = new AutoCompleteMemoryJTextField(autoCompleteService);
		panel_1.add(textField);
		textField.setColumns(10);

	}

}
