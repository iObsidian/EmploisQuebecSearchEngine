package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alde.commons.properties.PropertyFileManager;
import alde.commons.util.UtilityJFrame;
import automaticPrune.ReflectionValuePruner;
import city.City;
import job.Job;
import region.Region;
import selector.PropertyBuilder;
import selector.Selectable;
import selector.SelectorPanel;

public class SelectorUI {

	private static final Logger log = LoggerFactory.getLogger(SelectorUI.class);

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

				List<Object> jobs = new ArrayList<>();

				for (Selectable s : citySelector.getSelected()) {
					City c = (City) s;
					jobs.addAll(emploiQuebecAPI.getJobs(c));
				}

				// Prune jobs...

				log.info("Pruning jobs of invalid data...");

				int amountRemoved = 0;
				Iterator<Object> iterator = jobs.iterator();
				while (iterator.hasNext()) {
					Job j = (Job) iterator.next();
					if (j.getEmployer().contains("Invalid")) {
						amountRemoved++;
						iterator.remove();
					}
				}

				log.info("Removed  " + amountRemoved + " invalid jobs.");

				log.info("Compiling simillar data...");

				ReflectionValuePruner.prune(jobs);

				List<Job> actualJobList = new ArrayList<>();

				for (Object b : jobs) {
					actualJobList.add((Job) b);
				}

				mainPanel.setViewportView(new JobTableUI(actualJobList));
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
