package com.ui;

import com.dao.AlunoDAO;
import com.dao.GradeEscolar;
import com.recursos.InOut;
import com.vo.Disciplina;

public class MainQuestao1 {
	private static AlunoDAO listaAluno = new AlunoDAO();
	private static GradeEscolar grade = new GradeEscolar();
	private static Disciplina disc;
	public static boolean ProgramaJaRodou = false;
	
	public static void main(String[] args){
		Menu();
	}
	
	public static void Menu(){
		if(!ProgramaJaRodou){
			CriarDisciplinas();
			//listaAluno.LoadData(grade,"DadosAluno.json");
			ProgramaJaRodou = true;
		}
		int op;
		do{
			String opcoes = "Digite um dos Numeros abaixo:\n"+
							"1 - Aluno\n"+
							"2 - Gerir Grade Escolar\n"+
							"3 - Gerir Notas do Aluno\n"+
							"0 - Voltar";
			op = InOut.InInt(opcoes);
			switch(op){
				case 0:
					InOut.OutMessage("O programa será Finalizado", "Atenção");
					System.exit(0);
					break;
				case 1:
					TelaAluno.MenuAluno();
					break;
				case 2:
					TelaGrade.MenuGrade();
					break;
				case 3:
					TelaNota.MenuNota();
					break;
				default:
					InOut.OutMessage("Opção Invalida!", "Erro!");
					break;		
			}
		}while(op != 0);
	}
	
	private static void CriarDisciplinas(){
		/*disc = new Disciplina("Programação Orientada a Objetos");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Estrutura de Dados");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Análise de Sistemas de Informações Comerciais");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Projeto Integrador");
		grade.CadastrarDisciplina(disc);
		Disciplina disc = new Disciplina("Lógica de Programação");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Estatística");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Banco de Dados");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Sistemas Operacionais");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Sistemas de Informações");
		grade.CadastrarDisciplina(disc);*/
	}
	
	public static AlunoDAO getListaAluno(){
		return listaAluno;
	}
	public static GradeEscolar getGradeEscolar(){
		return grade;
	}
}
