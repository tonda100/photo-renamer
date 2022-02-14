package net.osomahe.photorenamer.gui.component;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import net.osomahe.photorenamer.util.PreferencesUtils;

public class FileChooser {
	private static String LAST_DIRECTORY = "LAST_DIRECTORY";
	
	public static File[] getSelectedFiles(Component component) {

		JFileChooser fc = new JFileChooser(PreferencesUtils.getValue(LAST_DIRECTORY));
		fc.setDialogTitle("Vyberte fotografie pro seřazení");
		FileFilter filter = new FileFilter() {

			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				} else if (file.getName().endsWith(".jpg")
						|| file.getName().endsWith(".JPG")) {
					return true;
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "Fotografie (jpg)";
			}

		};
		fc.addChoosableFileFilter(filter);
		fc.setMultiSelectionEnabled(true);
		int res = fc.showDialog(component, "Vybrat");
		PreferencesUtils.saveValue(LAST_DIRECTORY, fc.getCurrentDirectory().getAbsolutePath());
		if (res == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFiles();
		}
		return null;
	}

}
