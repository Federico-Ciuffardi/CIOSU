package gui;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.github.federico_ciuffardi.util.Prefs;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
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
		setTitle("IOSU");
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
		
		JMenu mnMisc = new JMenu("Misc");
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
