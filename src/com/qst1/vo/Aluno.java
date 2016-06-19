package com.qst1.vo;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
	private String nome;	
	private Integer matricula;
	private String cpf;
	
	private static int geradorMatricula = 0;
	
	private List<Disciplina> grade;
	
	public Aluno(){
		grade = new ArrayList<Disciplina>();
		matricula = 0;
	}
	
	public Aluno(String nome, String cpf){
		grade = new ArrayList<Disciplina>();
		this.nome = nome;
		this.matricula = ++geradorMatricula;
		this.cpf = cpf;
	}
	
	public Aluno(String nome, int matricula, String cpf){
		grade = new ArrayList<Disciplina>();
		this.nome = nome;
		this.matricula = matricula;
		this.cpf = cpf;
	}
	
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public String getCPF() {
		return this.cpf;
	}

	public void setCPF(String cpf) {
		this.cpf = cpf;
	}
	
	public void addMateria(Disciplina materia){
		this.grade.add(materia);
	}
	
	public List<Disciplina> getMaterias(){		
		return this.grade;
	}
	
	public void setMaterias(List<Disciplina> materias){
		this.grade = materias;
	}
	
	public static void zerarGerador(){
		geradorMatricula = 0;
	}
	
	public static void setGerador(int matricula){
		geradorMatricula = matricula;
	}
	
	public void removeDisciplina(int posicao){
		grade.remove(posicao);
	}
	
	public static int getGerador(){
		return geradorMatricula;
	}
}
