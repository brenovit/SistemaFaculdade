package com.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.dao.GradeEscolar;
import com.recursos.GradeAlunoTable;
import com.recursos.InOut;
import com.vo.Aluno;
import com.vo.Disciplina;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class InternalFrameCadastrarGradeAluno extends JInternalFrame {
	
	private JTextField txtMateria;
	private static JTextField txtMatricula;
	private static JTextField txtNome;
	private JTextField txtPesquisa;
	private static JTable table;
	private static JList list;
	
	private static GradeAlunoTable modeloT;
	private static DefaultListModel modeloL = new DefaultListModel();
	
	private		Integer	codigo;
	private		int		pos = -1;
	private		String	materia;
	private		Integer	matricula;
	private		String	pesquisa;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InternalFrameCadastrarGradeAluno frame = new InternalFrameCadastrarGradeAluno();
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
	public InternalFrameCadastrarGradeAluno() {
		setIconifiable(true);
		setFrameIcon(new ImageIcon(InternalFrameCadastrarGradeAluno.class.getResource("/com/images/disc32.png")));
		setTitle("Gerir Grade Aluno");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setBounds(100, 100, 475, 430);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		modeloT = new GradeAlunoTable();
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblDisciplinasCadastradas = new JLabel("Disciplinas Cadastradas");
		lblDisciplinasCadastradas.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisciplinasCadastradas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDisciplinasCadastradas.setBounds(300, 50, 149, 20);
		panel_1.add(lblDisciplinasCadastradas);
		
		list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pos = list.getSelectedIndex();				
			}
		});
		list.setVisibleRowCount(20);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(300, 70, 150, 295);
		list.setModel(modeloL);
		panel_1.add(list);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(2, 50, 293, 89);
		panel_1.add(panel_2);
		
		JLabel label = new JLabel("Matricula:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(10, 12, 58, 14);
		panel_2.add(label);
		
		JLabel label_1 = new JLabel("Nome:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(10, 36, 46, 14);
		panel_2.add(label_1);
		
		txtMatricula = new JTextField();
		txtMatricula.setEditable(false);
		txtMatricula.setColumns(10);
		txtMatricula.setBounds(78, 11, 86, 20);
		panel_2.add(txtMatricula);
		
		txtNome = new JTextField();
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNome.setEnabled(false);
		txtNome.setColumns(10);
		txtNome.setBounds(78, 34, 200, 20);
		panel_2.add(txtNome);
		
		JLabel lblDisciplina = new JLabel("Disciplina:");
		lblDisciplina.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDisciplina.setBounds(10, 63, 71, 14);
		panel_2.add(lblDisciplina);
		
		txtMateria = new JTextField();
		txtMateria.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMateria.setEnabled(false);
		txtMateria.setColumns(10);
		txtMateria.setBounds(78, 61, 200, 20);
		panel_2.add(txtMateria);
		
		JPanel panelVerde = new JPanel();
		panelVerde.setLayout(null);
		panelVerde.setBackground(new Color(153, 255, 153));
		panelVerde.setBounds(110, 0, 349, 50);
		panel_1.add(panelVerde);
		
		JLabel label_3 = new JLabel("Pesquisar:");
		label_3.setFont(new Font("Cambria", Font.BOLD, 14));
		label_3.setBounds(10, 13, 70, 20);
		panelVerde.add(label_3);
		
		txtPesquisa = new JTextField();
		txtPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPesquisa.setColumns(10);
		txtPesquisa.setBounds(90, 13, 181, 20);
		panelVerde.add(txtPesquisa);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO Pesquisar > Pesquisar
				if(ManipulaDados.ValidaPesquisa(txtPesquisa.getText()))
					return;
				String pesquisa = txtPesquisa.getText();
				Aluno aluno = new Aluno();
				aluno.setMatricula(Integer.parseInt(pesquisa));
				if(ManipulaDados.PesquisarAluno(aluno)){
					InOut.OutMessage("Aluno Encontrado");
					MudarCampos(aluno);
				}else{
					InOut.OutMessage("Aluno Não Encontrado");
				}
			}
		});
		button.setIcon(new ImageIcon(InternalFrameCadastrarGradeAluno.class.getResource("/com/images/usersearch32.png")));
		button.setToolTipText("Pesquisar pela Matr\u00EDcula do Aluno");
		button.setBackground(Color.BLACK);
		button.setBounds(280, 5, 40, 40);
		panelVerde.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 161, 295, 204);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int linha = table.getSelectedRow();
				materia = modeloT.getValueAt(linha, 1).toString();
				txtMateria.setText(materia);
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(modeloT);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(61);
		table.getColumnModel().getColumn(1).setPreferredWidth(224);
		
		JLabel lblTabelaDeDisciplinas = new JLabel("Tabela de Disciplinas");
		lblTabelaDeDisciplinas.setHorizontalAlignment(SwingConstants.CENTER);
		lblTabelaDeDisciplinas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTabelaDeDisciplinas.setBounds(-5, 138, 295, 20);
		panel_1.add(lblTabelaDeDisciplinas);
		
		JPanel panelAzul = new JPanel();
		panelAzul.setLayout(null);
		panelAzul.setBackground(new Color(153, 204, 255));
		panelAzul.setBounds(0, 0, 110, 50);
		panel_1.add(panelAzul);
		
		JButton btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO Adcionar materia > Aluno
				if(CamposVazios("Adicionar uma máteria em"))
					return;
				if(txtMateria.getText().equals("")){
					return;
				}else{
					int linha = table.getSelectedRow();
					if(linha == -1)
						return;
					
					matricula = Integer.parseInt(txtMatricula.getText());
					codigo = Integer.parseInt(modeloT.getValueAt(linha, 0).toString());									
					
					Disciplina disc = new Disciplina();
					Aluno aluno = new Aluno();
					
					disc.setCodigo(codigo);
					aluno.setMatricula(matricula);
					
					if(ManipulaDados.AdicionarMateria(aluno, disc)){
						AttLista(aluno);
					}else{
						InOut.OutMessage("Não foi possivel adicionar ["+materia+"] em: "+ txtNome.getText()+
								"\nPor favor verifique se a disciplina já está Cadastrada no Aluno\n","Erro");
					}
				}
			}
		});
		btnAdicionar.setIcon(new ImageIcon(InternalFrameCadastrarGradeAluno.class.getResource("/com/images/discadd32.png")));
		btnAdicionar.setToolTipText("Adicionar Disciplina");
		btnAdicionar.setForeground(Color.BLACK);
		btnAdicionar.setBackground(Color.BLACK);
		btnAdicionar.setBounds(10, 5, 40, 40);
		panelAzul.add(btnAdicionar);
		
		JButton btnRemover = new JButton("");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Adcionar materia > Aluno
				if(CamposVazios("Remover uma máteria de"))
					return;
				
				if(pos == -1){
					InOut.OutMessage("Para remover uma Materia, primeiro selecione-a na lista de Disciplinas Cadastradas");
					return;
				}
				
				matricula = Integer.parseInt(txtMatricula.getText());
				codigo = ((Disciplina) ((DefaultListModel)list.getModel()).getElementAt(pos)).getCodigo();
				pos = -1;
				
				Disciplina disc = new Disciplina();
				Aluno aluno = new Aluno();
				
				disc.setCodigo(codigo);
				aluno.setMatricula(matricula);
								
				ManipulaDados.RemoverMateria(aluno, disc);
				AttLista(aluno);				
			}
		});
		btnRemover.setIcon(new ImageIcon(InternalFrameCadastrarGradeAluno.class.getResource("/com/images/discdel32.png")));
		btnRemover.setToolTipText("Remover Disciplina");
		btnRemover.setBackground(Color.BLACK);
		btnRemover.setBounds(60, 5, 40, 40);
		panelAzul.add(btnRemover);
		
		JPanel statusBar = new JPanel();
		statusBar.setBackground(SystemColor.controlHighlight);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblPronto = new JLabel("Pronto");
		statusBar.add(lblPronto);
		
		PreencherTabela();
	}
	
	protected static void AttLista(Aluno aluno){
		//TODO	AttLista		
		List<Disciplina> listaDisciplina = ManipulaDados.DisciplinasCadastradas(aluno);
				
		if(modeloL.getSize() > 0)
			modeloL.clear();

		if(listaDisciplina.size() == 0)
			return;
		
		for(Disciplina disc : listaDisciplina){
			((DefaultListModel)list.getModel()).addElement(disc);
		}		
	}
	
	protected static void PreencherTabela(){
		try{
			modeloT.setValue(ManipulaDados.getListDisciplina());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected static void MudarCampos(Aluno dados){
		txtMatricula.setText(dados.getMatricula().toString());
		txtNome.setText(dados.getNome());
	}
	
	private boolean CamposVazios(String msg){
		if(txtMatricula.getText().equals("") || txtNome.getText().equals("")){
			InOut.OutMessage("Para "+msg+" um Aluno, primeiro selecione-o na Tabela de Alunos\n");
			return true;
		}
		return false;
	}
}
