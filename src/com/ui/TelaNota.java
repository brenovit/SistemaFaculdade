package com.ui;

import com.dao.AlunoDAO;
import com.recursos.InOut;
import com.vo.Aluno;
import com.vo.Disciplina;

public class TelaNota {
	private static SystemManager sm = new SystemManager();
	
	private static AlunoDAO listaAluno = MainQuestao1.getListaAluno();
	private static Disciplina disc;
	private static Aluno aluno;
	
	public static void MenuNota() {
		aluno = new Aluno();
		disc = new Disciplina();
		int op;
		do{
			String opcoes = "Digite um dos Numeros abaixo:\n"+
							"1 - Inserir/Alterar Nota do Aluno\n"+
							"2 - Checar Rendimento do Aluno\n"+
							"0 - Voltar";
			op = InOut.InInt(opcoes);
			switch(op){
				case 0:
					break;
				case 1:
					InserirNotaAluno(aluno, disc);
					break;
				case 2:					
					InOut.OutMessage(ChecarRendimento(aluno, disc));
					break;
				default:
					InOut.OutMessage("Opção Invalida!", "Erro!");
					break;		
			}
			//listaAluno.SaveData("DadosAluno.json");
		}while(op != 0);
		MainQuestao1.Menu();
	}
	
	private static void InserirNotaAluno(Aluno aluno, Disciplina disc){		
		String disciplinasCadastradas = "";
		if(TelaAluno.ProcurarAluno(aluno, "Inserir Nota")){
			disciplinasCadastradas = MostrarMateriasAluno(aluno);		
			if(ProcurarDisciplinaAluno(aluno, disc, "Inserir Nota",disciplinasCadastradas)){
				Double nota = InOut.InDouble("Digite a Nota obitida pelo Aluno:");
				if(!nota.equals(null)){
					listaAluno.AddNota(aluno, disc, nota);
				}
			}
		}
	}
	
	private static String ChecarRendimento(Aluno aluno, Disciplina disc){
		String msg = "";
		if(TelaAluno.ProcurarAluno(aluno, "Inserir Nota")){
			msg = MostrarMateriasAluno(aluno);
			return msg;
		}
		sm.AlunoNaoEncontrado();
		return null;
	}
	
	protected static String MostrarMateriasAluno(Aluno aluno){
		String msg = sm.DadosAlunoEncontrado(aluno) +
				"\nMaterias do Aluno:\n------------------------------------"+
				listaAluno.ShowDisciplinasMatriculadas(aluno);						 
		return msg;
	}
	
	protected static boolean ProcurarDisciplinaAluno(Aluno aluno, Disciplina disc, String msg, String disciplinasDoAluno){
		Integer codigo = InOut.InInt("Digite o Codigo da Disciplina que você deseja "+msg+":\n" +disciplinasDoAluno); 
		disc.setCodigo(codigo);
		if(listaAluno.FindMateria(aluno, disc) != -1){
			return true;
		}
		InOut.OutMessage("Disciplina não cadastrada");
		return false;
	}
}
