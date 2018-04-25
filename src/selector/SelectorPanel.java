package selector;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SelectorPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	List<? extends Selectable> selectables;

	public SelectorPanel(List<? extends Selectable> selectables) {

		this.selectables = selectables;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		for (Selectable r : selectables) {
			JCheckBox regionCheckBox = new JCheckBox(r.getName());
			regionCheckBox.setSelected(r.isSelected());
			regionCheckBox.setHorizontalAlignment(SwingConstants.TRAILING);

			ActionListener actionListener = actionEvent -> {
				r.setSelected(regionCheckBox.isSelected());
			};
			regionCheckBox.addActionListener(actionListener);

			add(regionCheckBox);
		}

	}

	public List<Selectable> getSelected() {
		List<Selectable> selected = new ArrayList<>();

		for (Selectable selectable : selectables) {
			if (selectable.isSelected()) {
				selected.add(selectable);
			}
		}
		return selected;
	}

}
