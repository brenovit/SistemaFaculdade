package com.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;

public class testes extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testes frame = new testes();
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
	public testes() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel, "name_2979031439725");
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_1.setBackground(Color.RED);
		getContentPane().add(panel_1, "name_2979038576035");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.GREEN);
		getContentPane().add(panel_2, "name_2979045178348");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.YELLOW);
		getContentPane().add(panel_3, "name_2979051657185");
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.BLUE);
		getContentPane().add(panel_4, "name_2979058204976");

	}

}
