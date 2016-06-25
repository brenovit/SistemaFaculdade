package com.ui;

import java.util.List;

import com.dao.AlunoDAO;
import com.dao.Banco;
import com.dao.DisciplinaDAO;
import com.persistencia.SaveLoadFile;
import com.recursos.InOut;
import com.vo.Aluno;
import com.vo.Disciplina;

public class ManipulaDados{
	
	private static AlunoDAO dataAluno = new AlunoDAO();
	private static DisciplinaDAO dataDisciplina = new DisciplinaDAO();
	
	private static SaveLoadFile SaveLoad = new SaveLoadFile();
	
	protected static void CadastrarAluno(Aluno aluno){
		dataAluno.Create(aluno);
	}
	
	protected static void AtualizarAluno(Aluno aluno){
		dataAluno.Update(aluno);
	}
	
	protected static void RemoverAluno(Aluno aluno){
		dataAluno.Delete(aluno);
	}
	
	protected static boolean ValidaPesquisa(String dadoPesquisa){
		if(dadoPesquisa.equals("")){
			InOut.OutMessage("Por Favor digite a matricula do aluno que deseja pesquisar no campo ao lado.", "Atenção", 1);
			return true;
		}
		return false;
	}
	
	protected static boolean PesquisarAluno(Aluno aluno){
		if(dataAluno.Read(aluno,true) != -1)
			return true;
		return false;
	}
	
	protected static boolean AdicionarMateria(Aluno aluno, Disciplina disc){		
		if(dataAluno.CadastrarGrade(aluno, disc))			
			return true;
		return false;		
	}
	
	protected static boolean RemoverMateria(Aluno aluno, Disciplina disc){
		if(dataAluno.RemoverGrade(aluno, disc))			
			return true;
		return false;		
	}
	
	protected static List<Disciplina> DisciplinasCadastradas(Aluno aluno){			//melhorar
		List<Disciplina> listaDisciplina = null;
		
		if(dataAluno.Read(aluno, true) != -1){
			listaDisciplina = aluno.getMaterias();
		}	
		
		return listaDisciplina;
	}
		
	protected static boolean EditarNota(Aluno aluno, Disciplina disc, Double nota){		
		if(dataAluno.AddNota(aluno, disc, nota))
			return true;
		return false;
	}
	
	public static void CadastrarDisciplinas(){
		if(dataDisciplina.isEmpty()){
			String [] discs = {
					"Programação Orientada a Objetos", 
					"Estrutura de Dados",
					"Análise de Sistemas de Informações Comerciais",
					"Projeto Integrador",
					"Lógica de Programação",
					"Estatística",
					"Banco de Dados",
					"Sistemas Operacionais",
					"Sistemas de Informações"};
			for(int i = 0; i < discs.length; i++){
				Disciplina disc = new Disciplina(discs[i]);
				dataDisciplina.Create(disc);
			}			
		}else{
			return;
		}
	}
	
	protected static AlunoDAO getAlunoDAO(){
		return dataAluno;
	}
	
	protected static List<Aluno> getListAluno(){
		return dataAluno.getLista();
	}
	
	protected static DisciplinaDAO getGradeEscolar(){
		return dataDisciplina;
	}
	
	protected static List<Disciplina> getListDisciplina(){
		return dataDisciplina.getLista();
	}
	
	protected static void MudaCampos(Aluno aluno){
		InternalFrameCadastroAluno.MudarCampos(aluno);			//Atualiza os campos da janela de cadastrar aluno
		InternalFrameCadastrarGradeAluno.MudarCampos(aluno);	//Atualiza os campos da janela de cadastrar grade em aluno
		InternalFrameCadastrarGradeAluno.AttLista(aluno);		//Atualiza os campos da janela de cadastrar aluno
		InternalFrameInserirNota.MudarCampos(aluno);			//Atualiza os campos da janela de alterar as disciplinas do aluno
	}
	
	protected static void Salvar(String arquivo){
		List<Aluno> listAluno = dataAluno.getLista();
		SaveLoad.SaveData(arquivo, listAluno);
	}
	
	protected static void Carregar(String arquivo){	
		if(!SaveLoad.LoadData(dataDisciplina, arquivo, dataAluno)){
			InOut.OutMessage("Não foi possivel Importar os dados,\n"
					+ "Por favor verifique se o arquivo se encontra no sistema", "ERRO", 2);
		}
	}
	
	//detalhe de um metodo
	 /**
     * Limpa a lista de Alunos	
     * @param
     * @return
     */
	protected static void LimparLista(){
		dataAluno.LimparLista();
	}
	
	protected static boolean Conectar(){
		Banco.Connect();
		if(Banco.isConnected())
			return true;
		return false;
	}
	
	protected static void Desconectar(){
		Banco.Disconnect();
	}
	
	protected static boolean isEmpty(){
		return false;		
	}
	
	protected static void LerBanco(){
		dataDisciplina.FeedSystem();
		dataAluno.FeedSystem();
	}
}
