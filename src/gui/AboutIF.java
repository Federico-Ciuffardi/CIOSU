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

/*
 *  JInternalFrame that contains the "about" help
 */


class AboutIF extends JInternalFrame{
	
	private static MainFrame mainFrame = MainFrame.getInstance();
	private static final long serialVersionUID = 1L;
	private static AboutIF instance = null;
	
	static void open() {
		if(instance == null) {
			instance = new AboutIF();
		}else {
			instance.init();
		}
	}
	
	private AboutIF() {
		firstTimeInit();
	}
	
	private void firstTimeInit() {
		setTitle("About");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(30, 30,526,410);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        tabbedPane.addTab("Serial ", null, panel, null);
        panel.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane);
        
        JTextPane txtpnUsedForConfiguring_1 = new JTextPane();
        txtpnUsedForConfiguring_1.setEditable(false);
        txtpnUsedForConfiguring_1.setFont(new Font("Dialog", Font.PLAIN, 16));
        txtpnUsedForConfiguring_1.setText("Used for configuring your cisco device trough the serial port.\n\nFeatures \n\n* Controled pasting:  each line is pasted one at the time so it evoids common pasting  problems when doing it through serial port\n* Waits for you to input further options to keep pasting: usefull when a large configuration text is pasted that includes commands like \"license accept end user agreement\" that asks [yes/no]: and there are other comands left to paste after this one without this the commands left are pasted as answers  to  [yes/no]: making them useless \n* List of serial ports availables on the prerences to help you to configure the serial line properly\n* Paste cancel: if you press CTRL+C when pasting it will stop\n* DEL working properly");
        scrollPane.setViewportView(txtpnUsedForConfiguring_1);
        
        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Compare", null, panel_1, null);
        panel_1.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane_1 = new JScrollPane();
        panel_1.add(scrollPane_1);
        
        JTextPane txtpnUtilityForComparing = new JTextPane();
        scrollPane_1.setViewportView(txtpnUtilityForComparing);
        txtpnUtilityForComparing.setFont(new Font("Dialog", Font.PLAIN, 16));
        txtpnUtilityForComparing.setText("Utility for comparing a current configuration with a target configuration (must have the same format as the show running/startup commands of the Cisco IOS).\n-\nColor code:\n* RED:     ERROR  (no match)\n* YELLOW:  CARFUL (may be a encripted string or something router specific that prevents matching) (may have to add some more exceptions on the future)\n* GREEN:   OK     (it matches)\n-\nThere may be other uses since it compares the lines and configuration modes without order, the configuration mode of a line beeing the global configuration mode if it has no indentation, else the configuration mode is the nearest line without indentation.\n");
        txtpnUtilityForComparing.setEditable(false);
        
        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("General", null, panel_2, null);
        panel_2.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane_2 = new JScrollPane();
        panel_2.add(scrollPane_2, BorderLayout.CENTER);
        
        JTextPane txtpnIosu = new JTextPane();
        txtpnIosu.setEditable(false);
        txtpnIosu.setText("IOSU-2019");
        scrollPane_2.setViewportView(txtpnIosu);
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
