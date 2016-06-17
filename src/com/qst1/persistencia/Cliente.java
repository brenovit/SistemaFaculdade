package com.qst1.persistencia;

import java.util.ArrayList;
import java.util.List;
import com.recursos.*;

public class Cliente {
	//value objects
    private String nome;
    private String email;
    private int idade;
    
    private List<Carro> carros;
    
    public Cliente (){
    	carros = new ArrayList<Carro>();
    }
    public Cliente (String cNome){
    	carros = new ArrayList<Carro>();
    	setNome(cNome);
    	setEmail("");
    	setIdade(18);
    }
    public Cliente (String cNome, String cEmail, int cIdade){
    	carros = new ArrayList<Carro>();
    	setNome(cNome);
    	setEmail(cEmail);
    	setIdade(cIdade);
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }
    
    public List<Carro> getCarros(){		
		return this.carros;
	}
	
	public void setCarros(List<Carro> carros){
		this.carros = carros;
	}
    
    public void setIdade(int pIdade) {
        if(pIdade>=18){
            idade = pIdade;
        } else {
            InOut.OutMessage("Idade invalida", "Erro");
        }
    }
    
    public String toString(){
    	String msg = "\nNome: " + this.nome + " - E-Mail: " + this.email + " - Idade: " + this.idade + "\n----------";
    	for(Carro carro: this.carros){
    		msg += carro.toString();
    	}
    	msg += "\n==============================\n";
		return msg;    	
    }
}
