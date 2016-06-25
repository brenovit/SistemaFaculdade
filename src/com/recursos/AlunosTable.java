package com.recursos;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.vo.Aluno;

@SuppressWarnings("serial")
public class AlunosTable extends AbstractTableModel{
	//constantes para indetificar as colunas
	private final int MATRICULA = 0;
	private final int NOME = 1;
	private final int CPF = 2;
	
	private String[] columns = {"Matricula", "Nome", "CPF"};
	private List<Aluno> dados;
	
	public AlunosTable(){
		dados = new ArrayList<Aluno>();
	}
	
	@Override
	public String getColumnName(int num){
		return this.columns[num];
	}
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return dados.size();
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}
	
	@Override
	public Object getValueAt(int row, int column) {					//return a value in a row
		// TODO Auto-generated method stub
		switch(column){
			case MATRICULA: return dados.get(row).getMatricula();
			case NOME: 		return dados.get(row).getNome();
			case CPF: 		return dados.get(row).getCPF();
			default:		throw new IndexOutOfBoundsException("Invalid Column");
		}
	}
	
	@Override
	public void setValueAt(Object value, int row, int column){		//set 
			if(value == null)	return;
			switch (column) {
				case NOME: dados.get(row).setNome((String)value);break;
				case CPF: dados.get(row).setCPF((String)value);break;
			}
	}
	
	public Class<?> getColumnClass(int column) {
		switch(column){
			case MATRICULA: return int.class;
			case NOME: 		return String.class;
			case CPF: 		return String.class;
			default:		throw new IndexOutOfBoundsException("Invalid Column");
		}
	}
	
	/**
	 * adiciona uma linha na tabela
	 * @param o
	 */
	public void addRow(Aluno o){
		this.dados.add(o);
		this.fireTableDataChanged();
		//this.fireTableRowsInserted(indexOf(o), indexOf(o));
	}
	
	/**
	 * preenche toda a tabela com uma lista
	 * @param dataIn
	 */
	public void addRowAll(List<Aluno> dataIn){
		dados.addAll(dataIn);
		this.fireTableDataChanged();
	}

	/**
	 * remove uma linha da coluna
	 * @param row(linha)
	 */
	public void removeRow(int row){
		this.dados.remove(row);
		this.fireTableDataChanged();
		//this.fireTableRowsDeleted(row, row);
	}
	
	/**
	 * Limpa toda a tabela
	 */
	public void removeAll(){
		dados.clear();
		this.fireTableDataChanged();
	}
	
	/**
	 * retornar o aluno de uma linha especifica
	 * @param row(linha) 
	 * @return
	 */
	public Aluno getValue(int row){
		return this.dados.get(row);
	}
	
	public int indexOf(Aluno o){
		return dados.indexOf(o);
	}
	/**
	 * Substitui todos os valores da tabela
	 * @param lista
	 */
	public void setValue(List<Aluno> lista){
		dados = lista;
		this.fireTableDataChanged();
	}
}
