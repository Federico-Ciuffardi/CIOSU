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
import java.beans.PropertyVetoException;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/*
 *  JInternalFrame that contains the "shortcuts" help
 */

class ShortcutsIF extends JInternalFrame{
	
	private static MainFrame mainFrame = MainFrame.getInstance();
	private static final long serialVersionUID = 1L;
	private static ShortcutsIF instance = null;
	private JTextField txtCopy;
	private JTextField txtCtrlshiftc;
	private JTextField txtCtrlshiftv;
	private JTextField txtPaste;
	private JTextField txtCtrlc;
	private JTextField txtCancelPastingdoes;
	
	static void open() {
		if(instance == null) {
			instance = new ShortcutsIF();
		}else {
			instance.init();
		}
	}
	
	private ShortcutsIF() {
		firstTimeInit();
	}
	
	private void firstTimeInit() {
		
		setTitle("Shortcuts");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(30, 30,538,350);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane();
        tabbedPane.addTab("Serial", null, scrollPane, null);
        
        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 2.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        
        txtCtrlshiftc = new JTextField();
        txtCtrlshiftc.setEditable(false);
        txtCtrlshiftc.setText("CTRL+SHIFT+C");
        GridBagConstraints gbc_txtCtrlshiftc = new GridBagConstraints();
        gbc_txtCtrlshiftc.insets = new Insets(0, 0, 5, 5);
        gbc_txtCtrlshiftc.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtCtrlshiftc.gridx = 0;
        gbc_txtCtrlshiftc.gridy = 0;
        panel.add(txtCtrlshiftc, gbc_txtCtrlshiftc);
        txtCtrlshiftc.setColumns(10);
        
        txtCopy = new JTextField();
        txtCopy.setEditable(false);
        txtCopy.setText("Copy");
        GridBagConstraints gbc_txtCopy = new GridBagConstraints();
        gbc_txtCopy.insets = new Insets(0, 0, 5, 0);
        gbc_txtCopy.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtCopy.gridx = 1;
        gbc_txtCopy.gridy = 0;
        panel.add(txtCopy, gbc_txtCopy);
        txtCopy.setColumns(10);
        
        txtCtrlshiftv = new JTextField();
        txtCtrlshiftv.setEditable(false);
        txtCtrlshiftv.setText("CTRL+SHIFT+V");
        GridBagConstraints gbc_txtCtrlshiftv = new GridBagConstraints();
        gbc_txtCtrlshiftv.insets = new Insets(0, 0, 5, 5);
        gbc_txtCtrlshiftv.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtCtrlshiftv.gridx = 0;
        gbc_txtCtrlshiftv.gridy = 1;
        panel.add(txtCtrlshiftv, gbc_txtCtrlshiftv);
        txtCtrlshiftv.setColumns(10);
        
        txtPaste = new JTextField();
        txtPaste.setEditable(false);
        txtPaste.setText("Paste\n");
        GridBagConstraints gbc_txtPaste = new GridBagConstraints();
        gbc_txtPaste.insets = new Insets(0, 0, 5, 0);
        gbc_txtPaste.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtPaste.gridx = 1;
        gbc_txtPaste.gridy = 1;
        panel.add(txtPaste, gbc_txtPaste);
        txtPaste.setColumns(10);
        
        txtCtrlc = new JTextField();
        txtCtrlc.setEditable(false);
        txtCtrlc.setText("CTRL+C");
        GridBagConstraints gbc_txtCtrlc = new GridBagConstraints();
        gbc_txtCtrlc.insets = new Insets(0, 0, 0, 5);
        gbc_txtCtrlc.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtCtrlc.gridx = 0;
        gbc_txtCtrlc.gridy = 2;
        panel.add(txtCtrlc, gbc_txtCtrlc);
        txtCtrlc.setColumns(10);
        
        txtCancelPastingdoes = new JTextField();
        txtCancelPastingdoes.setEditable(false);
        txtCancelPastingdoes.setText("Cancel pasting (does not replace nomal functionality)");
        GridBagConstraints gbc_txtCancelPastingdoes = new GridBagConstraints();
        gbc_txtCancelPastingdoes.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtCancelPastingdoes.gridx = 1;
        gbc_txtCancelPastingdoes.gridy = 2;
        panel.add(txtCancelPastingdoes, gbc_txtCancelPastingdoes);
        txtCancelPastingdoes.setColumns(10);
        
        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Compare", null, panel_1, null);
        panel_1.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane_1 = new JScrollPane();
        panel_1.add(scrollPane_1);
        
        JTextPane txtpnUtilityForComparing = new JTextPane();
        scrollPane_1.setViewportView(txtpnUtilityForComparing);
        txtpnUtilityForComparing.setFont(new Font("Dialog", Font.PLAIN, 16));
        txtpnUtilityForComparing.setText("none");
        txtpnUtilityForComparing.setEditable(false);
        mainFrame.getContentPane().add(this);
        init();
        
	}
	
	private void init() {
        setVisible(true);
        try {
        	setIcon(false);
			setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
        moveToFront();
	}

}
