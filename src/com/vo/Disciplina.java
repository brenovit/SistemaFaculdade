package com.vo;

public class Disciplina {
	private String nome;
	private boolean aprovado;
	private Double nota = 0.0;
	private Integer codigo;
	
	public Disciplina(){
	}
	
	public Disciplina(String nome, Integer codigo){
		this.nome = nome;
		this.codigo = codigo;
		this.nota = 0.0;
	}
	
	public Disciplina(String nome, Integer codigo, Double nota){
		this.nome = nome;
		this.codigo = codigo;
		this.nota = nota;
	}
	
	public Disciplina(String nome){
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Double getNota(){
		return this.nota;
	}
	
	public void setNota(Double valor){
		this.nota = valor;
		if(this.nota < 6){
			this.aprovado = false;
		}else{
			this.aprovado = true;
		}
	}
	
	public boolean getAprovado(){
		return this.aprovado;
	}
	
	public Integer getCodigo(){
		return codigo;
	}
	
	public void setCodigo(Integer codigo){
		this.codigo = codigo;
	}
	
	@Override
	public String toString(){
		return this.codigo + "." + this.nome;
	}
}