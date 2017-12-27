package ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import emplois.Emploi;
import emplois.RiotMotor;

public class LoadingScreen extends JPanel {
	private JLabel label;
	private JProgressBar progressBar;

	RiotMotor riotMotor = new RiotMotor(this);

	UI ui;

	/**
	 * Create the panel.
	 * @param ui 
	 */
	public LoadingScreen(UI ui) {
		this.ui = ui;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel progressBarPanel = new JPanel();
		panel.add(progressBarPanel, BorderLayout.SOUTH);
		progressBarPanel.setLayout(new BorderLayout(0, 0));

		progressBar = new JProgressBar();
		progressBarPanel.add(progressBar, BorderLayout.NORTH);

		JPanel labelPanel = new JPanel();
		panel.add(labelPanel, BorderLayout.CENTER);
		labelPanel.setLayout(new BorderLayout(0, 0));

		label = new JLabel("...");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel.add(label, BorderLayout.CENTER);

		riotMotor.reloadList();

	}

	public void reportTotal(int current, int max) {
		progressBar.setValue(current);
		progressBar.setMaximum(max);
	}

	public void reportJobs(int totalJobs) {
		label.setText(i18n.Constants.emploisTrouveesLabel + " " + totalJobs);
	}

	public void reportEnded(List<Emploi> emplois) {
		ui.loadingEnded(emplois);
	}

}
