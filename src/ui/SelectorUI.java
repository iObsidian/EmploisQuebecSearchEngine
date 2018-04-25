package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.BasicConfigurator;

import alde.commons.properties.PropertyFileManager;
import alde.commons.util.UtilityJFrame;
import city.City;
import job.Job;
import region.Region;
import selector.PropertyBuilder;
import selector.Selectable;
import selector.SelectorPanel;

public class SelectorUI {

	Loading loadingPanel = new Loading();

	PropertyBuilder propertyBuilder = new PropertyBuilder(new PropertyFileManager("emploi-quebec-search.properties"));
	EmploiQuebecAPI emploiQuebecAPI = new EmploiQuebecAPI();

	SelectorPanel regionSelector;
	SelectorPanel citySelector;

	private JFrame frame;
	private JScrollPane mainPanel;

	int index = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		BasicConfigurator.configure();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectorUI window = new SelectorUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SelectorUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new UtilityJFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setIconImage(null);

		mainPanel = new JScrollPane();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

		showRegionSelector();

		JPanel nextPanel = new JPanel();
		frame.getContentPane().add(nextPanel, BorderLayout.SOUTH);
		nextPanel.setLayout(new BorderLayout(0, 0));

		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index == 0) {
					showCitySelector();
					index++;
				} else if (index == 1) {
					showJobTable();
				}
			}
		});
		nextPanel.add(nextButton);

		JPanel infoPanel = new JPanel();
		nextPanel.add(infoPanel, BorderLayout.WEST);

		Component strutLeft = Box.createHorizontalStrut(300);
		infoPanel.add(strutLeft);
	}

	public void showRegionSelector() {
		setIsLoading();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.setTitle("Region selector");

				regionSelector = new SelectorPanel(emploiQuebecAPI.getRegions());
				mainPanel.setViewportView(regionSelector);
			}
		});

	}

	public void showCitySelector() {

		setIsLoading();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.setTitle("City selector");

				List<City> cities = new ArrayList<>();

				System.out.println(regionSelector.getSelected().size());

				for (Selectable s : regionSelector.getSelected()) {
					Region r = (Region) s;
					cities.addAll(emploiQuebecAPI.getCities(r));
				}

				citySelector = new SelectorPanel(cities);

				mainPanel.setViewportView(citySelector);
			}
		});

	}

	private void showJobTable() {
		setIsLoading();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				frame.setTitle("Jobs");

				List<Job> jobs = new ArrayList<>();

				for (Selectable s : citySelector.getSelected()) {
					City c = (City) s;
					jobs.addAll(emploiQuebecAPI.getJobs(c));
				}

				mainPanel.setViewportView(new JobTableUI(jobs));
			}
		});

	}

	private void setIsLoading() {
		frame.setTitle("Loading...");

		mainPanel.setViewportView(loadingPanel);
		mainPanel.revalidate();
		mainPanel.repaint();

		loadingPanel.revalidate();
		loadingPanel.repaint();

	}

}
