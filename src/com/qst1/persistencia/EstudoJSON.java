package com.qst1.persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.qst1.vo.Aluno;
import com.qst1.vo.Disciplina;

public class EstudoJSON {
	private ArrayList<Carro> lista = new ArrayList<Carro>();
	static File arq = new File("jsonFile.json");
	
	public static void main(String[] args) throws IOException {
		SaveJson();
		LoadJson();
       //adicaoDeUmObjeto();        
    }
	
	private static void SaveJson() throws IOException {
	    //Criação do objeto carro e atribuição dos valores
		
		ArrayList<Carro> lista1 = new ArrayList<Carro>();
		ArrayList<Carro> lista2 = new ArrayList<Carro>();
		ArrayList<Carro> lista3 = new ArrayList<Carro>();
		
	    Carro carro0 = new Carro(10,"Celta","AAA1234");
	    Carro carro1 = new Carro(11,"Palio","BBB1234");
	    Carro carro2 = new Carro(12,"Fox","CCC1234");
	    Carro carro3 = new Carro(13,"Punto","DDD1234");
	    Carro carro4 = new Carro(14,"HB20","EEE1234");
	    Carro carro5 = new Carro(14,"Ferrari","FFF1234");
	    
	    lista1.add(carro0);
	    lista1.add(carro1);
	    lista1.add(carro2);
	    
	    lista2.add(carro2);
	    lista2.add(carro3);
	    lista2.add(carro4);
	    
	    lista3.add(carro5);
	    
	    Gson gson = new Gson();
	    
	    Cliente cli1 = new Cliente("Breno", "Breno@Gmail.com", 18);
	    cli1.setCarros(lista1);
	    Cliente cli2 = new Cliente("João", "Joao@Hotmail.com", 20);
	    cli2.setCarros(lista2);
	    Cliente cli3 = new Cliente("José", "Jose@Yahoo.com", 30);
	    cli3.setCarros(lista3);
	    
	    //Criação do objeto carroJson
	    /*JSONObject carroJson = new JSONObject();
	    //Inserção dos valores do carro no objeto JSON
	    carroJson.put("id", carro.getId());
	    carroJson.put("Modelo", carro.getModelo());
	    carroJson.put("Placa", carro.getPlaca());*/
	 
	    //Impressão do objeto JSON
	    
		FileWriter fw = new FileWriter(arq, false);
		PrintWriter pw = new PrintWriter(fw);
		
		String aux = gson.toJson(cli1);
		pw.println(aux);
		
		aux = gson.toJson(cli2);
		pw.println(aux);
		
		aux = gson.toJson(cli3);
		pw.println(aux);
		
		pw.flush();
		pw.close();
		
	}
	
	private static void LoadJson(){
		try {
			//Criação do objeto carro e atribuição dos valores
		    
		    Gson gson = new Gson();		    
		    
			FileReader fr = new FileReader(arq);
			BufferedReader br = new BufferedReader(fr);			

			String linha = "";
			
			ArrayList <String> result = new ArrayList<String>();
			
			while((linha = br.readLine())!= null){
				System.out.println(linha);
				if(linha != null && !linha.isEmpty()){
					result.add(linha);
				}
			}
			for(String s: result){
				Cliente cliente = gson.fromJson(s, Cliente.class);
				System.out.println(cliente.toString());
			}
			
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
