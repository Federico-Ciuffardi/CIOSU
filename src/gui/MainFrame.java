/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright (C) 2019  Federico Ciuffardi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Please contact me (federico.ciuffardi@outlook.com) if you need 
 * additional information or have any questions.
 */

package gui;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.github.federico_ciuffardi.util.Prefs;

/* 
 * Main JFrame
 */

class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static MainFrame instance = null;
	
	public static void main(String[] args) {
		Prefs prefs = new Prefs("conf-comp");
		prefs.setDefault("theme", UIManager.getSystemLookAndFeelClassName());
		try {
			UIManager.setLookAndFeel(prefs.get("theme"));		
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e1) {
				e.printStackTrace();
			}
		}
		instance = new MainFrame();
	}
	
	static MainFrame getInstance() {
		if(instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}
	
	private MainFrame() {
		setContentPane(new JDesktopPane());
		setTitle("CIOSU");
		setBounds(100, 100, 600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNew = new JMenu("Serial");
		menuBar.add(mnNew);
		
		JMenuItem mntmConfiguration = new JMenuItem("Open");
		mntmConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigureIF.open();
			}
		});
		mnNew.add(mntmConfiguration);
		
		JMenuItem mntmPreferences = new JMenuItem("Preferences");
		mntmPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PreferencesIF.open();
				PreferencesIF.tabbedPane.setSelectedIndex(0);
			}
		});
		mnNew.add(mntmPreferences);
		
		JMenu mnComparison = new JMenu("Comparison");
		menuBar.add(mnComparison);
		
		JMenuItem mntmcomparison = new JMenuItem("Open");
		mnComparison.add(mntmcomparison);
		
		JMenuItem mntmPreferences_1 = new JMenuItem("Preferences");
		mntmPreferences_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PreferencesIF.open();
				PreferencesIF.tabbedPane.setSelectedIndex(1);
;
			}
		});
		mnComparison.add(mntmPreferences_1);
		mntmcomparison.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompareIF.open();
			}
		});
		
		JMenu mnMisc = new JMenu("Deprecated");//deprecated functionalities menu
		//menuBar.add(mnMisc);
		
		JMenuItem mntmConfCopy = new JMenuItem("Configuration Copy");
		mntmConfCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CopyIF.open();
			}
		});
		mnMisc.add(mntmConfCopy);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmKeyboardShorcuts = new JMenuItem("Keyboard Shorcuts");
		mntmKeyboardShorcuts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShortcutsIF.open();
			}
		});
		mnHelp.add(mntmKeyboardShorcuts);
		
		JMenuItem mntmAbout = new JMenuItem("About...");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutIF.open();
			}
		});
		mnHelp.add(mntmAbout);
		setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		setVisible(true);
	}
}
