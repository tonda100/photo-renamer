package net.osomahe.photorenamer.gui.component;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class Spinner extends JSpinner {

	private static final long serialVersionUID = 5664965730455266950L;

	public Spinner(int value, int min, int max){
		SpinnerModel model = new SpinnerNumberModel(value, min, max, 1);
		setModel(model);
	}

	@Override
	public Integer getValue() {
		return (Integer) super.getValue();
	}
	
	
}
