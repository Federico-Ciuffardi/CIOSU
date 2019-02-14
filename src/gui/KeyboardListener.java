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

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.JTextComponent;
import com.github.federico_ciuffardi.util.BinSemaphore;

/*
 * KeyboardListener meant to analyze the keys pressed on the cli and communicate through the serial port accordingly
 */

public class KeyboardListener implements KeyListener {
	private serial.Port serialPort;
	static boolean pasting = false;
	static boolean stopPasting = false;
	private List<Integer> newLinesPos = new ArrayList<Integer>();
	private BinSemaphore bSem;
	private JTextComponent jText;
	public KeyboardListener(serial.Port comPort, BinSemaphore mutex,JTextComponent jText) {
		serialPort = comPort;
		this.bSem = mutex;
		this.jText = jText;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			serialPort.send(2);
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			serialPort.send(6);
		}else if(e.getKeyCode() == KeyEvent.VK_UP) {
			serialPort.send(16);
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			serialPort.send(14);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		char toSend = arg0.getKeyChar();

 		if(toSend == 10) {//LF
 			toSend = 13;//change to CR
 		}else if(toSend == 127) {//DEL
 			toSend = 4;//change to EOT (for the DEL functionality to work properly)
 		}else if(toSend == 22) {//CRTL+V
 			if(arg0.isShiftDown()) {//+SHIFT
 				paste();
 				return;
 			}
 		}else if(toSend == 3) {//CTRL+C
 			if(pasting) {
 				stopPasting = true;
 			}
 			if(arg0.isShiftDown()) {//+SHIFT
 				//copy
 				StringSelection stringSelection = new StringSelection(jText.getSelectedText());
 				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
 				return;
 			}
		}
 		serialPort.send(toSend);
	}
	//creates a new thread and pastes one line after acquiring the binary semaphore causing it to need the release from another
	//thread before pasting the next line
	private void paste() {
		(new Thread() {
				@Override
				public void run() {
					//float sTNS = System.nanoTime();
					pasting = true;
		 			try {
		 				int prevBreak = -1;
						String copyedText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
						for(int i = 0; i<copyedText.length();i++) {
				 			if(copyedText.charAt(i)=='\n') {
			 					newLinesPos.add(new Integer(i));
			 					String toSend = copyedText.substring(prevBreak+1, i+1);
			 					prevBreak = i;
			 					bSem.acquire();
								if(stopPasting) {
									pasting = false;
									stopPasting = false;
									return;
								}
			 					for(int j = 0; j<toSend.length(); j++ ) {
			 						serialPort.send(toSend.charAt(j));
			 					}
			 				}
				 		}
						if(prevBreak == -1) prevBreak = 0;
						if(stopPasting) {
							pasting = false;
							stopPasting = false;
							return;
						}
						String toSend = copyedText.substring(prevBreak);
	 					for(int j = 0; j<toSend.length(); j++ ) {					
	 						serialPort.send(toSend.charAt(j));
	 					}
					} catch ( UnsupportedFlavorException | IOException e) {
						e.printStackTrace();
					}
		 			pasting = false;
		 			//float eTNS = System.nanoTime();
		 			//System.out.println("Time elapsed: "+(eTNS-sTNS)/1000000000+" secs");
				}		
			}).start();
	}
	
}
