package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.recursos.InOut;
import com.vo.Aluno;
import com.vo.Disciplina;

public class GradeEscolar implements DAO{
	
	private List<Disciplina> listaDisc;
	private String msg = "";
	private PreparedStatement pst = null;
	
	public GradeEscolar(){
		listaDisc = new ArrayList<Disciplina>();	
	}
	
	public List<Disciplina> getLista(){
		return listaDisc;
	}

	@Override
	public void Create(Object o) {
		//TODO Create
		Disciplina disc = (Disciplina) o;
		try {
			String sql = "INSERT INTO disciplinas (nome) VALUES (?)";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setString(1, disc.getNome());
			pst.executeUpdate();
			Banco.Disconnect();
		} catch (Exception e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - INSERT", 1);
		}
		listaDisc.add(disc);
		
	}
	
	public void Read(){
		try {
			String sql = "SELECT id,nome FROM disciplinas";
			pst = Banco.Connect().prepareStatement(sql);
			ResultSet rs = pst.executeQuery(sql);
			while(rs.next()){
				Integer codigo = Integer.parseInt(rs.getString(1));
				String nome = rs.getString(2);				
				
				Disciplina disc = new Disciplina(nome,codigo);				
				Disciplina.setGerador(codigo);
				
				listaDisc.add(disc);
			}
		} catch (Exception e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - LEITURA", 1);
		}
	}
	
	@Override
	public String Show() {
		msg = "";
		for(Disciplina disciplina : listaDisc){
			msg += "\nCodigo: " + disciplina.getCodigo()+
					"\nNome: "+disciplina.getNome() + 
					"\n------------------------------------";
		}
		return msg;
	}
	
	@Override
	public int Find(Object o) {
		//TODO Find
		return Find(o, false);
	}
	
	@Override
	public int Find(Object o, boolean alterar) {
		//TODO Find
		Disciplina disc = (Disciplina) o;
		int posicao = -1;        
        int posAux = 0;
        
        while((posAux < listaDisc.size()) &&
                (!listaDisc.get(posAux).getCodigo().equals(disc.getCodigo()))){
            posAux++;
        }
        if((posAux < listaDisc.size()) && 
        		(listaDisc.get(posAux).getCodigo().equals(disc.getCodigo())) == true){
        	if(alterar){
	        	disc.setNome(listaDisc.get(posAux).getNome());
	        	disc.setCodigo(listaDisc.get(posAux).getCodigo());
        	}
            posicao = posAux;
        }
        return posicao;
	}

	@Override
	public void Update(Object o) {
		//TODO Update
		Disciplina disc = (Disciplina) o;
		try {
			String sql = "UPDATE disciplinas SET nome ?";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setString(1, disc.getNome());
			pst.executeUpdate();
			Banco.Disconnect();
		} catch (Exception e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - INSERT", 1);		
		}
		
		int posicao = Find(disc,false);
        if(posicao != -1){
        	listaDisc.get(posicao).setNome(disc.getNome());
        }
	}

	@Override
	public boolean Delete(Object o) {
		//TODO Auto-generated method stub
		Disciplina disc = (Disciplina) o;
		try {
			String sql = "DELETE FROM disciplina WHERE nome = ?";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setString(1, disc.getNome());
		} catch (Exception e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - DELETE", 1);
		}
		
		int pos = Find(disc,false);
		if(pos != -1){
			listaDisc.remove(pos);
			return true;
		} else
			InOut.OutMessage("Disciplina não Cadastrado");
		return false;
	}
		
	public void CadastrarGrade(Aluno aluno, Disciplina disc){
		try {
			String sql = "INSERT INTO grade_aluno (id_aluno,id_disciplina) VALUES (?,?)";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setString(1, aluno.getMatricula().toString());
			pst.setString(2, disc.getCodigo().toString());
			pst.executeUpdate();
			Banco.Disconnect();
		} catch (Exception e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - DELETE", 1);
		}
		
		int pos = Find(disc,true);
		if(pos != -1){
			aluno.addMateria(disc);
		}
	}
	
	public boolean isEmpty(){
		try {
			String sql = "SELECT * FROM disciplinas";
			pst = Banco.Connect().prepareStatement(sql);
			ResultSet rs = pst.executeQuery(sql);
			if(!rs.next()){
				return true;
			}
		} catch (Exception e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - VAZIO", 1);
		}
		return false;
	}
}
