package com.vo;

public class Disciplina {
	private String nome;
	private boolean aprovado;
	private Double nota = 0.0;
	private Integer codigo;
	
	private static int geradorCodigo = 0;
	
	public Disciplina(){
		this.codigo = 0;
	}
	
	public Disciplina(String nome){
		geradorCodigo++;
		this.nome = nome;
		this.codigo = geradorCodigo;
	}
	
	public Disciplina(String nome, Integer codigo){
		this.nome = nome;
		this.codigo = codigo;
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
	
	public static void zerarGerador(){
		geradorCodigo = 0;
	}
	
	public static void setGerador(int valor){
		geradorCodigo = valor;
	}
	
	@Override
	public String toString(){
		return this.nome;
	}
}