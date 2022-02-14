package net.osomahe.photorenamer.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import net.osomahe.photorenamer.gui.component.FileChooser;
import net.osomahe.photorenamer.gui.component.PhotoTimeComparator;
import net.osomahe.photorenamer.object.Photo;

public class PhotoTable extends JTable {
	private static final long serialVersionUID = 4177314232620405726L;
	private PhotoModel model;
	private Component parent;

	public PhotoTable(Component parent) {
		this.parent = parent;
		model = new PhotoModel();
		setModel(model);
		setRowHeight(30);
		setIntercellSpacing(new Dimension(5, 5));
		getColumnModel().getColumn(2).setCellRenderer(new CalendarRenderer());
		getColumnModel().getColumn(3).setCellRenderer(new CorrectionRenderer());
		setAutoCreateRowSorter(true);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	public void addPhotos() {
		File[] files = FileChooser.getSelectedFiles(parent);
		if (files != null) {
			for (File file : files) {
				model.data.add(new Photo(file));
			}
			model.fireTableDataChanged();
			repaint();
		}

	}

	public void removeAllPhoto() {
		if (model.data.size() == 0)
			return;
		int res = JOptionPane.showConfirmDialog(parent,
				"Opravdu chcete všechny přidané fotografie odstranit?",
				"Odstranit vše?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (res == JOptionPane.YES_OPTION) {
			model.data.clear();
			model.fireTableDataChanged();
			repaint();
		}
	}

	public void removeSelectedPhoto() {
		int[] selected = getSelectedRows();
		if (selected == null || selected.length == 0)
			return;
		int res = JOptionPane.showConfirmDialog(parent,
				"Opravdu chcete vybrané fotografie odstranit?", "Odstranit?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (res == JOptionPane.YES_OPTION) {
			List<Photo> photos = new ArrayList<Photo>();
			for (int i : selected) {
				photos.add(model.data.get(convertRowIndexToModel(i)));
			}
			for (Photo photo : photos) {
				model.data.remove(photo);
			}

			model.fireTableDataChanged();
			repaint();
		}
	}

	public void setCorrection() {
		int[] selected = getSelectedRows();
		if (selected != null && selected.length > 0) {
			Long correction = AdvancedDialog.getCorrection(parent);
			for (int i : selected) {
				model.data.get(convertRowIndexToModel(i)).setCorrection(
						correction);
			}
			model.fireTableDataChanged();
			repaint();
		} else {
			JOptionPane.showMessageDialog(this,
					"Nejsou vybrány žádné fotografie ke korekci času", "Pozor",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void setNewNames() {
		List<Photo> dataNew = new ArrayList<Photo>();
		dataNew.addAll(model.data);
		Collections.sort(dataNew, new PhotoTimeComparator());
		int digits = (String.valueOf(dataNew.size())).length();
		for (int i = 0; i < dataNew.size(); i++) {
			Photo p = dataNew.get(i);
			StringBuffer buffer = new StringBuffer();
			int iDigits = (String.valueOf(i)).length();
			for(; iDigits < digits; iDigits++){
				buffer.append("0");
			}

			buffer.append(i);
			buffer.append("_");
			buffer.append(p.getName());
			p.setNewName(buffer.toString());
		}
		model.fireTableDataChanged();
		repaint();
	}

	public void rename() {
		if(model.data.size() == 0)
			return;
		
		int res = JOptionPane.showConfirmDialog(parent, "Chcete fotografie opravdu přejmenovat?", "Přejmenování", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION){
			setNewNames();
			for(Photo p : model.data){
				File fDest = new File(p.getFile().getParent() + File.separatorChar + p.getNewName());
				p.getFile().renameTo(fDest);
			}
			model.data.clear();
			model.fireTableDataChanged();
			repaint();
			
			JOptionPane.showMessageDialog(parent, "Přejmenování bylo dokončeno", "Hotovo", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class PhotoModel extends AbstractTableModel {
		private static final long serialVersionUID = -878010141798244588L;
		private final String[] names = { "Název", "Navrhovaný název", "Datum",
				"Časová korekce", "Fotoaparát" };
		private List<Photo> data = new ArrayList<Photo>();

		@Override
		public int getColumnCount() {
			return names.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public String getColumnName(int column) {
			return names[column];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Photo sel = data.get(rowIndex);
			if (columnIndex == 0) {
				return sel.getName();
			} else if (columnIndex == 1) {
				return sel.getNewName();
			} else if (columnIndex == 2) {
				return sel.getDate();
			} else if (columnIndex == 3) {
				return sel.getCorrection();
			} else if (columnIndex == 4) {
				return sel.getCamera();
			}
			return null;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 0) {
				return String.class;
			} else if (columnIndex == 1) {
				return String.class;
			} else if (columnIndex == 2) {
				return Calendar.class;
			} else if (columnIndex == 3) {
				return Long.class;
			} else if (columnIndex == 4) {
				return String.class;
			}
			return super.getColumnClass(columnIndex);
		}

	}

	private class CalendarRenderer extends DefaultTableCellRenderer implements
			TableCellRenderer {
		private static final long serialVersionUID = -4550764715834882938L;
		private SimpleDateFormat format;

		public CalendarRenderer() {
			format = new SimpleDateFormat("HH:mm:ss, dd.MM.yyyy");
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			JLabel lb = (JLabel) super.getTableCellRendererComponent(table,
					value, isSelected, hasFocus, row, column);
			if (value instanceof Calendar) {
				lb.setText(format.format(((Calendar) value).getTime()));
			}

			return lb;
		}

	}

}
