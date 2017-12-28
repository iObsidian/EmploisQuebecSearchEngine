package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class Loading extends JFrame {

	private JPanel contentPane;

	private JLabel lblInitialising;
	private JProgressBar progressBar;

	JobMotor riotMotor = new JobMotor(this);

	UI ui;
	private JLabel lblStatus;
	private Component horizontalStrut;
	private Component horizontalStrut_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Loading frame = new Loading();
					frame.setVisible(true);

					Dimension dimemsion = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation(dimemsion.width / 2 - frame.getSize().width / 2, dimemsion.height / 2 - frame.getSize().height / 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Loading() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		setIconImage(Toolkit.getDefaultToolkit().getImage(Loading.class.getResource("/com/sun/javafx/scene/web/skin/Copy_16x16_JFX.png")));
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
		progressBar.setStringPainted(true);
		progressBarPanel.add(progressBar, BorderLayout.NORTH);

		JPanel labelPanel = new JPanel();
		getContentPane().add(labelPanel, BorderLayout.CENTER);
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));

		horizontalStrut_1 = Box.createHorizontalStrut(20);
		labelPanel.add(horizontalStrut_1);

		lblInitialising = new JLabel("Initialising...");
		lblInitialising.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel.add(lblInitialising);

		horizontalStrut = Box.createHorizontalStrut(20);
		labelPanel.add(horizontalStrut);

		lblStatus = new JLabel("Status...");
		lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(lblStatus);

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
		lblInitialising.setText(totalJobs + " emplois trouvés.");
	}

	public void reportEnded(List<Job> emplois) {
		ui.loadingEnded(emplois);
		setVisible(false);
	}

	public void reportStatus(String s) {
		System.out.println(s);
		lblStatus.setText(s);
	}

}
