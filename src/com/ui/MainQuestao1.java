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
					InOut.OutMessage("O programa ser� Finalizado", "Aten��o");
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
					InOut.OutMessage("Op��o Invalida!", "Erro!");
					break;		
			}
		}while(op != 0);
	}
	
	private static void CriarDisciplinas(){
		/*disc = new Disciplina("Programa��o Orientada a Objetos");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Estrutura de Dados");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("An�lise de Sistemas de Informa��es Comerciais");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Projeto Integrador");
		grade.CadastrarDisciplina(disc);
		Disciplina disc = new Disciplina("L�gica de Programa��o");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Estat�stica");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Banco de Dados");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Sistemas Operacionais");
		grade.CadastrarDisciplina(disc);
		disc = new Disciplina("Sistemas de Informa��es");
		grade.CadastrarDisciplina(disc);*/
	}
	
	public static AlunoDAO getListaAluno(){
		return listaAluno;
	}
	public static GradeEscolar getGradeEscolar(){
		return grade;
	}
}
