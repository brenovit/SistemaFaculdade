package com.qst1.ui;

import com.qst1.vo.Aluno;
import com.recursos.InOut;

public class SystemManager {
	
	public String ProcurarDefinindoMatricula (Aluno paluno, String complemento) {
		Integer matricula = InOut.InInt("Insira a matricula do Aluno que deseja "+complemento+":");
		paluno.setMatricula(matricula);
		return DadosAlunoEncontrado (paluno);
	}
	
	public String DadosAlunoEncontrado (Aluno aluno){
		String msg = "Aluno Encontrado\n------------------------------------" +
				 "\nMatricula: " + aluno.getMatricula() +
			     "\nNome: "+aluno.getNome()+
			     "\nCPF: "+aluno.getCPF()+
			     "\n------------------------------------\n";
		return msg;		
	}
	
	public void AlunoNaoEncontrado(){
		InOut.OutMessage("N�o foi encontrado nenhum aluno com esta matricula.\n"
				+ "Por favor verifique se digitou corretamente os dados e tente novamente.", 
				"Aluno N�o Encontrado");
	}
	
	
	
}
