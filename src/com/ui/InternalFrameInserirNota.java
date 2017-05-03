
package com.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.recursos.GradeAlunoTable;
import com.recursos.InOut;
import com.vo.Aluno;
import com.vo.Disciplina;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InternalFrameInserirNota extends JInternalFrame {
	protected	static 	JTextField 	txtPesquisa;
	protected 	static 	JTextField 	txtMatricula;
	protected 	static 	JTextField 	txtNome;
	protected 	static 	JTextField 	txtDisciplina;
	protected 	static	JTextField 	txtNota;
	protected	JButton	btnNota;
	protected	JButton	btnConfirmar;
	protected	JButton	btnCancelar;
	protected	JButton	btnPesquisar;
	
	protected JLabel		lblStatus;
	private static JTable table;
	private static GradeAlunoTable modelo;
	private JLabel lblTabela;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InternalFrameInserirNota frame = new InternalFrameInserirNota();
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
	public InternalFrameInserirNota() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setFrameIcon(new ImageIcon(InternalFrameInserirNota.class.getResource("/com/images/nota32.png")));
		setTitle("Inserir Nota");
		setClosable(true);
		setIconifiable(true);
		setBounds(100, 100, 370, 470);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		modelo = new GradeAlunoTable();
		
		JPanel statusBar = new JPanel();
		statusBar.setBackground(SystemColor.controlHighlight);
		FlowLayout fl_statusBar = (FlowLayout) statusBar.getLayout();
		fl_statusBar.setAlignment(FlowLayout.LEFT);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		
		lblStatus = new JLabel("Pronto");
		statusBar.add(lblStatus);
		
		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);
		
		lblTabela = new JLabel("Tabela de Disciplinas Cadastradas");
		lblTabela.setBounds(88, 215, 178, 15);
		lblTabela.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mainPanel.add(lblTabela);
		
		JPanel panelAzul = new JPanel();
		panelAzul.setBounds(0, 0, 354, 50);
		panelAzul.setLayout(null);
		panelAzul.setBackground(new Color(153, 204, 255));
		mainPanel.add(panelAzul);
		
		btnNota = new JButton("");
		btnNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO Botão Editar Nota
								
				if(CamposVazios()){
					return;
				}
				
				int linha  = table.getSelectedRow();
				if(linha == -1)
					return;
				
				AtivaCampos(true);
				FrmPrincipal.setTableEnable(false);
				txtNota.requestFocus();
				lblStatus.setText("Editando nota");
			}
		});
		btnNota.setIcon(new ImageIcon(InternalFrameInserirNota.class.getResource("/com/images/notaedit32.png")));
		btnNota.setToolTipText("Editar Nota");
		btnNota.setForeground(Color.BLACK);
		btnNota.setBackground(Color.BLACK);
		btnNota.setBounds(10, 5, 40, 40);
		panelAzul.add(btnNota);
		
		btnConfirmar = new JButton("");
		btnConfirmar.setEnabled(false);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Botão Confirmar				
				int linha  = table.getSelectedRow();
				if(linha == -1)
					return;		
				
				Disciplina disc = modelo.getValue(linha);
				
				Double nota = Double.parseDouble(txtNota.getText());
				
				if(nota > 10){
					InOut.OutMessage("A nota maxima possivel é 10", "ATENÇÃO", 0);
					nota = 10.0;
				}
										
				Integer matricula = Integer.parseInt(txtMatricula.getText());
				
				Aluno aluno = new Aluno();				
				aluno.setMatricula(matricula);
				
				if(ManipulaDados.EditarNota(aluno, disc, nota)){					
					PreencherTabela(aluno);
				}else{
					return;
				}
				AtivaCampos(false);
				FrmPrincipal.setTableEnable(true);
				lblStatus.setText("Pronto");
			}
		});
		btnConfirmar.setIcon(new ImageIcon(InternalFrameInserirNota.class.getResource("/com/images/notaconfirm32.png")));
		btnConfirmar.setToolTipText("Confirmar Edi\u00E7\u00E3o");
		btnConfirmar.setBackground(Color.BLACK);
		btnConfirmar.setBounds(254, 5, 40, 40);
		panelAzul.add(btnConfirmar);
		
		btnCancelar = new JButton("");
		btnCancelar.setEnabled(false);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Botão Cancelar
				AtivaCampos(false);
				FrmPrincipal.setTableEnable(true);
				lblStatus.setText("Pronto");
			}
		});
		btnCancelar.setIcon(new ImageIcon(InternalFrameInserirNota.class.getResource("/com/images/notacancel32.png")));
		btnCancelar.setToolTipText("Cancelar Edi\u00E7\u00E3o");
		btnCancelar.setBackground(Color.BLACK);
		btnCancelar.setBounds(304, 5, 40, 40);
		panelAzul.add(btnCancelar);
		
		JPanel panelVerde = new JPanel();
		panelVerde.setBounds(0, 50, 354, 50);
		panelVerde.setLayout(null);
		panelVerde.setBackground(new Color(153, 255, 153));
		mainPanel.add(panelVerde);
		
		JLabel label = new JLabel("Pesquisar:");
		label.setFont(new Font("Cambria", Font.BOLD, 14));
		label.setBounds(10, 13, 70, 20);
		panelVerde.add(label);
		
		txtPesquisa = new JTextField();
		txtPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPesquisa.setColumns(10);
		txtPesquisa.setBounds(90, 13, 181, 20);
		panelVerde.add(txtPesquisa);
		
		btnPesquisar = new JButton("");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Botão Pesquisar Aluno
				if(ManipulaDados.ValidaPesquisa(txtPesquisa.getText()))
					return;
				String pesquisa = txtPesquisa.getText();
				Aluno aluno = new Aluno();
				aluno.setMatricula(Integer.parseInt(pesquisa));
				if(ManipulaDados.PesquisarAluno(aluno)){
					InOut.OutMessage("Aluno encontrado");
					MudarCampos(aluno);
				}else{
					InOut.OutMessage("Aluno não encontrado");
				}
			}
		});
		btnPesquisar.setIcon(new ImageIcon(InternalFrameInserirNota.class.getResource("/com/images/usersearch32.png")));
		btnPesquisar.setToolTipText("Pesquisar pela Matr\u00EDcula do Aluno");
		btnPesquisar.setBackground(Color.BLACK);
		btnPesquisar.setBounds(304, 5, 40, 40);
		panelVerde.add(btnPesquisar);
		
		JPanel panelDados = new JPanel();
		panelDados.setBounds(0, 100, 354, 130);
		panelDados.setLayout(null);
		mainPanel.add(panelDados);
		
		JLabel label_1 = new JLabel("Matricula:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(10, 12, 58, 14);
		panelDados.add(label_1);
		
		JLabel label_2 = new JLabel("Nome:");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_2.setBounds(10, 36, 46, 14);
		panelDados.add(label_2);
		
		txtMatricula = new JTextField();
		txtMatricula.setEditable(false);
		txtMatricula.setColumns(10);
		txtMatricula.setBounds(78, 11, 86, 20);
		panelDados.add(txtMatricula);
		
		txtNome = new JTextField();
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNome.setEnabled(false);
		txtNome.setColumns(10);
		txtNome.setBounds(78, 34, 200, 20);
		panelDados.add(txtNome);
		
		JLabel label_3 = new JLabel("Disciplina:");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_3.setBounds(10, 63, 71, 14);
		panelDados.add(label_3);
		
		txtDisciplina = new JTextField();
		txtDisciplina.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDisciplina.setEnabled(false);
		txtDisciplina.setColumns(10);
		txtDisciplina.setBounds(78, 61, 200, 20);
		panelDados.add(txtDisciplina);
		
		JLabel lblNota = new JLabel("Nota:");
		lblNota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNota.setBounds(10, 90, 71, 14);
		panelDados.add(lblNota);
		
		txtNota = new JTextField();
		txtNota.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNota.setEnabled(false);
		txtNota.setColumns(10);
		txtNota.setBounds(78, 88, 86, 20);
		panelDados.add(txtNota);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 241, 354, 164);
		mainPanel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//TODO Evento > Click > Tabela
				int linha  = table.getSelectedRow();	
				
				Disciplina disc = modelo.getValue(linha);
				
				MudaCamposDisciplina(disc);
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(modelo);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(60);
	}
	
	private boolean CamposVazios(){
		if(txtMatricula.getText().equals("") || txtDisciplina.getText().equals("")){
			InOut.OutMessage("Para Alterar a nota de um Aluno: "
					+ "\n1º - Selecione-o na Tabela de Alunos Cadastrados"
					+ "\n2º - Selecione a materia na Tabela de Disciplinas Cadastradas","Atenção",2);
			return true;
		}
		return false;
	}
	
	private void AtivaCampos(boolean estado){
		//TODO AtivaCampos
		txtNota.setEnabled(estado);
		
		btnConfirmar.setEnabled(estado);
		btnCancelar.setEnabled(estado);
		
		btnNota.setEnabled(!estado);		
		btnPesquisar.setEnabled(!estado);		
	}
	private void MudaCamposDisciplina(Disciplina disc){
		txtDisciplina.setText(disc.getNome());
		txtNota.setText(disc.getNota().toString());
	}
	
	protected static void MudarCampos(Aluno aluno){
		//TODO MudaCampos
		txtNome.setText(aluno.getNome());
		txtMatricula.setText(aluno.getMatricula().toString());
		PreencherTabela(aluno);
	}
	
	protected void MudaDisciplina(Disciplina disc){
		txtDisciplina.setText(disc.getNome());
		txtNota.setText(disc.getNota().toString());
	}
	
	protected static void PreencherTabela(Aluno aluno){
		//TODO PreencherTabela
		List<Disciplina> listaDisciplina = ManipulaDados.DisciplinasCadastradas(aluno);
		try{
			modelo.setValue(listaDisciplina);			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
}
