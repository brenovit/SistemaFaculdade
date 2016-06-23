package com.ui;

import com.dao.AlunoDAO;
import com.dao.GradeEscolar;
import com.recursos.InOut;
import com.vo.Aluno;
import com.vo.Disciplina;

public class TelaGrade {
	private static SystemManager sm = new SystemManager();
	
	private static GradeEscolar grade = MainQuestao1.getGradeEscolar();
	private static AlunoDAO listaAluno = MainQuestao1.getListaAluno();
	private static Disciplina disc;
	private static Aluno aluno;	
	
	protected static void MenuGrade(){
		aluno = new Aluno();
		disc = new Disciplina();
		int op;
		do{
			String opcoes = "Digite um dos Numeros abaixo:\n"+
							"1 - Cadastrar Grade do Aluno\n"+
							"2 - Listar Disciplinas Disponiveis\n"+
							"3 - Listar Materias Cadastradas em um Aluno\n"+
							"4 - Remover Grade de Aluno\n"+
							"0 - Voltar";
			op = InOut.InInt(opcoes);
			switch(op){
				case 0:
					break;
				case 1:
					CadastrarGradeAluno();
					break;
				case 2:
					InOut.OutMessage(ListarDisciplinas());
					break;
				case 3:
					InOut.OutMessage(MostrarMateriasAluno(aluno));
					break;
				case 4:
					RemoverGradeAluno(aluno, disc);
					break;
				default:
					InOut.OutMessage("Opção Invalida!", "Erro!");
					break;		
			}
			//listaAluno.SaveData("DadosAluno.json");
		}while(op != 0);
		MainQuestao1.Menu();
	}
	
	private static void CadastrarGradeAluno(){
		aluno = new Aluno();
		disc = new Disciplina();
		if(TelaAluno.ProcurarAluno(aluno, "Cadastrar uma Matéria")){		//procura o aluno
			if(ProcurarDisciplina(disc,"Cadastrar no Aluno")){	//procura a materia
				if(listaAluno.FindMateria(aluno, disc) == -1){	//verifica se o aluno ja esta cadastrado na materia
					grade.CadastrarGrade(aluno, disc);
				}else{
					InOut.OutMessage("Disciplina já cadastrada neste Aluno", "ERRO");
				}
			}
		}
	}
	
	protected static String MostrarMateriasAluno(Aluno aluno){
		if(TelaAluno.ProcurarAluno(aluno, "Visualizar as Materias")){
			String msg = sm.DadosAlunoEncontrado(aluno) +
					"\nMaterias do Aluno:\n------------------------------------"+
					listaAluno.ShowDisciplinasMatriculadas(aluno);						 
			return msg;
		}
		sm.AlunoNaoEncontrado();
		return null;
	}
	
	protected static String ListarDisciplinas(){
		String msg = "Disciplinas Cadastradas no Sistema"+
				     "\n------------------------------------"+grade.Show();
		return msg;
	}

	private static void RemoverGradeAluno(Aluno aluno, Disciplina disc){
		String disciplinasCadastradas = TelaNota.MostrarMateriasAluno(aluno);
		if(TelaAluno.ProcurarAluno(aluno, "Remover uma Máteria")){
			if(TelaNota.ProcurarDisciplinaAluno(aluno, disc, "Remover", disciplinasCadastradas)){
				listaAluno.RemoverGrade(aluno, disc);
			}
		}
	}
	
	protected static boolean ProcurarDisciplina(Disciplina disc, String msg){
		String disciplinas = ListarDisciplinas();
		Integer codigo = InOut.InInt(disciplinas +
									"\nDigite o Codigo da Disciplina que você deseja " +msg+ ":");
		disc.setCodigo(codigo);
		if(grade.Find(disc,true) != -1){
			InOut.OutMessage("Diciplina: "+disc.getNome()+
							 "\nCodigo: "+disc.getCodigo());
			return true;
		}
		InOut.OutMessage("Disciplina não cadastrada");
		return false;
	}	
}
