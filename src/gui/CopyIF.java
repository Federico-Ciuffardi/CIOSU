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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import javax.swing.JTextArea;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

//deprecated

class CopyIF extends JInternalFrame{
	
	static MainFrame mainFrame = MainFrame.getInstance();
	private static final long serialVersionUID = 1L;
	private Path currentPath;
	private JTextArea currentTextArea;
	private int timesNexted = 0;
	private final int LINES_PER_COPY = 30;
	private JTextField textField;
	private List<Integer> newLinesPos = new ArrayList<Integer>();
	static void open() {
		new CopyIF();
	}
	private CopyIF() {
		init();
	}
	private void init() {
		setTitle("Copy NO FILE SELECTED");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(30, 30,650,800);
        mainFrame.getContentPane().add(this);
        getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel sToSPanel = new JPanel();
		getContentPane().add(sToSPanel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 5, 0};
		gbl_panel.columnWeights = new double[]{0.0, 2.0, 1.0, 2.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		sToSPanel.setLayout(gbl_panel);
		
		JScrollPane sToSScrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		sToSPanel.add(sToSScrollPane, gbc_scrollPane);
		
		currentTextArea = new JTextArea();
 		currentTextArea.setSelectionColor(new Color(173, 216, 230));
		sToSScrollPane.setViewportView(currentTextArea);
		
		 currentTextArea.setCaretPosition(0);
		 
		 JButton btnPrevious = new JButton("Previous");
		 btnPrevious.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		//Highlighter h = currentTextArea.getHighlighter();
		 		if(timesNexted>=2) {
			 		currentTextArea.requestFocusInWindow();
			 		currentTextArea.setSelectionStart(newLinesPos.get(timesNexted-2));
			 		currentTextArea.setSelectionEnd(newLinesPos.get(timesNexted-1));
			 		StringSelection stringSelection = new StringSelection(currentTextArea.getSelectedText());
			 		Toolkit.getDefaultToolkit().getSystemSelection().setContents(stringSelection, null);
			 		//highlight(h,0,newLinesPos.get(timesNexted-1),new Color(80,220,100));
			 		timesNexted--;
			 		textField.setText(timesNexted+"");
		 		}else {
		 			timesNexted=0;
		 			textField.setText(timesNexted+"");
		 		}
		 	}
		 });
		 GridBagConstraints gbc_btnPrevious = new GridBagConstraints();
		 gbc_btnPrevious.fill = GridBagConstraints.BOTH;
		 gbc_btnPrevious.insets = new Insets(0, 0, 5, 5);
		 gbc_btnPrevious.gridx = 1;
		 gbc_btnPrevious.gridy = 3;
		 sToSPanel.add(btnPrevious, gbc_btnPrevious);
		 
		 JButton btnNext = new JButton("Next");
		 btnNext.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		next();
		 	}
		 });
		 
		 textField = new JTextField();
		 textField.setEditable(false);
		 textField.setHorizontalAlignment(SwingConstants.CENTER);
		 textField.setText("0");
		 GridBagConstraints gbc_textField = new GridBagConstraints();
		 gbc_textField.fill = GridBagConstraints.BOTH;
		 gbc_textField.insets = new Insets(0, 0, 5, 5);
		 gbc_textField.gridx = 2;
		 gbc_textField.gridy = 3;
		 sToSPanel.add(textField, gbc_textField);
		 textField.setColumns(10);
		 GridBagConstraints gbc_btnNext = new GridBagConstraints();
		 gbc_btnNext.fill = GridBagConstraints.BOTH;
		 gbc_btnNext.insets = new Insets(0, 0, 5, 5);
		 gbc_btnNext.gridx = 3;
		 gbc_btnNext.gridy = 3;
		 sToSPanel.add(btnNext, gbc_btnNext);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnCurrent = new JMenu("File");
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
				}
				currentTextArea.setCaretPosition(0);
				newLinesPos.clear();
				newLinesPos.add(new Integer(0));
				String text = currentTextArea.getText();
		 		int newLinesCount = 0;
		 		for(int i = 0; i<text.length();i++) {
		 			if(text.charAt(i)=='\n') {
		 				newLinesCount++;
		 				if(newLinesCount == LINES_PER_COPY ) {
		 					newLinesCount = 0;
		 					newLinesPos.add(new Integer(i));
		 				}
		 			}
		 		}
		 		newLinesPos.add(new Integer(text.length()));
	 			timesNexted=0;
	 			textField.setText(timesNexted+"");
		 		next();
			}
		});
		mnCurrent.add(mntmOpenCurrent);
		setVisible(true);
	}
	void clean() {
		currentTextArea.setText("");
		setTitle("Copy NO FILE SELECTED");
	}
	void setCurrentPath(String newCurrentPath){
		currentPath = Paths.get(newCurrentPath);
		setTitle(currentPath.getFileName().toString());
	}

	private void next() {
 		if(timesNexted<newLinesPos.size()-1) {
	 		currentTextArea.requestFocusInWindow();
	 		currentTextArea.setSelectionStart(newLinesPos.get(timesNexted));
	 		currentTextArea.setSelectionEnd(newLinesPos.get(timesNexted+1));
	 		//highlight(h,0,newLinesPos.get(timesNexted),new Color(80,220,100));
	 		StringSelection stringSelection = new StringSelection(currentTextArea.getSelectedText());
	 		Toolkit.getDefaultToolkit().getSystemSelection().setContents(stringSelection, null);
	 		timesNexted++;
	 		textField.setText(timesNexted+"");
 		}else {
	 		timesNexted =newLinesPos.size();
	 		textField.setText(timesNexted+"");
 		}
	}
}
