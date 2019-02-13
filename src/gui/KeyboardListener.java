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
		if(pasting) return;
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
		if(pasting) return;
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		char toSend = arg0.getKeyChar();
		System.out.println();
		System.out.println((byte)toSend);

 		if(toSend == 10) {//if LF
 			toSend = 13;//change to CR
 		}else if(toSend == 127) {//if DEL
 			toSend = 4;//change to EOT (for the DEL functionality to work properly)
 		}else if(toSend == 22) {//if CRTL+V
 			if(arg0.isShiftDown()) {//
 				paste();
 				return;
 			}
 		}else if(toSend == 3) {//
 			if(pasting) {
 				stopPasting = true;
 			}
 			if(arg0.isShiftDown()) {//copy
 				StringSelection stringSelection = new StringSelection(jText.getSelectedText());
 				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
 				return;
 			}
		}
 		serialPort.send(toSend);
	}
	private void paste() {
		(new Thread() {
				@Override
				public void run() {
					float sTNS = System.nanoTime();
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
		 			float eTNS = System.nanoTime();
		 			System.out.println("Time elapsed: "+(eTNS-sTNS)/1000000000+" secs");
				}		
			}).start();
	}
}
