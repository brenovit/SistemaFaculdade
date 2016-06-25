package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.recursos.InOut;
import com.vo.Aluno;
import com.vo.Disciplina;

public class AlunoDAO implements DAO {
	private List<Aluno> listaAluno;
	private List<Disciplina> listaDisciplina;
	
	private String msg = "";	
	
	private PreparedStatement pst = null;
	
	public AlunoDAO(){
		super();
		listaAluno = new ArrayList<Aluno>();
	}
	
	public int size(){
		return listaAluno.size();
	}
	
	public List<Aluno> getLista(){
		return listaAluno;
	}
	
	@Override
	public void Create(Object o) {
		Aluno object = (Aluno) o;
		try{
			String sql = "INSERT INTO alunos (nome,cpf) VALUES (?,?)";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setString(1, object.getNome());
			pst.setString(2, object.getCPF());
			pst.executeUpdate();
			Banco.Disconnect();
		}catch(Exception e){
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - INSERT", 1);
		}
		
		listaAluno.add(object);
	}
	
	@Override
	public int Read(Object o) {
		return Read(o, false);
	}
	
	@Override
	public int Read(Object o, boolean alterar) {
        int posicao = -1;        
        int posAux = 0;
        
        while((posAux < listaAluno.size()) &&
                (!listaAluno.get(posAux).getMatricula().equals(((Aluno)o).getMatricula()))){
            posAux++;
        }
        if((posAux < listaAluno.size()) && 
        		(listaAluno.get(posAux).getMatricula().equals(((Aluno)o).getMatricula())) == true){
        	if(alterar){
        		((Aluno)o).setCPF(listaAluno.get(posAux).getCPF());
        		((Aluno)o).setNome(listaAluno.get(posAux).getNome());
        		((Aluno)o).setMaterias(listaAluno.get(posAux).getMaterias());
        		((Aluno)o).setMatricula(listaAluno.get(posAux).getMatricula());
        	}
            posicao = posAux;
        }
        return posicao;
	}
	
	@Override
	public void Update(Object o) {
		Aluno object = (Aluno) o;
		try{			
			String sql = "UPDATE alunos SET nome = ?, cpf = ? WHERE id = ?";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setString(1, object.getNome());
			pst.setString(2, object.getCPF());
			pst.setString(3, object.getMatricula().toString());
			pst.executeUpdate();
			Banco.Disconnect();
		}catch(Exception e){
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - UPDATE", 1);
		}
		
        int posicao = Read(object,false);
        if(posicao != -1){
        	listaAluno.get(posicao).setNome(object.getNome());
        	listaAluno.get(posicao).setCPF(object.getCPF());
        }
	}

	@Override
	public boolean Delete(Object o) {
		Aluno object = (Aluno) o;
		try{
			String sql = "DELETE FROM alunos WHERE id = ?";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setInt(1, object.getMatricula());
			pst.executeUpdate();
			Banco.Disconnect();
			return true;
		}catch(Exception e){
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - DELETE", 1);
		}
		
	    int posicao = Read(object,false);
	    if(posicao != -1){
	    	listaAluno.remove(posicao);	
	    	return true;
	    }
	    return false;
	}

	@Override
	public String Show() {
		msg = "";
		for(Aluno aluno : listaAluno){
			msg += "\nMatricula: " + aluno.getMatricula()+
					"\nNome: " + aluno.getNome()+
					"\nCPF: " + aluno.getCPF()+
					"\n------------------------------------";
		}
		return msg;
	}
	
	public void LimparLista(){
		Aluno.zerarGerador();
		listaAluno.clear();
	}
	
	public String ShowDisciplinasMatriculadas(Aluno aluno) {
		msg = "";
		listaDisciplina = aluno.getMaterias();
		for(Disciplina disciplina : listaDisciplina){
			msg += "\nCodigo: " + disciplina.getCodigo() +
					"\nNome: " + disciplina.getNome() +
					"\nNota: " + disciplina.getNota() +
					"\nAprovado: " + disciplina.getAprovado() +
					"\n------------------------------------";
		}
		return msg;
	}
	
	public boolean CadastrarGrade(Aluno aluno, Disciplina disc){
		try {
			String sql = "INSERT INTO grade_aluno (id_aluno,id_disciplina) VALUES (?,?)";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setString(1, aluno.getMatricula().toString());
			pst.setString(2, disc.getCodigo().toString());
			pst.executeUpdate();
			Banco.Disconnect();
		} catch (Exception e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - INSERIR GRADE", 1);
		}
		
		try {
			int pos = Read(aluno);
			if(pos != -1){
				listaAluno.get(pos).addMateria(disc);
				return true;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int FindMateria(Aluno aluno, Disciplina disc){		
		try {
			String sql = "SELECT id_disciplina FROM grade_aluno";
			pst = Banco.Connect().prepareStatement(sql);
			ResultSet rs = (pst = Banco.Connect().prepareStatement(sql)).executeQuery(sql);
			Integer codigo = 0;
			while(rs.next()){
				codigo = rs.getInt(1);
				if(codigo.equals(disc.getCodigo())){
					break;				
				}
			}
			if(!codigo.equals(disc.getCodigo())){
				return -1;
			}
			
		} catch (Exception e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - LEITURA", 1);
		}
		
		int pos = Read(aluno);
		int posAux = 0;		
		try {			
			if(pos != -1){							
				listaDisciplina = listaAluno.get(pos).getMaterias();
				pos = -1;
				while((posAux < listaDisciplina.size()) && 
						(!listaDisciplina.get(posAux).getCodigo().equals(disc.getCodigo()))){
					posAux++;
				}
				if((posAux < listaDisciplina.size()) && 
						(listaDisciplina.get(posAux).getCodigo().equals(disc.getCodigo())) == true){
					pos = posAux;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return pos;		
	}
	
	public boolean RemoverGrade(Aluno aluno, Disciplina disc){
		try {
			String sql = "DELETE FROM grade_aluno WHERE id_aluno = ? AND ? id_disciplinas = ?";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setInt(1, aluno.getMatricula());
			pst.setInt(2, disc.getCodigo());
			pst.executeUpdate();
			Banco.Disconnect();
		} catch (Exception e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - DELETE", 1);
		}
		
		try {
			int pos = FindMateria(aluno, disc);
			if(pos != -1){
				listaAluno.get(pos).removeDisciplina(pos);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean AddNota(Aluno aluno, Disciplina disc, Double nota){
		try {
			String sql = "UPDATE grade_aluno SET nota = ? WHERE id_aluno = ? AND id_disciplina = ?";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setDouble(1, nota);
			pst.setInt(2, aluno.getMatricula());
			pst.setInt(3, disc.getCodigo());
			pst.executeUpdate();
			Banco.Disconnect();
		} catch (Exception e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - DELETE", 1);
		}
		
		try {
			int posAlu = Read(aluno);
			int posDisc = FindMateria(aluno, disc);
			if(posDisc != -1){				
				listaAluno.get(posAlu).getMaterias().get(posDisc).setNota(nota);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void FeedSystem(){	//Alimentar o Sistema
		try {
			String sql = "SELECT id, nome, cpf FROM alunos";
			/*pst = Banco.Connect().prepareStatement(sql);
			ResultSet rs = pst.executeQuery(sql);*/
			ResultSet rs = (pst = Banco.Connect().prepareStatement(sql)).executeQuery(sql);
			while(rs.next()){
				Integer matricula = Integer.parseInt(rs.getString(1));
				String  nome = rs.getString(2);
				String cpf = rs.getString(3);
				
				Aluno aluno = new Aluno(nome, matricula, cpf);
				Aluno.setGerador(matricula);
				listaAluno.add(aluno);				
			}			
			Banco.Disconnect();
			
			sql = "SELECT id_aluno, id_disciplina, nota FROM grade_aluno";
			pst = Banco.Connect().prepareStatement(sql);
			rs = (pst = Banco.Connect().prepareStatement(sql)).executeQuery(sql);
			while(rs.next()){
				Integer matricula = Integer.parseInt(rs.getString(1));
				Integer codigo = Integer.parseInt(rs.getString(2));
				Double nota = Double.parseDouble(rs.getString(3));			
				
				Aluno aluno = new Aluno();
				aluno.setMatricula(matricula);
				
				Disciplina disc = new Disciplina();
				disc.setCodigo(codigo);
				
				CadastrarGrade(aluno, disc);
				AddNota(aluno, disc, nota);				
			}
			
			Banco.Disconnect();
		} catch (Exception e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - ALIMENTAÇÃO", 1);
		}
	}
}
