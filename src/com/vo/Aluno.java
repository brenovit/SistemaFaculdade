package com.vo;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
	private String nome;	
	private Integer matricula;
	private String cpf;
	
	private List<Disciplina> grade;
	
	public Aluno(){
		grade = new ArrayList<Disciplina>();
		matricula = -1;
	}
	public Aluno(String nome, String cpf){
		grade = new ArrayList<Disciplina>();
		this.nome = nome;
		this.cpf = cpf;
	}
	
	public Aluno(int matricula, String nome, String cpf){
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

	public List<Disciplina> getMaterias(){		
		return this.grade;
	}
	
	public void setMaterias(List<Disciplina> materias){
		this.grade = materias;
	}

	public String toString(){
		return this.matricula + "." + this.nome + "." + this.cpf;
	}
}
