package ssdd.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class GUITracker {

	private JFrame frmClienteTracker;
	private JTextField textFieldID;
	private JTextField textFieldIP;
	private JTextField textFieldPortTrackers;
	private JTextField textFieldPortPeers;
	private JTable tableTrackers;
	private JTable tablePeers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITracker window = new GUITracker();
					window.frmClienteTracker.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUITracker() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClienteTracker = new JFrame();
		frmClienteTracker.setTitle("Cliente Tracker");
		frmClienteTracker.setBounds(100, 100, 450, 300);
		frmClienteTracker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelbtnSalir = new JPanel();
		frmClienteTracker.getContentPane().add(panelbtnSalir, BorderLayout.SOUTH);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelbtnSalir.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelbtnSalir.add(btnSalir);
		
		JPanel panelPestanas = new JPanel();
		frmClienteTracker.getContentPane().add(panelPestanas, BorderLayout.CENTER);
		panelPestanas.setLayout(new CardLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panelPestanas.add(tabbedPane, "name_13854292971972");
		
		JPanel panelTrackers = new JPanel();
		tabbedPane.addTab("Trackers", null, panelTrackers, null);
		panelTrackers.setLayout(new BorderLayout(0, 0));
		
		String[] columnNamesPeers = {"ID",
                "IP",
                "Puerto",
                "Torrent"};
		
		String[] columnNamesTrackers = {"ID",
                "Máster",
                "KeepAlive"};
		
		Object[][] dataPeers = {
			    
			};
		Object[][] dataTrackers = {
			    
		};
		
		
		tableTrackers = new JTable(dataTrackers,columnNamesTrackers);
		panelTrackers.add(tableTrackers, BorderLayout.CENTER);
		
		JScrollPane scrollPaneTrackers = new JScrollPane(tableTrackers);
		panelTrackers.add(scrollPaneTrackers, BorderLayout.CENTER);
		tableTrackers.setFillsViewportHeight(true);
		
		JPanel panelPeers = new JPanel();
		tabbedPane.addTab("Peers", null, panelPeers, null);
		panelPeers.setLayout(new BorderLayout(0, 0));
		
		tablePeers = new JTable(dataPeers,columnNamesPeers);
		tablePeers.setSurrendersFocusOnKeystroke(false);
		tablePeers.setShowVerticalLines(true);
		tablePeers.setShowHorizontalLines(true);
		tablePeers.setRowSelectionAllowed(true);
		tablePeers.setFillsViewportHeight(false);
		tablePeers.setEnabled(true);
		tablePeers.setColumnSelectionAllowed(false);
		tablePeers.setCellSelectionEnabled(false);
		panelPeers.add(tablePeers, BorderLayout.CENTER);
		
		JScrollPane scrollPanePeers = new JScrollPane(tablePeers);
		panelPeers.add(scrollPanePeers, BorderLayout.CENTER);
		tablePeers.setFillsViewportHeight(true);
		
		JPanel panelConfiguracion = new JPanel();
		tabbedPane.addTab("Configuraci\u00F3n", null, panelConfiguracion, null);
		panelConfiguracion.setLayout(null);
		
		textFieldID = new JTextField();
		textFieldID.setBounds(203, 25, 135, 20);
		panelConfiguracion.add(textFieldID);
		textFieldID.setColumns(10);
		
		textFieldIP = new JTextField();
		textFieldIP.setBounds(203, 56, 135, 20);
		panelConfiguracion.add(textFieldIP);
		textFieldIP.setColumns(10);
		
		textFieldPortTrackers = new JTextField();
		textFieldPortTrackers.setBounds(203, 87, 135, 20);
		panelConfiguracion.add(textFieldPortTrackers);
		textFieldPortTrackers.setColumns(10);
		
		textFieldPortPeers = new JTextField();
		textFieldPortPeers.setBounds(203, 118, 135, 20);
		panelConfiguracion.add(textFieldPortPeers);
		textFieldPortPeers.setColumns(10);
		
		JLabel lblID = new JLabel("ID");
		lblID.setBounds(88, 25, 46, 14);
		panelConfiguracion.add(lblID);
		
		JLabel lblIP = new JLabel("Direcci\u00F3n IP");
		lblIP.setBounds(88, 56, 85, 14);
		panelConfiguracion.add(lblIP);
		
		JLabel lblPuertoTrackers = new JLabel("Puerto Trackers");
		lblPuertoTrackers.setBounds(88, 90, 85, 14);
		panelConfiguracion.add(lblPuertoTrackers);
		
		JLabel lblPuertoPeers = new JLabel("Puerto Peers");
		lblPuertoPeers.setBounds(88, 121, 85, 14);
		panelConfiguracion.add(lblPuertoPeers);
	}
}
