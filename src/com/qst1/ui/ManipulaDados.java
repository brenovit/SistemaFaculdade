package com.qst1.ui;

import java.util.List;

import com.qst1.dao.AlunoDAO;
import com.qst1.dao.GradeEscolar;
import com.qst1.persistencia.SaveLoadFile;
import com.qst1.vo.Aluno;
import com.qst1.vo.Disciplina;
import com.recursos.InOut;

public class ManipulaDados{
	
	private static AlunoDAO dataAluno = new AlunoDAO();
	private static GradeEscolar grade = new GradeEscolar();
	private static SaveLoadFile SaveLoad = new SaveLoadFile();
	
	protected static void CadastrarAluno(Aluno aluno){
		if(dataAluno.isConnected())
			dataAluno.Create(aluno);
	}
	
	protected static void AtualizarAluno(Aluno aluno){
		aluno.setCPF(aluno.getCPF());
		aluno.setNome(aluno.getNome());
		aluno.setMatricula(aluno.getMatricula());
		if(dataAluno.isConnected())
			dataAluno.Update(aluno);
	}
	
	protected static void RemoverAluno(Aluno aluno){
		aluno.setMatricula(aluno.getMatricula());
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
		aluno.setMatricula(aluno.getMatricula());
		if(dataAluno.Find(aluno,true) != -1){
			aluno.setNome(aluno.getNome());
			aluno.setCPF(aluno.getCPF());
			aluno.setMatricula(aluno.getMatricula());
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
		Disciplina disc;
		disc = new Disciplina("Programação Orientada a Objetos");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Estrutura de Dados");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Análise de Sistemas de Informações Comerciais");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Projeto Integrador");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Lógica de Programação");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Estatística");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Banco de Dados");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Sistemas Operacionais");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Sistemas de Informações");
		grade.CadastrarDisciplina(disc);
	}
	
	protected static AlunoDAO getListaAluno(){
		return dataAluno;
	}
	
	protected static GradeEscolar getGradeEscolar(){
		return grade;
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
     * @param rowIndex
     * @return
     */
	protected static void LimparLista(){
		dataAluno.LimparLista();
	}
	
	protected static boolean Conectar(){
		dataAluno.Connect();
		if(dataAluno.isConnected())
			return true;
		return false;
	}
	
	protected static void Desconectar(){
		dataAluno.Disconnect();
	}
}
