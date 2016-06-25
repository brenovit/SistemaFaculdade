package com.recursos;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.vo.Aluno;
import com.vo.Disciplina;

@SuppressWarnings("serial")
public class DisciplinasTable extends AbstractTableModel{
	//constantes para indetificar as colunas
	private final int CODIGO = 0;
	private final int NOME = 1;
	
	private String[] columns = {"Código", "Nome"};
	private List<Disciplina> dados;
	
	public DisciplinasTable(){
		dados = new ArrayList<Disciplina>();
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
	public Object getValueAt(int row, int column) {					//return a value in a row
		// TODO Auto-generated method stub
		switch(column){
			case CODIGO: 			return dados.get(row).getCodigo();
			case NOME: 				return dados.get(row).getNome();
			default:		throw new IndexOutOfBoundsException("Invalid Column");
		}
	}
	
	@Override
	public void setValueAt(Object value, int row, int column){		//set 
		if(value == null)	return;
		switch (column) {
			case NOME: dados.get(row).setNome((String)value);break;
			default:		throw new IndexOutOfBoundsException("Invalid Column");
		}
	}
	
	public Class<?> getColumnClass(int column) {
		switch(column){
			case NOME: 		return String.class;
			case CODIGO: 	return Integer.class;
			default:		throw new IndexOutOfBoundsException("Invalid Column");
		}
	}
	
	/**
	 * adiciona uma linha na tabela
	 * @param o
	 */
	public void addRow(Disciplina o){
		this.dados.add(o);
		this.fireTableDataChanged();
		//this.fireTableRowsInserted(indexOf(o), indexOf(o));
	}
	
	/**
	 * preenche toda a tabela com uma lista
	 * @param dataIn
	 */
	public void addRowAll(List<Disciplina> dataIn){
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
	public Disciplina getValue(int row){
		return this.dados.get(row);
	}
	
	public int indexOf(Aluno o){
		return dados.indexOf(o);
	}
	/**
	 * Substitui todos os valores da tabela
	 * @param lista
	 */
	public void setValue(List<Disciplina> lista){
		dados = lista;
		this.fireTableDataChanged();
	}
}
