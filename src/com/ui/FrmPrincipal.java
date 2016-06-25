package com.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.persistencia.Abrir;
import com.recursos.AlunosTable;
import com.recursos.InOut;
import com.vo.Aluno;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JToolBar;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class FrmPrincipal extends JFrame {

	private JPanel mainPane;
	
	private static boolean ProgramaJaRodou = false;
		
	private 	JDesktopPane 	desktopPane;
	
	protected 	JPanel 		contentPane;
	
	private		static		JTable		table;	
	private 	static		AlunosTable modelo;

	private boolean primeiraVez = true;
	
	private InternalFrameCadastroAluno 	frmCadAluno = new InternalFrameCadastroAluno();
	private InternalFrameCadastrarGradeAluno frmCadGradAluno = new InternalFrameCadastrarGradeAluno();
	private InternalFrameInserirNota frmEditNotAluno = new InternalFrameInserirNota();
	
	private JFileChooser fc = new JFileChooser();
	private String arquivo = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {			//Windows, Nimbus, Metal, CDE
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmPrincipal frame = new FrmPrincipal();
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
	public FrmPrincipal() {

		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//SalvarTabela();
				ManipulaDados.Desconectar();
			}
			@Override
			public void windowOpened(WindowEvent arg0) {
				IniciarSistema();
			}			
		});
				
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmPrincipal.class.getResource("/com/images/appicon2.png")));
		setTitle("Gerenciador de Faculdade");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 880, 660);		
		
		modelo = new AlunosTable();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JMenuItem mntmNovo = new JMenuItem("Novo");
		mntmNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mntmNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Menu > Novo
				Novo();
			}
		});
		mntmNovo.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/databasenew16.png")));
		mnArquivo.add(mntmNovo);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO Menu > Abrir Arquivo
				AbrirArquivo();
			}
		});
		mntmAbrir.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/open16.png")));
		mntmAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnArquivo.add(mntmAbrir);
		
		JMenuItem mntmSalvar = new JMenuItem("Salvar");
		mntmSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Menu > Salvar
				SalvarArquivo();
			}
		});
		mntmSalvar.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/save16.png")));
		mntmSalvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnArquivo.add(mntmSalvar);
		
		JMenuItem mntmSalvarComo = new JMenuItem("Salvar como...");
		mntmSalvarComo.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/saveas16.png")));
		mntmSalvarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnArquivo.add(mntmSalvarComo);
		
		JSeparator separator_1 = new JSeparator();
		mnArquivo.add(separator_1);
		
		JMenuItem mntmImportar = new JMenuItem("Importar");
		mntmImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO Menu > Importar
				Importar();
			}
		});
		mntmImportar.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/databaseimport16.png")));
		mntmImportar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, (InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK)));
		mnArquivo.add(mntmImportar);
		
		JMenuItem mntmExportar = new JMenuItem("Exportar");
		mntmExportar.setEnabled(false);
		mntmExportar.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/databaseexport16.png")));
		mntmExportar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, (InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK)));
		mnArquivo.add(mntmExportar);
		
		JMenuItem mntmVisualizar = new JMenuItem("Visualizar");
		mntmVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Menu > visualizar arquivo
				VisualizarArquivo();
			}
		});
		mntmVisualizar.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/databasesearch16.png")));
		mnArquivo.add(mntmVisualizar);
		
		JSeparator separator = new JSeparator();
		mnArquivo.add(separator);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO Menu > Sair
				SalvarTabela();
				if(InOut.ConfirmDialog("Deseja realmente Sair?", "Atenção") == 0){
					System.exit(0);
				}else{
					return;
				}
			}
		});
		mnArquivo.add(mntmSair);
		
		JMenu mnCadastro = new JMenu("Cadastro");
		menuBar.add(mnCadastro);
		
		JMenu mnAluno = new JMenu("Aluno");
		mnAluno.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/student.png")));
		mnCadastro.add(mnAluno);
		
		JMenuItem mntmGerirAlunos = new JMenuItem("Gerenciar Alunos");
		mntmGerirAlunos.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/user16.png")));
		mntmGerirAlunos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Menu > Cadastrar Aluno
				ChamarTelaGerirAluno();
			}
		});
		mnAluno.add(mntmGerirAlunos);
	
		JMenuItem mntmGerirGrade = new JMenuItem("Gerenciar Grade dos Alunos");
		mntmGerirGrade.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/disc16.png")));
		mntmGerirGrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO Menu > Gerenciar Grade
				ChamarTelaCadastrarMateria();
			}
		});
		mnAluno.add(mntmGerirGrade);
		
		JMenuItem mntmGerirNota = new JMenuItem("Gerenciar Notas do Aluno");
		mntmGerirNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Menu > Gerenciar nota aluno
				ChamarTelaGerirNota();
			}
		});
		mntmGerirNota.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/nota16.png")));
		mnAluno.add(mntmGerirNota);
		
		JMenu mnDisciplina = new JMenu("Disciplina");
		mnDisciplina.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/grade.png")));
		mnCadastro.add(mnDisciplina);
		
		JMenuItem mntmCadastrarDisciplina = new JMenuItem("Cadastrar disciplina");
		mntmCadastrarDisciplina.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/grade16.png")));
		mnDisciplina.add(mntmCadastrarDisciplina);
		
		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);
		
		JMenuItem menuItem = new JMenuItem("Ajuda");
		menuItem.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/help16.png")));
		mnAjuda.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("Sobre");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO	Menu > Sobre
				
			}
		});
		menuItem_1.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/info.png")));
		mnAjuda.add(menuItem_1);
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPane);
		mainPane.setLayout(new BorderLayout(0, 0));
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(UIManager.getColor("control"));
		mainPane.add(desktopPane);
		desktopPane.setLayout(null);
		
		JLabel lblTabelaAluno = new JLabel("Tabela de Alunos Cadastrados");
		lblTabelaAluno.setBounds(0, 0, 904, 15);
		lblTabelaAluno.setHorizontalAlignment(SwingConstants.LEFT);
		lblTabelaAluno.setFont(new Font("Tahoma", Font.PLAIN, 12));
		desktopPane.add(lblTabelaAluno);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 15, 380, 506);
		desktopPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//TODO Evento Clicar no registro da tabela de alunos
				int linha = table.getSelectedRow();
				
				Integer matricula = Integer.parseInt(modelo.getValueAt(linha, 0).toString());				
				String nome = modelo.getValueAt(linha, 1).toString();
				String CPF = modelo.getValueAt(linha, 2).toString();
		
				Aluno aluno = new Aluno(matricula, nome, CPF);
				
				ManipulaDados.MudaCampos(aluno);
			}
		});
		scrollPane.setViewportView(table);		
		table.setModel(modelo);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(140);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		mainPane.add(toolBar, BorderLayout.NORTH);
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar.add(toolBar_1);
		
		JButton btnSalvar = new JButton("");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO Button > Salvar
				SalvarArquivo();
			}
		});
		
		JButton btnAbrir = new JButton("");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Button > Abrir
				AbrirArquivo();
			}
		});
		
		JButton btnNovo = new JButton("");
		toolBar_1.add(btnNovo);
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Button > Exportar
				Novo();
			}
		});
		btnNovo.setToolTipText("Novo");
		btnNovo.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/databasenew32.png")));
		btnAbrir.setToolTipText("Abrir");
		toolBar_1.add(btnAbrir);
		btnAbrir.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/open.png")));
		toolBar_1.add(btnSalvar);
		btnSalvar.setToolTipText("Salvar");
		btnSalvar.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/save32.png")));
		
		JButton btnSalvarComo = new JButton("");
		btnSalvarComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Button > Salvar Como
				primeiraVez = true;
				SalvarArquivo();
			}
		});
		btnSalvarComo.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/saveas32.png")));
		btnSalvarComo.setToolTipText("Salvar Como...");
		toolBar_1.add(btnSalvarComo);
		
		JToolBar toolBar_3 = new JToolBar();
		toolBar.add(toolBar_3);
		
		JButton btnCadAluno = new JButton("");
		btnCadAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Button > Tela Cadastro Aluno
				ChamarTelaGerirAluno();
			}
		});
		toolBar_3.add(btnCadAluno);
		btnCadAluno.setBackground(UIManager.getColor("Button.background"));
		btnCadAluno.setToolTipText("Gerir Aluno");
		btnCadAluno.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/user32.png")));
		
		JButton btnNota = new JButton("");
		btnNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Button > Tela Inserir Nota Materia Aluno
				ChamarTelaGerirNota();
			}
		});
		btnNota.setToolTipText("Gerir Nota");
		toolBar_3.add(btnNota);
		btnNota.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/nota32.png")));
		
		JButton btnCadGradeAluno = new JButton("");
		btnCadGradeAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Button > Tela Cadastro Grade Aluno
				ChamarTelaCadastrarMateria();
			}
		});
		btnCadGradeAluno.setToolTipText("Gerir Disciplina");
		toolBar_3.add(btnCadGradeAluno);
		btnCadGradeAluno.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/disc32.png")));
		
		JButton btnCadGrade = new JButton("");
		toolBar_3.add(btnCadGrade);
		btnCadGrade.setEnabled(false);
		btnCadGrade.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/images/grade32.png")));
		
		JPanel statusBar = new JPanel();
		mainPane.add(statusBar, BorderLayout.SOUTH);
		desktopPane.setLayer(statusBar, 0);
		statusBar.setBackground(SystemColor.controlHighlight);
		FlowLayout fl_statusBar = (FlowLayout) statusBar.getLayout();
		fl_statusBar.setAlignment(FlowLayout.RIGHT);
		
		JLabel lblPorBrenoNunes = new JLabel("Por Breno Nunes");
		statusBar.add(lblPorBrenoNunes);
		lblPorBrenoNunes.setFont(new Font("Tahoma", Font.BOLD, 13));
	}
	
	private void Importar(){
		//TODO Método Importar
		ManipulaDados.LimparLista();		
		ManipulaDados.Carregar("DadosAluno.json");
		PreencherTabela();
	}
	private void Exportar(){
		//TODO Método Importar		
	}
	private void ChamarTelaGerirAluno(){
		//TODO Método ChamarTelaGerirAluno
		if(frmCadAluno.isIcon()){
			try {
				frmCadAluno.setIcon(false);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}
		desktopPane.setLayer(frmCadAluno, 1);
		frmCadAluno.setBounds(389,20, 400, 260);
		desktopPane.add(frmCadAluno);
		frmCadAluno.setVisible(true);
	}
	private void ChamarTelaGerirNota(){
		//TODO Método ChamarTelaGerirNota
		if(frmEditNotAluno.isIcon()){
			try{
				frmEditNotAluno.setIcon(false);
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		desktopPane.setLayer(frmEditNotAluno, 1);
		frmEditNotAluno.setBounds(389, 20, 370, 470);
		desktopPane.add(frmEditNotAluno);
		frmEditNotAluno.setVisible(true);
	}
    private void ChamarTelaCadastrarMateria(){
    	//TODO Método ChamarTelaCadastrarMateria
    	if(frmCadGradAluno.isIcon()){
			try {
				frmCadGradAluno.setIcon(false);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}
		desktopPane.setLayer(frmCadGradAluno, 1);
		frmCadGradAluno.setBounds(389, 20, 475, 430);
		desktopPane.add(frmCadGradAluno);
		frmCadGradAluno.setVisible(true);
	}
    
    private void Novo(){
    	//TODO metodo Novo
    	SalvarTabela();
    	ManipulaDados.LimparLista();
    	PreencherTabela();
    	setTitle("Gerenciador de Faculdade");
    }    
    private void SalvarTabela(){
    	/*if(modelo.getRowCount() > 0){
    		int op = InOut.ConfirmDialog("Deseja salvar a tabela atual?", "Salvar");
    		if(op == 0){//sim
    			SalvarArquivo();
    		}else if(op == 1){//não
    			//faz nada
    		}else{//cancelou
    			return;
    		}
    	}*/
    }
    private void VisualizarArquivo(){
    	try {
			Abrir.AbrirArquivo(arquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    private void AbrirArquivo(){
		//TODO Metodo abrir
    	ManipulaDados.LimparLista();
    	showOpenFileDialog();
		try{
			if(!arquivo.equals("") && arquivo.endsWith("json")){
				ManipulaDados.Carregar(arquivo);
				PreencherTabela();
				setTitle("Gerenciador de Faculdade - "+arquivo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
   	}    
    private void SalvarArquivo(){
		//TODO Método Salvar
    	if(primeiraVez && arquivo.equals("")){
    		showSaveFileDialog();
    		if(!arquivo.equals("") && arquivo.endsWith("json")){
				primeiraVez = false;
		    	ManipulaDados.Salvar(arquivo);
    		}
		}else{
	    	ManipulaDados.Salvar(arquivo);
		}    	
   	}
    
    private void showOpenFileDialog() {
    	//TODO Show Open File Dialog
        fc.setCurrentDirectory(new File(System.getProperty("user.home")));
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("JSON file(*.json)", "json"));
        fc.setAcceptAllFileFilterUsed(true);
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            arquivo = fc.getSelectedFile().getAbsolutePath();
            arquivo = arquivo.replace(".json", "");
            arquivo += ".json";
        }else{
        	arquivo = "";
        }
    }
    private void showSaveFileDialog() {
    	//TODO Show Save File Dialog
        fc.setCurrentDirectory(new File(System.getProperty("user.home")));
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("JSON file(*.json)", "json"));
        fc.setAcceptAllFileFilterUsed(true);
        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
        	arquivo = fc.getSelectedFile().getAbsolutePath()+".json";
        }else{
        	arquivo = "";
        }
    }
   
	protected static void setTableEnable(boolean mode){
		table.setEnabled(mode);
	}
	
	protected static void PreencherTabela(){
		try{			
			modelo.setValue(ManipulaDados.getListAluno());			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void IniciarSistema() {
		// TODO Iniciar Sistema
		if(!ManipulaDados.Conectar()){
			InOut.OutMessage("Infelizmente não foi possivel se conectar com o Servidor."
					+ "\nPor favor tente novamente mais tarde", "ERRO", 0);
			System.exit(0);
		}else{
			ManipulaDados.Desconectar();
			//checar se tem dado
			ManipulaDados.LerBanco();				//Inserir os alunos do banco do programa
			PreencherTabela();						//Atualizar tabela com os alunos
			ManipulaDados.CadastrarDisciplinas();	//Inserir as disciplinas 
		}
	}
}
