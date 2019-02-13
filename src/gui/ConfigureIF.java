package gui;

import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.InternalFrameEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import com.github.federico_ciuffardi.util.BinSemaphore;

class ConfigureIF extends JInternalFrame{
	
	static MainFrame mainFrame = MainFrame.getInstance();
	private static ConfigureIF instance = null;
	private static final long serialVersionUID = 1L;
	private JTextArea ConsoleTextArea;
	private serial.Port serialPort;
	private String consoleText=" ";
	private int cursorPos = 0;
	private BinSemaphore bSem;
	
	static void open() {
		if(instance == null) {
			instance = new ConfigureIF();
		}else {
			instance.init();
		}
	}
	private ConfigureIF() {
		firstTimeInit();
	}
	private void init() {
		Preferences prefs =  Preferences.userRoot().node("conf-comp");
		String serialID = prefs.get("SerialID","/dev/ttyS0");
		serialPort = serial.PortsHandler.getInstance().open(serialID);
		if(serialPort == null) {
			JOptionPane.showMessageDialog(this, "Serial Port error\nalready on use?\nhave privilege?\n", "ERROR", JOptionPane.ERROR_MESSAGE);
		}else {
			bSem = new BinSemaphore();
			if(!serialReadHandler.isAlive()) {//only once
				ConsoleTextArea.addKeyListener(new KeyboardListener(serialPort,bSem,ConsoleTextArea));
				serialReadHandler.start();
			}
	        setVisible(true);
	        try {
	        	setIcon(false);
				setSelected(true);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        moveToFront();
			ConsoleTextArea.grabFocus();
			try {
				setMaximum(true);
			} catch (PropertyVetoException e2) {
				e2.printStackTrace();
			}
		}
	}
	private void firstTimeInit() {
		setTitle("Console");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(30, 30,2*mainFrame.getWidth()/3,2*mainFrame.getHeight()/3);
        mainFrame.getContentPane().add(this);
		getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel ConsolePanel = new JPanel();
		getContentPane().add(ConsolePanel);
		ConsolePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		ConsolePanel.add(scrollPane, BorderLayout.CENTER);
		ConsoleTextArea = new JTextArea();
		scrollPane.setViewportView(ConsoleTextArea);
		ConsoleTextArea.setWrapStyleWord(true);
		ConsoleTextArea.setEditable(false);
		ConsoleTextArea.setLineWrap(true);
		ConsoleTextArea.setFont(new Font("Courier 10 Pitch", Font.PLAIN, 22));
		ConsoleTextArea.setForeground(Color.WHITE);
		ConsoleTextArea.setBackground(Color.BLACK);
		ConsoleTextArea.setFocusTraversalKeysEnabled(false);
		init();
	}
	Thread serialReadHandler = new Thread() {
		@Override
		public void run() {
			serialPort.send(12);
			try {
			   while (true){
			      byte[] readBuffer = serialPort.receive();
			      for(byte b:readBuffer) {
			    	  consoleProcess(b);
			      }
			      update1();
			   }
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}
	};
	private void consoleProcess(byte b) {
		if(b== 37 && KeyboardListener.pasting) {
				//error or warning 
		}
		if((b==35 || b == 62)  && KeyboardListener.pasting) {
			bSem.release();
		}
		if(b==8) {//Backspace
			if(cursorPos>0) {
				cursorPos--;
			}  
		}else if(b==7){//Bell
		  //sound or something
		}else {
			if(b==13) {
			  cursorPos =consoleText.length(); 
			}
			String aux1 = consoleText.substring(0,cursorPos);
			String aux2 = " ";
			if(cursorPos+1<consoleText.length()) {
				aux2 = consoleText.substring(cursorPos+1);
			}
			consoleText = aux1+(char)b+aux2;
			cursorPos++;	  
		  }
	}
	private void update1() {
		ConsoleTextArea.setText(consoleText);
		ConsoleTextArea.setCaretPosition(ConsoleTextArea.getText().length());
		Highlighter h = ConsoleTextArea.getHighlighter();
		h.removeAllHighlights();
		highlight(h,cursorPos,cursorPos+1, Color.GRAY );
	}
	private void highlight(Highlighter h,int startPos,int endPos, Color color ) {
		try {
			h.addHighlight(startPos,endPos,new DefaultHighlighter.DefaultHighlightPainter(color));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	@Override
    protected void fireInternalFrameEvent(int id) {
        if (id == InternalFrameEvent.INTERNAL_FRAME_CLOSING) {
        	serialPort.closePort();
        }
        super.fireInternalFrameEvent(id);
    }
}
