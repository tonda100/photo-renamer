package net.osomahe.photorenamer.gui;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CorrectionRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 5888857254296897470L;
	private SimpleDateFormat format;

	public CorrectionRenderer() {
		format = new SimpleDateFormat("HH:mm:ss, dd.MM.yyyy");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lb = (JLabel) super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if (value != null) {
			long millis = ((Calendar) table.getValueAt(row, column - 1)).getTimeInMillis();
			Calendar cal = Calendar.getInstance();
			millis += (Long) value;
			cal.setTimeInMillis(millis);
			String newDate = format.format(cal.getTime());
			lb.setText(long2String((Long) value) + " (" + newDate + ")");
		}

		return lb;
	}

	private String long2String(Long value) {
		long num = value;
		String symbol = "+";
		if (num < 0) {
			num *= -1;
			symbol = "-";
		}
		long days = num / (24 * 60 * 60 * 1000);
		long hours = num / (60 * 60 * 1000) - days * 24;
		long mins = num / (60 * 1000) - days * 60 * 24 - hours * 60;
		long secs = num / (1000) - days * 60 * 60 * 24 - hours * 60 * 60 - mins
				* 60;
		if (days == 0) {
			return symbol + " " + get2(hours) + ":" + get2(mins) + ":"
					+ get2(secs);
		} else {
			return symbol + " " + days + " dní, " + get2(hours) + ":"
					+ get2(mins) + ":" + get2(secs);
		}
	}

	private String get2(long num) {
		if (num < 10)
			return "0" + num;
		return "" + num;
	}

}
