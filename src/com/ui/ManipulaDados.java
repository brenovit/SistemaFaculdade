package com.ui;

import java.util.List;

import com.dao.AlunoDAO;
import com.dao.Banco;
import com.dao.GradeEscolar;
import com.persistencia.SaveLoadFile;
import com.recursos.InOut;
import com.vo.Aluno;
import com.vo.Disciplina;

public class ManipulaDados{
	
	private static AlunoDAO dataAluno = new AlunoDAO();
	private static GradeEscolar grade = new GradeEscolar();
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
		if(dataAluno.Find(aluno,true) != -1){
			return true;
		}
		return false;
	}
	
	protected static boolean AdicionarMateria(Aluno aluno, Disciplina disc){		
		if(dataAluno.FindMateria(aluno, disc) == -1){
			grade.CadastrarGrade(aluno, disc);	
			return true;
		}
		return false;		
	}
	
	protected static void RemoverMateria(Aluno aluno, Disciplina disc){		
		dataAluno.RemoverGrade(aluno, disc);
	}
	
	protected static List<Disciplina> DisciplinasCadastradas(Aluno aluno){			
		List<Disciplina> listaDisciplina = null;
		
		if(dataAluno.Find(aluno, true) != -1){
			listaDisciplina = aluno.getMaterias();
		}	
		
		return listaDisciplina;
	}
		
	protected static boolean EditarNota(Aluno aluno, Disciplina disc, Double nota){		
		if(dataAluno.AddNota(aluno, disc, nota)){
			return true;
		}
		return false;
	}
	
	public static void CadastrarDisciplinas(){
		if(grade.isEmpty()){
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
				grade.Create(disc);
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
	
	protected static GradeEscolar getGradeEscolar(){
		return grade;
	}
	
	protected static List<Disciplina> getListDisciplina(){
		return grade.getLista();
	}
	protected static void MudaCampos(Aluno aluno){
		InternalFrameCadastroAluno.MudarCampos(aluno);
		InternalFrameCadastrarGradeAluno.MudarCampos(aluno);
		InternalFrameCadastrarGradeAluno.AttLista(aluno);
		InternalFrameInserirNota.MudarCampos(aluno);
	}
	
	protected static void Salvar(String arquivo){
		List<Aluno> listAluno = dataAluno.getLista();
		SaveLoad.SaveData(arquivo, listAluno);
	}
	
	protected static void Carregar(String arquivo){	
		if(!SaveLoad.LoadData(grade, arquivo, dataAluno)){
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
		dataAluno.Read();
		grade.Read();
	}
}
