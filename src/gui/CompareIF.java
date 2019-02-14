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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import comp.DtComp;
import comp.DtFullComp;
import comp.Factory;
import comp.ICtrlComp;
import comp.State;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Font;

/*
 * JInternalFrame that provides a graphical interface for the configuration comparison
 */

class CompareIF extends JInternalFrame{
	
	private static MainFrame mainFrame = MainFrame.getInstance();
	private static final long serialVersionUID = 1L;
	private Path currentPath;
	private Path targetPath;
	private JTextArea currentTextArea;
	private JTextArea targetTextArea;
	
	static void open() {
		new CompareIF();
	}
	
	private CompareIF() {
		setTitle("NO FILE SELECTED == NO FILE SELECTED");
		init();
	}
	
	private void init() {
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(30, 30,2*mainFrame.getWidth()/3,2*mainFrame.getHeight()/3);
        mainFrame.getContentPane().add(this);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane);
        
        JPanel sToSPanel = new JPanel();
        tabbedPane.addTab("Side to Side", null, sToSPanel, null);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0};
        gbl_panel.rowHeights = new int[]{0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        sToSPanel.setLayout(gbl_panel);
        
        JScrollPane sToSScrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        sToSPanel.add(sToSScrollPane, gbc_scrollPane);
        
        currentTextArea = new JTextArea();
        currentTextArea.setFont(new Font("Garuda", Font.PLAIN, 16));
        sToSScrollPane.setViewportView(currentTextArea);
       
        currentTextArea.setCaretPosition(0);
        
        JLabel lblCurrent = new JLabel("Current");
        sToSScrollPane.setColumnHeaderView(lblCurrent);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridx = 1;
        gbc_scrollPane_1.gridy = 0;
        sToSPanel.add(scrollPane_1, gbc_scrollPane_1);
        
        targetTextArea = new JTextArea();
        targetTextArea.setFont(new Font("Garuda", Font.PLAIN, 16));
        scrollPane_1.setViewportView(targetTextArea);
        
        targetTextArea.setCaretPosition(0);
        
        JLabel lblTarget = new JLabel("Target");
        scrollPane_1.setColumnHeaderView(lblTarget);
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		getContentPane().add(updateButton, BorderLayout.SOUTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnCurrent = new JMenu("Current");
		menuBar.add(mnCurrent);
		
		JMenuItem mntmOpenCurrent = new JMenuItem("Open");
		mntmOpenCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					setCurrentPath(chooser.getSelectedFile().getAbsolutePath()); 
					try {
						currentTextArea.setText(new String(Files.readAllBytes(currentPath)));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					currentTextArea.setCaretPosition(0);
				}			
			}
		});
		mnCurrent.add(mntmOpenCurrent);
		
		JMenuItem mntmSaveCurrent = new JMenuItem("Save");
		mntmSaveCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Files.write( currentPath, currentTextArea.getText().getBytes());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnCurrent.add(mntmSaveCurrent);
		
		JMenuItem mntmRestoreCurrent = new JMenuItem("Restore");
		mntmRestoreCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					currentTextArea.setText(new String(Files.readAllBytes(currentPath)));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				currentTextArea.setCaretPosition(0);
			}
		});
		mnCurrent.add(mntmRestoreCurrent);
		
		JMenuItem mntmCleanCurrent = new JMenuItem("Clean");
		mntmCleanCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentTextArea.setText("");
			}
		});
		mnCurrent.add(mntmCleanCurrent);
		
		JMenu mnTarget = new JMenu("Target");
		menuBar.add(mnTarget);
		
		JMenuItem mntmOpenTarget = new JMenuItem("Open");
		mntmOpenTarget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					setTargetPath(chooser.getSelectedFile().getAbsolutePath()); 
					try {
						targetTextArea.setText(new String(Files.readAllBytes(targetPath)));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					targetTextArea.setCaretPosition(0);
				}
			}
		});
		mnTarget.add(mntmOpenTarget);
		
		JMenuItem mntmSaveTarget = new JMenuItem("Save");
		mntmSaveTarget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Files.write( targetPath, targetTextArea.getText().getBytes());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnTarget.add(mntmSaveTarget);
		
		JMenuItem mntmRestoreTarget = new JMenuItem("Restore");
		mntmRestoreTarget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					targetTextArea.setText(new String(Files.readAllBytes(targetPath)));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				targetTextArea.setCaretPosition(0);
			}
		});
		mnTarget.add(mntmRestoreTarget);
		
		JMenuItem mntmCleanTarget = new JMenuItem("Clean");
		mntmCleanTarget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				targetTextArea.setText("");
			}
		});
		mnTarget.add(mntmCleanTarget);
		setVisible(true);
	}
	
	private void update() {
		ICtrlComp iCtrlComp = Factory.getInstance().getICtrlComp();
		DtFullComp dtFullComp = iCtrlComp.fullComp(currentTextArea.getText(),targetTextArea.getText());
		displayFullComp(dtFullComp);
	}
	
	private void displayFullComp(DtFullComp dtFullComp) {
		DtComp[] cComp = dtFullComp.getCurrentComp();
		DtComp[] tComp =  dtFullComp.getTargetComp();
		setCompOnJTextArea(cComp, currentTextArea);
		setCompOnJTextArea(tComp, targetTextArea);
	}
	
	private void setCompOnJTextArea(DtComp[] comp, JTextArea JTA) {
		int cpos = JTA.getCaretPosition();
		JTA.setText("");
		Highlighter h = JTA.getHighlighter();
		for(DtComp dtComp:comp) {
			
			String currentText = JTA.getText();
			
			int startPos = currentText.length();
			int endPos = currentText.length()+dtComp.getLine().length();
			JTA.append(dtComp.getLine()+"\n");
			if(dtComp.getState() == State.OK ) {
				highlight(h,startPos,endPos,new Color(80,220,100));
				continue;
			}
			if(dtComp.getState() == State.MISSING || dtComp.getState() == State.SURPLUS) {
				highlight(h,startPos,endPos,new Color(255, 51, 51));
				continue;
			}
			if(dtComp.getState() == State.CAREFUL) {
				highlight(h,startPos,endPos,new Color(255, 255, 153));
				continue;
			}
		}
		JTA.setCaretPosition(cpos);
	}
	
	private void highlight(Highlighter h,int startPos,int endPos, Color color ) {
		try {
			h.addHighlight(startPos,endPos,new DefaultHighlighter.DefaultHighlightPainter(color));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	private void setCurrentPath(String newCurrentPath){
		currentPath = Paths.get(newCurrentPath);
		if(targetPath != null) {
			setTitle(currentPath.getFileName()+" == "+targetPath.getFileName());
		}else {
			setTitle(currentPath.getFileName()+" == NO FILE SELECTED");
		}
	}
	
	private void setTargetPath(String newTargetPath){
		targetPath = Paths.get(newTargetPath);
		if(currentPath != null) {
			setTitle(currentPath.getFileName()+" == "+targetPath.getFileName());
		}else {
			setTitle("NO FILE SELECTED == "+targetPath.getFileName());
		}
	}
	
}
