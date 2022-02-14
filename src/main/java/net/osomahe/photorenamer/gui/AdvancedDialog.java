package net.osomahe.photorenamer.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.osomahe.photorenamer.gui.component.Spinner;

public class AdvancedDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = -3765340301942035183L;
	private Long correction;
	private JComboBox cbSymbol;
	private Spinner spDay, spHour, spMin, spSec;
	private JButton btOk, btCancel;

	private AdvancedDialog(Component component) {
		super((Frame) component);
		setModal(true);
		setTitle("Nastavení korekce času pro vybrané snímky");
		setLayout(new BorderLayout());

		JPanel pan = new JPanel();
		cbSymbol = new JComboBox(new String[] { "+", "-" });
		pan.add(cbSymbol);

		spDay = new Spinner(0, 0, 10000);
		pan.add(spDay);
		pan.add(new JLabel("dní, "));

		spHour = new Spinner(0, 0, 23);
		pan.add(spHour);
		pan.add(new JLabel(":"));

		spMin = new Spinner(0, 0, 59);
		pan.add(spMin);
		pan.add(new JLabel(":"));

		spSec = new Spinner(0, 0, 59);
		pan.add(spSec);
		add(pan, BorderLayout.CENTER);

		JPanel panBts = new JPanel();
		panBts.setLayout(new FlowLayout(FlowLayout.CENTER));
		btOk = new JButton("Ok");
		btOk.addActionListener(this);
		panBts.add(btOk);
		btCancel = new JButton("Cancel");
		btCancel.addActionListener(this);
		panBts.add(btCancel);

		add(panBts, BorderLayout.SOUTH);

		pack();
		setSize(350, 110);
		setLocation(300, 200);
		setVisible(true);
	}

	public static Long getCorrection(Component component) {
		return new AdvancedDialog(component).correction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btOk)) {
			long num = spDay.getValue() * 24L * 60 * 60 * 1000
					+ spHour.getValue() * 60 * 60 * 1000 + spMin.getValue()
					* 60 * 1000 + spSec.getValue() * 1000;
			if (cbSymbol.getSelectedIndex() == 1) { // pokud je vybrane minus
				num *= -1;
			}
			correction = num;
			dispose();
		} else if (e.getSource().equals(btCancel)) {
			dispose();
		}
	}
}
