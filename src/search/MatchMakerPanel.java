package search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import alde.commons.util.autoCompleteJTextField.UtilityJTextField;

public class MatchMakerPanel extends JPanel {

	JPanel main_panel;

	List<String> matchers = new ArrayList<String>();

	List<SearchKeywordsChangedListener> listeners = new ArrayList<>();

	private UtilityJTextField textField;

	public void addListener(SearchKeywordsChangedListener l) {
		listeners.add(l);
	}

	public void addAutoCompleteData(List<String> data) {
		textField.getCompletionService().addData(data);
	}

	public MatchMakerPanel() {
		setLayout(new BorderLayout(0, 0));

		main_panel = new JPanel();
		add(main_panel, BorderLayout.CENTER);

		matchers.add("Jesus");
		matchers.add("lives");

		textField = new UtilityJTextField("Add keyword", true);
		textField.addReceiver(input -> {
			if (!matchers.contains(input)) {
				matchers.add(input);
			}
			populate();
		});

		populate();

		textField.setSize(new Dimension(20, 100));
		add(textField, BorderLayout.SOUTH);

	}

	public void populate() {
		main_panel.removeAll();

		for (String m : matchers) {
			main_panel.add(getMatchPanel(m));
		}

		main_panel.revalidate();
		main_panel.repaint();
	}

	public JPanel getMatchPanel(String m) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));

		JButton btnRemove = new JButton(m);

		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				matchers.remove(m);
				main_panel.remove(panel);

				populate();
				System.out.println("Removed...");
			}
		});

		btnRemove.setForeground(Color.WHITE);
		btnRemove.setBackground(Color.BLACK);
		panel.add(btnRemove, BorderLayout.EAST);

		Component strut = Box.createHorizontalStrut(5);
		panel.add(strut, BorderLayout.WEST);

		return panel;
	}

}
