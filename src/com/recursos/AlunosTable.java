package com.recursos;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.qst1.vo.Aluno;

public class AlunosTable extends AbstractTableModel{
	//constantes para indetificar as colunas
	private final int MATRICULA = 0;
	private final int NOME = 1;
	private final int CPF = 2;
	
	private String[] columns = {"Matricula", "Nome", "CPF"};
	private List<Aluno> dados;
	
	public AlunosTable(){
		dados = new ArrayList<Aluno>();
		this.addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent tme) {
				// TODO Auto-generated method stub
				int row = tme.getFirstRow();
			}			
		});		
	}
	
	public void addRow(Aluno o){
		this.dados.add(o);
		this.fireTableDataChanged();
	}
	
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
		if(column != MATRICULA)
			return true;
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
	
	public void removeRow(int row){
		this.dados.remove(row);
		this.fireTableDataChanged();
	}
	
	public Aluno get(int row){
		return this.dados.get(row);
	}
}
