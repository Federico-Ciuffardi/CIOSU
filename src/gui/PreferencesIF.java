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

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;
import com.github.federico_ciuffardi.util.Prefs;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextPane;
import javax.swing.JComboBox;

/*
 * JInternalFrame that provides a graphical interface for the preferences
 */

class PreferencesIF extends JInternalFrame{
	
	static MainFrame mainFrame = MainFrame.getInstance();
	private static final long serialVersionUID = 1L;
	private static PreferencesIF instance = null;
	private JTextField textFieldSerialLine;
	private JTextPane hintsTextPane;
	private JComboBox<String> themes;
	private Prefs prefs;
	static JTabbedPane tabbedPane;
	
	static void open() {
		if(instance == null) {
			instance = new PreferencesIF();
		}else {
			instance.init();
		}
	}
	
	private PreferencesIF() {
		prefs = new Prefs("conf-comp");
		prefs.setDefault("SerialID", "/dev/ttyS0");
		firstTimeInit();
	}
	
	private void firstTimeInit() {
		setTitle("Preferences");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(30, 30,522,337);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        tabbedPane.addTab("Serial", null, panel, null);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panel.rowHeights = new int[]{15, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        
        JLabel lblSerialLine = new JLabel("Serial line:");
        GridBagConstraints gbc_lblSerialLine = new GridBagConstraints();
        gbc_lblSerialLine.insets = new Insets(0, 0, 5, 5);
        gbc_lblSerialLine.anchor = GridBagConstraints.EAST;
        gbc_lblSerialLine.gridx = 1;
        gbc_lblSerialLine.gridy = 1;
        panel.add(lblSerialLine, gbc_lblSerialLine);
        
        textFieldSerialLine = new JTextField();
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.gridwidth = 2;
        gbc_textField.insets = new Insets(0, 0, 5, 5);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 2;
        gbc_textField.gridy = 1;
        panel.add(textFieldSerialLine, gbc_textField);
        textFieldSerialLine.setColumns(10);
        
        JLabel lblHints = new JLabel(" Hints for Serial line\n");
        GridBagConstraints gbc_lblHints = new GridBagConstraints();
        gbc_lblHints.gridwidth = 2;
        gbc_lblHints.insets = new Insets(0, 0, 5, 5);
        gbc_lblHints.gridx = 1;
        gbc_lblHints.gridy = 2;
        panel.add(lblHints, gbc_lblHints);
        
        hintsTextPane = new JTextPane();
        hintsTextPane.setEditable(false);
        GridBagConstraints gbc_hintsTextPane = new GridBagConstraints();
        gbc_hintsTextPane.gridwidth = 3;
        gbc_hintsTextPane.insets = new Insets(0, 0, 5, 5);
        gbc_hintsTextPane.fill = GridBagConstraints.BOTH;
        gbc_hintsTextPane.gridx = 1;
        gbc_hintsTextPane.gridy = 3;
        panel.add(hintsTextPane, gbc_hintsTextPane);
        
        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Compare", null, panel_1, null);
        
        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("General", null, panel_2, null);
        GridBagLayout gbl_panel_2 = new GridBagLayout();
        gbl_panel_2.columnWidths = new int[]{0, 0, 75, 0};
        gbl_panel_2.rowHeights = new int[]{15, 0, 0, 0};
        gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        panel_2.setLayout(gbl_panel_2);
        
        JButton btnRestore = new JButton("Restore to defaults\n");
        btnRestore.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int dialogResult = JOptionPane.showConfirmDialog (instance, "Restore to defaults?","Are You Sure?",JOptionPane.YES_NO_OPTION);
        		if(dialogResult == JOptionPane.YES_OPTION){
        			if(!prefs.getDefault("theme").equals(themes.getSelectedItem())) {
            			JOptionPane.showMessageDialog(instance, "Restarting is required for the theme change to take effect", "Warning", JOptionPane.INFORMATION_MESSAGE);
            		}
            		prefs.allToDefaults();
            		reload();
            		setVisible(false);
        		}
        	}
        });
        
        JLabel lblTheme = new JLabel("Theme:");
        GridBagConstraints gbc_lblTheme = new GridBagConstraints();
        gbc_lblTheme.insets = new Insets(0, 0, 5, 5);
        gbc_lblTheme.anchor = GridBagConstraints.EAST;
        gbc_lblTheme.gridx = 1;
        gbc_lblTheme.gridy = 1;
        panel_2.add(lblTheme, gbc_lblTheme);
        
        themes = new JComboBox<String>();
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 2;
        gbc_comboBox.gridy = 1;
        panel_2.add(themes, gbc_comboBox);
        GridBagConstraints gbc_btnRestore = new GridBagConstraints();
        gbc_btnRestore.anchor = GridBagConstraints.SOUTHEAST;
        gbc_btnRestore.gridx = 2;
        gbc_btnRestore.gridy = 2;
        panel_2.add(btnRestore, gbc_btnRestore);
        
        JPanel panel_3 = new JPanel();
        getContentPane().add(panel_3, BorderLayout.SOUTH);
        GridBagLayout gbl_panel_3 = new GridBagLayout();
        gbl_panel_3.columnWidths = new int[]{142, 79, 143, 0};
        gbl_panel_3.rowHeights = new int[]{25, 0};
        gbl_panel_3.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel_3.setLayout(gbl_panel_3);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_btnCancel = new GridBagConstraints();
        gbc_btnCancel.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
        gbc_btnCancel.gridx = 1;
        gbc_btnCancel.gridy = 0;
        panel_3.add(btnCancel, gbc_btnCancel);
        btnCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setVisible(false);
        	}
        });
        
        JButton btnApplyAndClose = new JButton("Apply and Close");
        btnApplyAndClose.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_btnApplyAndClose = new GridBagConstraints();
        gbc_btnApplyAndClose.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnApplyAndClose.gridx = 2;
        gbc_btnApplyAndClose.gridy = 0;
        panel_3.add(btnApplyAndClose, gbc_btnApplyAndClose);
        btnApplyAndClose.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		prefs.set("SerialID", textFieldSerialLine.getText()); 
        		if(!prefs.get("theme").equals(themes.getSelectedItem())) {
        			JOptionPane.showMessageDialog(instance, "Restarting is required for the theme change to take effect", "!", JOptionPane.INFORMATION_MESSAGE);
            		prefs.set("theme", (String) themes.getSelectedItem());
        		}
        		setVisible(false);
        	}
        });
        mainFrame.getContentPane().add(this);
        init(); 
	}
	
	public void init() {
		reload();
        setVisible(true);
        try {
        	setIcon(false);
			setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
        moveToFront();
	}
	
	private void reload() {
		textFieldSerialLine.setText( prefs.get("SerialID"));
        String[] apd = serial.PortsHandler.getInstance().getAvailablePortsDescription();
        String hints = "Available Ports information:\n";
        for(String s:apd) {
        	hints += s + "\n";
        }
        hints += "-\nOn linux precede the port system name with \"/dev/\"";
        hintsTextPane.setText(hints);
        themes.removeAllItems();
        UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo look : looks) {
        	themes.addItem(look.getClassName());
            if(look.getClassName().equals(prefs.get("theme"))) {
            	themes.setSelectedItem(look.getClassName());
            }
        }
	}
	
}
