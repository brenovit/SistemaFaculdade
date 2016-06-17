package com.qst1.ui;

import com.qst1.dao.AlunoDAO;
import com.qst1.vo.Aluno;
import com.recursos.InOut;

public class TelaAluno {
	private static SystemManager sm = new SystemManager();
	private static AlunoDAO listaAluno = MainQuestao1.getListaAluno();
	private static Aluno aluno;
	
	
	protected static void MenuAluno(){
		aluno = new Aluno();
		int op;
		do{
		String opcoes = "Digite um dos Numeros abaixo:\n"+
						"1 - Cadastrar Aluno\n"+
						"2 - Listar Alunos\n"+
						"3 - Alterar Dados do Aluno\n"+
						"4 - Procurar por Aluno\n"+
						"5 - Deletar Aluno\n"+						
						"6 - Limpar Lista\n"+
						"0 - Voltar";
		op = InOut.InInt(opcoes);
		switch(op){
			case 0:
				break;
			case 1:
				CadastrarAluno();
				break;
			case 2:
				ListarAluno();
				break;
			case 3:
				AlterarAluno(aluno, "Alterar");
				break;
			case 4:
				ProcurarAluno(aluno,"Procurar");
				break;
			case 5:
				DeletarAluno(aluno, "Deletar");
				break;
			case 6:
				LimparLista();
				break;
			default:
				InOut.OutMessage("Opção Invalida!", "Erro!");
				break;		
		}
		//listaAluno.SaveData("DadosAluno.json");
		}while(op != 0);
		MainQuestao1.Menu();
	}
	
	private static void CadastrarAluno() {
		String nome = InOut.InString("Insira o Nome do Aluno:");
		String cpf = InOut.InString("Digite o CPF do Aluno:");
		//Aluno aluno = new Aluno("Breno","07049603546");
		Aluno aluno = new Aluno(nome,cpf);
		listaAluno.Create(aluno);
	}
	
	private static void ListarAluno() {
		String msg = "Alunos Cadastrados no Sistema\n------------------------------------"+listaAluno.Show();
		InOut.OutMessage(msg);
	}
	
	private static void AlterarAluno(Aluno aluno, String msg){
		sm.ProcurarDefinindoMatricula (aluno, msg);
		if(listaAluno.Find(aluno,true) != -1){					 
			String nome = InOut.InString(sm.DadosAlunoEncontrado(aluno) + "Digite o novo Nome:");
			String cpf = InOut.InString(sm.DadosAlunoEncontrado(aluno) + "Digite o novo CPF do Aluno:");
			aluno.setNome(nome);
			aluno.setCPF(cpf);
			listaAluno.Update(aluno);
		}else{
			sm.AlunoNaoEncontrado();
		}
	}
	
	protected static boolean ProcurarAluno(Aluno aluno, String msg){
		sm.ProcurarDefinindoMatricula (aluno, msg);
		if(listaAluno.Find(aluno,true) != -1){
			InOut.OutMessage(sm.DadosAlunoEncontrado(aluno));
			return true;
		}
		sm.AlunoNaoEncontrado();
		return false;
	}
	
	private static void DeletarAluno(Aluno aluno, String msg){
		sm.ProcurarDefinindoMatricula (aluno, msg);
		if(listaAluno.Delete(aluno)){						 
			InOut.OutMessage(sm.DadosAlunoEncontrado(aluno) + "Clique Confirmar para Remover o aluno do registro.");			
		}else{
			sm.AlunoNaoEncontrado();
		}		
	}
	
	private static void LimparLista() {
		if(InOut.ConfirmDialog("Deseja Realmente Deletar toda a lista de Alunos:", "Limpar a Lista de Alunos") == 0){
			listaAluno.LimparLista();
		}
	}
}
