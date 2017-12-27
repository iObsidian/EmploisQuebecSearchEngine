package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import emplois.Emploi;
import javax.swing.JSplitPane;

public class UI extends JFrame {

	AdvancedSearch as = new AdvancedSearch();

	private JobVisualiser panel = new JobVisualiser();
	Table table = new Table(panel);

	private JPanel contentPane;
	private JPanel search;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
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
	public UI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UI.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/capslock-icon.png")));
		setTitle("Outil de recherche pour Placement en Ligne d'Emplois Qu\u00E9bec");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 881, 518);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setDividerLocation(400);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JPanel tableAndSearchPanel = new JPanel(new BorderLayout());

		tableAndSearchPanel.add(table, BorderLayout.CENTER);

		JPanel searchPanel = new JPanel();
		table.add(searchPanel, BorderLayout.SOUTH);
		searchPanel.setLayout(new BorderLayout(0, 0));

		//

		searchPanel.add(as.getSimpleSearchPanel(table), BorderLayout.NORTH);
		searchPanel.add(as, BorderLayout.SOUTH);

		splitPane.setLeftComponent(tableAndSearchPanel);

		splitPane.setRightComponent(panel);

	}

	public void loadingEnded(List<Emploi> emplois) {
		setVisible(true);

		System.out.println("Emplois : " + emplois.size());

		table.setTableData(emplois, false);

	}

}
