package com.qst1.persistencia;

public class Carro {
    private Integer id;
    private String modelo;
    private String placa;
 
    public Carro(){
    	
    }
    
    public Carro(Integer id, String modelo, String placa){
    	this.id = id;
    	this.modelo = modelo;
    	this.placa = placa;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
 
        //Aqui fizemos o Override do método toString() para visualizar a impressão com o System.out.println()
    @Override
    public String toString() {
        return "\nId: " + id + " - Modelo: " + modelo + " - Placa: " + placa + "\n--------------------";
    }
 
}