package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import emplois.Emploi;
import emplois.RiotMotor;

public class LoadingBar extends JFrame {

	private JPanel contentPane;

	private JLabel lblInitialising;
	private JProgressBar progressBar;

	RiotMotor riotMotor = new RiotMotor(this);

	UI ui;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoadingBar frame = new LoadingBar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoadingBar() {
		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoadingBar.class.getResource("/com/sun/javafx/scene/web/skin/Copy_16x16_JFX.png")));
		setTitle("Loading...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 140);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		ui = new UI();

		getContentPane().setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel progressBarPanel = new JPanel();
		getContentPane().add(progressBarPanel, BorderLayout.SOUTH);
		progressBarPanel.setLayout(new BorderLayout(0, 0));

		progressBar = new JProgressBar();
		progressBarPanel.add(progressBar, BorderLayout.NORTH);

		JPanel labelPanel = new JPanel();
		getContentPane().add(labelPanel, BorderLayout.CENTER);
		labelPanel.setLayout(new BorderLayout(0, 0));

		lblInitialising = new JLabel("Initialising...");
		lblInitialising.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel.add(lblInitialising, BorderLayout.CENTER);

		reload();

	}

	private void reload() {
		ui.setVisible(false);

		new Thread() {
			public void run() {
				riotMotor.reloadList();
			}
		}.start();
	}

	public void reportTotal(int current, int max) {
		progressBar.setValue(current);
		progressBar.setMaximum(max);
	}

	public void reportJobs(int totalJobs) {
		lblInitialising.setText(i18n.Constants.emploisTrouveesLabel + " " + totalJobs);
	}

	public void reportEnded(List<Emploi> emplois) {
		ui.loadingEnded(emplois);
		setVisible(false);
	}

}
