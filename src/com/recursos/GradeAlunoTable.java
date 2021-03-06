package com.recursos;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.vo.Aluno;
import com.vo.Disciplina;

@SuppressWarnings("serial")
public class GradeAlunoTable extends AbstractTableModel{
	//constantes para indetificar as colunas
	private final int CODIGO = 0;
	private final int NOME = 1;
	private final int NOTA = 2;
	private final int APROVADO = 3;
	
	private String[] columns = {"C�digo", "Nome", "Nota","Aprovado"};
	private List<Disciplina> dados;
	
	public GradeAlunoTable(){
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
	public boolean isCellEditable(int row, int column){
		return false;
	}
	
	@Override
	public Object getValueAt(int row, int column) {					//return a value in a row
		// TODO Auto-generated method stub
		switch(column){
			case CODIGO: 	return dados.get(row).getCodigo();
			case NOME: 		return dados.get(row).getNome();
			case NOTA: 		return dados.get(row).getNota();
			case APROVADO: 	return dados.get(row).getAprovado();
			default:		throw new IndexOutOfBoundsException("Invalid Column");
		}
	}
	/**
	 * S� a coluna 2 - NOTA ser� editada.
	 * Insira um valor Double.
	 */
	@Override
	public void setValueAt(Object value, int row, int column){		//set 
			if(value == null)	return;
			dados.get(2).setNota((Double)value);
	}
	
	public Class<?> getColumnClass(int column) {
		switch(column){
			case CODIGO: 	return int.class;
			case NOME: 		return String.class;
			case APROVADO: 	return Boolean.class;
			case NOTA:		return Double.class;
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
