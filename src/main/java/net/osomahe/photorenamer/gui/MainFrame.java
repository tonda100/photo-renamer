package net.osomahe.photorenamer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class MainFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 2058204897388240094L;
	private JMenuItem miAdd, miRemove, miRemoveAll, miExit, miAbout;
	private PhotoTable table;
	private JButton btAdd, btRemove, btRemoveAll, btProceed, btPreview, btCorrection;
	private Icon icoAdd, icoRemove, icoRemoveAll, icoProceed, icoPreview, icoCorrection;

	public MainFrame() {
		super("PHOTO Renamer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/img/icon.gif"));
			icoAdd = new ImageIcon(getClass().getResource("/img/add.png"));
			icoRemove = new ImageIcon(getClass().getResource("/img/remove.png"));
			icoRemoveAll = new ImageIcon(getClass().getResource("/img/trash.png"));
			icoProceed = new ImageIcon(getClass().getResource("/img/start.png"));
			icoPreview = new ImageIcon(getClass().getResource("/img/preview.png"));
			icoCorrection = new ImageIcon(getClass().getResource("/img/correction.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (image != null)
			setIconImage(image);

		initGui();
		setSize(800, 600);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
		table.requestFocus();
	}

	private void initGui() {
		initMenu();
		JPanel pan = new JPanel(new BorderLayout());
		
		//vytvoreni toolbaru
		JToolBar bar = new JToolBar(JToolBar.HORIZONTAL);
		btAdd = new JButton("Přidat", icoAdd);
		btAdd.setVerticalTextPosition(AbstractButton.BOTTOM);
		btAdd.setHorizontalTextPosition(AbstractButton.CENTER);
		btAdd.addActionListener(this);
		//btAdd.setMargin(new Insets(5, 5, 5, 5));
		btAdd.setRequestFocusEnabled(false);
		bar.add(btAdd);
		
		btRemove = new JButton("Odebrat", icoRemove);
		btRemove.setVerticalTextPosition(AbstractButton.BOTTOM);
		btRemove.setHorizontalTextPosition(AbstractButton.CENTER);
		btRemove.addActionListener(this);
		//btRemove.setMargin(new Insets(5, 5, 5, 5));
		btRemove.setRequestFocusEnabled(false);
		bar.add(btRemove);
		
		btRemoveAll = new JButton("Odebrat vše", icoRemoveAll);
		btRemoveAll.setVerticalTextPosition(AbstractButton.BOTTOM);
		btRemoveAll.setHorizontalTextPosition(AbstractButton.CENTER);
		btRemoveAll.addActionListener(this);
		//btRemoveAll.setMargin(new Insets(5, 5, 5, 5));
		btRemoveAll.setRequestFocusEnabled(false);
		bar.add(btRemoveAll);
		bar.addSeparator();
		
		btCorrection = new JButton("Korekce času", icoCorrection);
		btCorrection.setVerticalTextPosition(AbstractButton.BOTTOM);
		btCorrection.setHorizontalTextPosition(AbstractButton.CENTER);
		btCorrection.addActionListener(this);
		//btProceed.setMargin(new Insets(5, 5, 5, 5));
		btCorrection.setRequestFocusEnabled(false);
		bar.add(btCorrection);	
		
		btPreview = new JButton("Náhled", icoPreview);
		btPreview.setVerticalTextPosition(AbstractButton.BOTTOM);
		btPreview.setHorizontalTextPosition(AbstractButton.CENTER);
		btPreview.addActionListener(this);
		//btProceed.setMargin(new Insets(5, 5, 5, 5));
		btPreview.setRequestFocusEnabled(false);
		bar.add(btPreview);		
		bar.addSeparator();
		
		btProceed = new JButton("Spustit", icoProceed);
		btProceed.setVerticalTextPosition(AbstractButton.BOTTOM);
		btProceed.setHorizontalTextPosition(AbstractButton.CENTER);
		btProceed.addActionListener(this);
		//btProceed.setMargin(new Insets(5, 5, 5, 5));
		btProceed.setRequestFocusEnabled(false);
		bar.add(btProceed);		
		bar.addSeparator();
		
		bar.setFloatable(false);
		pan.add(bar, BorderLayout.NORTH);

		//vytvoreni hlavni tabulky
		table = new PhotoTable(this);
		JScrollPane sp = new JScrollPane(table);
		sp.getViewport().setBackground(Color.WHITE);
		sp.setBorder(BorderFactory.createTitledBorder("Vybrané fotobrafie k přejmenování"));
		pan.add(sp, BorderLayout.CENTER);
		add(pan, BorderLayout.CENTER);

	}

	private void initMenu() {
		JMenuBar bar = new JMenuBar();
		JMenu menu1 = new JMenu("Soubor");
		miAdd = new JMenuItem("Přidat");
		miAdd.addActionListener(this);
		menu1.add(miAdd);
		menu1.addSeparator();

		miRemove = new JMenuItem("Odebrat");
		miRemove.addActionListener(this);
		menu1.add(miRemove);
		miRemoveAll = new JMenuItem("Odebrat vše");
		miRemoveAll.addActionListener(this);
		menu1.add(miRemoveAll);
		menu1.addSeparator();

		miExit = new JMenuItem("Konec");
		miExit.addActionListener(this);
		menu1.add(miExit);
		bar.add(menu1);

		JMenu menu2 = new JMenu("Help");
		miAbout = new JMenuItem("About");
		miAbout.addActionListener(this);
		menu2.add(miAbout);
		bar.add(menu2);

		setJMenuBar(bar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(miAdd) || e.getSource().equals(btAdd)) {
			table.addPhotos();
		} else if (e.getSource().equals(miRemove) || e.getSource().equals(btRemove)) {
			table.removeSelectedPhoto();
		} else if (e.getSource().equals(miRemoveAll) || e.getSource().equals(btRemoveAll)) {
			table.removeAllPhoto();
		} else if (e.getSource().equals(btCorrection)) {
			table.setCorrection();
		} else if (e.getSource().equals(btPreview)) {
			table.setNewNames();
		} else if (e.getSource().equals(btProceed)) {
			table.rename();
		} else if (e.getSource().equals(miAbout)) {
			JOptionPane.showMessageDialog(this, "PHOTO RENAMER\n\nProgram k přejmenovávání fotografií z různých fotoaparátů tak,\n" +
					"aby při prohlížení byly chronologicky seřazeny.\n\n\nTonda (2010)", "O nás", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource().equals(miExit)) {
			System.exit(0);
		}
	}
}
