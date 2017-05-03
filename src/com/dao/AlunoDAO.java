package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.conexao.Banco;
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
	
	public List<Disciplina> getDisciplinas(Aluno aluno){
		int pos = Read(aluno);
		if(pos != -1){
			listaDisciplina = listaAluno.get(pos).getMaterias();
		}
		return listaDisciplina;
	}
	
	@Override	
	public boolean Create(Object o) {
		Aluno object = (Aluno) o;
		try {
			String sql = "INSERT INTO alunos (nome,cpf) VALUES (?,?)";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setString(1, object.getNome());
			pst.setString(2, object.getCPF());			
			pst.execute();
			Banco.Disconnect();
			
			String SQL = "SELECT alunos.id FROM faculdade.alunos WHERE cpf = ?";
			pst = Banco.Connect().prepareStatement(SQL);
			pst.setString(1, object.getCPF());
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				object.setMatricula(rs.getInt(1));
			
			listaAluno.add(object);
			Banco.Disconnect();		
			return true;
		} catch(SQLException e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERRO - CADASTRAR ALUNO", 1);
		} finally {
			Banco.Disconnect();			
		}
		return false;
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
        	/*if(alterar){
        		((Aluno)o).setCPF(listaAluno.get(posAux).getCPF());
        		((Aluno)o).setNome(listaAluno.get(posAux).getNome());
        		((Aluno)o).setMaterias(listaAluno.get(posAux).getMaterias());
        		((Aluno)o).setMatricula(listaAluno.get(posAux).getMatricula());
        	}*/
            posicao = posAux;
        }
        return posicao;
	}
	
	@Override
	public boolean Update(Object o) {
		Aluno object = (Aluno) o;
		int posicao = Read(object,false);
        if(posicao != -1) {
        	listaAluno.get(posicao).setNome(object.getNome());
        	listaAluno.get(posicao).setCPF(object.getCPF());
        	return true;
        }
        return false;
	}
	
	public boolean UpdateBanco(Object o) {
		Aluno object = (Aluno) o;
		try {			
			String sql = "UPDATE alunos SET nome = ?, cpf = ? WHERE id = ?";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setString(1, object.getNome());
			pst.setString(2, object.getCPF());
			pst.setString(3, object.getMatricula().toString());
			pst.executeUpdate();
			Banco.Disconnect();
			return true;
		} catch(SQLException e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERRO - UPDATE", 1);
		} finally {
			Banco.Disconnect();
		}	     
		return false;
	}
	
	@Override
	public boolean Delete(Object o){
		int pos = Read(o,false);
	    if(pos != -1) {
	    	listaAluno.remove(pos);
	    	return true;
	    }
	    return false;
	}
	
	public boolean DeleteBanco(Object o) {
		Aluno object = (Aluno) o;
		try {
			String sql = "DELETE FROM alunos WHERE id = ?";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setInt(1, object.getMatricula());
			pst.executeUpdate();
			
			int posicao = Read(object,false);
		    if(posicao != -1) {
		    	listaAluno.remove(posicao);
		    }
			Banco.Disconnect();
			return true;				//inconsistência
		} catch(SQLException e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERRO - DELETE", 1);
		} finally {
			Banco.Disconnect();
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
		listaAluno.clear();
	}
	
	public String ShowDisciplinasMatriculadas(Aluno aluno) {
		msg = "";
		int pos = Read(aluno);
		if(pos != -1){
			listaDisciplina = listaAluno.get(pos).getMaterias();
			for(Disciplina disciplina : listaDisciplina){
				msg += "\nCodigo: " + disciplina.getCodigo() +
						"\nNome: " + disciplina.getNome() +
						"\nNota: " + disciplina.getNota() +
						"\nAprovado: " + disciplina.getAprovado() +
						"\n------------------------------------";
			}
		}
		return msg;
	}
	
	public boolean CadastrarGrade(Aluno aluno, Disciplina disc){
		int pos = Read(aluno);
		if(pos != -1){
			listaAluno.get(pos).getMaterias().add(disc);					//inconsistência
			return true;
		}
		return false;
	}
	
	public boolean CadastrarGradeBanco(Aluno aluno, Disciplina disc){
		try {
			String sql = "INSERT INTO grade_aluno (id_aluno,id_disciplina) VALUES (?,?)";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setString(1, aluno.getMatricula().toString());
			pst.setString(2, disc.getCodigo().toString());
			pst.executeUpdate();
			
			Banco.Disconnect();
			return true;
		} catch (SQLException e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERRO - INSERIR GRADE", 1);
		} finally {
			Banco.Disconnect();
		}
		return false;
	}
	
	public int TemMateria(Aluno aluno, Disciplina disc){
		int pos = Read(aluno);
		int posAux = 0;
		
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
		return pos;
	}
	
	public boolean TemMateriaBanco(Aluno aluno, Disciplina disc){
		try {			
			String sql = "SELECT id_disciplina FROM grade_aluno WHERE id_aluno = ?";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setInt(1, aluno.getMatricula());
			ResultSet rs = (pst = Banco.Connect().prepareStatement(sql)).executeQuery(sql);
			
			Integer codigo = 0;			
			while(rs.next()){
				codigo = rs.getInt(1);
				if(codigo.equals(disc.getCodigo())){
					Banco.Disconnect();
					return true;				//tem materia	
				}
			}									
			
		} catch (SQLException e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERRO - LEITURA", 1);
		} finally {
			Banco.Disconnect();
		}	
		return false;	//não tem materia
	}
	
	public boolean RemoverGrade(Aluno aluno, Disciplina disc){
		int pos = TemMateria(aluno, disc);
		if(pos != -1){
			System.out.println(disc.toString());
			System.out.println("Lista: " + listaAluno.get(pos).getMaterias().get(pos).toString());
			return true;
		}
		return false;
	}
	
	public boolean RemoverGradeBanco(Aluno aluno, Disciplina disc){
		try {
			String sql = "DELETE FROM grade_aluno WHERE id_aluno = ? AND id_disciplina = ?";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setInt(1, aluno.getMatricula());
			pst.setInt(2, disc.getCodigo());
			pst.executeUpdate();			
		} catch (SQLException e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERRO - DELETE", 1);
		} finally {
			Banco.Disconnect();
		}		
		return false;
	}
	
	public boolean AddNota(Aluno aluno, Disciplina disc, Double nota){
		int posAlu = Read(aluno);
		int posDisc = TemMateria(aluno, disc);
		if(posDisc != -1){				
			listaAluno.get(posAlu).getMaterias().get(posDisc).setNota(nota);
			return true;
		}	
		return false;
	}
	
	public boolean AddNotaBanco(Aluno aluno, Disciplina disc, Double nota){
		try {
			String sql = "UPDATE grade_aluno SET nota = ? WHERE id_aluno = ? AND id_disciplina = ?";
			pst = Banco.Connect().prepareStatement(sql);
			pst.setDouble(1, nota);
			pst.setInt(2, aluno.getMatricula());
			pst.setInt(3, disc.getCodigo());
			pst.executeUpdate();
			Banco.Disconnect();
			return true;
		} catch (SQLException e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERRO - ADICIONAR NOTA", 1);
		} finally {
			Banco.Disconnect();
		}
		return false;
	}
	
	public void SyncSystem(){	//Syncronizar o sistema com o banco
		try {
			String sql = "SELECT id, nome, cpf FROM alunos";
			ResultSet rs = (pst = Banco.Connect().prepareStatement(sql)).executeQuery();
			while(rs.next()){
				Integer matricula = Integer.parseInt(rs.getString(1));
				String  nome = rs.getString(2);
				String cpf = rs.getString(3);
				
				Aluno aluno = new Aluno(matricula, nome, cpf);
				listaAluno.add(aluno);
				System.out.println(aluno.toString());
			}			
			Banco.Disconnect();
			
			sql = "SELECT id_aluno, id_disciplina, disciplinas.nome, nota FROM grade_aluno INNER JOIN disciplinas ON id_disciplina = disciplinas.id";
			rs = (pst = Banco.Connect().prepareStatement(sql)).executeQuery();
			while(rs.next()){
				Integer matricula = rs.getInt(1);
				Integer codigo = rs.getInt(2);
				String nome = rs.getString(3);
				Double nota = rs.getDouble(4);			
				
				Aluno aluno = new Aluno();
				aluno.setMatricula(matricula);
				
				Disciplina disc = new Disciplina(nome, codigo);
				
				CadastrarGrade(aluno, disc);
				AddNota(aluno, disc, nota);
			}
		} catch (SQLException e) {
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERRO - SINCRONIZAÇÃO", 1);
		} finally {
			Banco.Disconnect();
		}
	}
}
