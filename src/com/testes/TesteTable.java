package com.testes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;

public class TesteTable extends JFrame {

	private JPanel contentPane;
	private JTable tblAcessos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TesteTable frame = new TesteTable();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TesteTable() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 569, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 11, 531, 294);
		contentPane.add(scrollPane);
		
		tblAcessos = new JTable();
		tblAcessos.setRowSelectionAllowed(false);
		tblAcessos.setCellSelectionEnabled(true);
		tblAcessos.setBorder(null);
		tblAcessos.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tblAcessos.setFillsViewportHeight(true);
		tblAcessos.setEnabled(false);
		scrollPane.setViewportView(tblAcessos);
		tblAcessos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"nome", "url", "Acessos"
			}
		));
		preencherTabela();
	}
	
	public void preencherTabela(){
		try{
			DefaultTableModel modelo = (DefaultTableModel) tblAcessos.getModel();
			if(modelo.getRowCount() > 0){
				modelo.setRowCount(0);
			}
			
			File arq = new File("dados.txt");
			FileReader fr = new FileReader(arq);
			BufferedReader br = new BufferedReader(fr);
			
			String linha = "";
			while((linha = br.readLine()) != null){
				Object[] obj = linha.split(";");
				modelo.addRow(obj);
			}
			
			fr.close();
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
