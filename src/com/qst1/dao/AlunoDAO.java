package com.qst1.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.qst1.vo.Aluno;
import com.qst1.vo.Disciplina;
import com.recursos.InOut;

public class AlunoDAO implements DAO {
	private List<Aluno> listaAluno;
	private List<Disciplina> listaDisciplina;
	private String msg = "";
	private Connection connection = null;		//manager the connection
	private Statement statement = null;			//manager the database, doing the query
	private ResultSet result = null;			//return data from database
	
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
	
	public void Connect() {
		// TODO Conectar no banco
		String URL = "jdbc:mysql://localhost:3306/faculdade";	//URL where the database is
		String user = "root";								//main user
		String pass = "root";								//main user password
		String driver = "com.mysql.jdbc.Driver";			//database controller
		try{
			Class.forName(driver);											//Registering the controller
			this.connection = DriverManager.getConnection(URL,user,pass);	//Establishing connection whith the database
			this.statement = this.connection.createStatement();				//Making the query use struct
		}catch(Exception e){
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - CONNECT", 0);
		}
	}

	public boolean isConnected() {
		// TODO Verificar conexão
		if(this.connection != null){
			return true;
		}
		return false;
	}
	
	@Override
	public void Create(Object o) {
		Aluno object = (Aluno) o;
		try{
			String query = "INSERT INTO alunos (nome,cpf) VALUES ('" + object.getNome() + "', '" + object.getCPF() + "');";
			this.statement.executeUpdate(query);
		}catch(Exception e){
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - INSERT", 1);
		}
		
		listaAluno.add(object);
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
	
	@Override
	public int Find(Object o) {
		return Find(o, false);
	}
	
	@Override
	public int Find(Object o, boolean alterar) {
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
			String query = "UPDATE alunos SET nome = '"+object.getNome()+"', cpf = '"+object.getCPF()+"' WHERE id = '"+object.getMatricula()+"';";
			this.statement.executeUpdate(query);
		}catch(Exception e){
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - UPDATE", 1);
		}
		
        int posicao = Find(object,false);
        if(posicao != -1){
        	listaAluno.get(posicao).setNome(object.getNome());
        	listaAluno.get(posicao).setCPF(object.getCPF());
        }
	}

	@Override
	public boolean Delete(Object o) {
		Aluno object = (Aluno) o;
		try{
			String query = "DELETE FROM alunos WHERE id = '"+object.getMatricula()+"';";
			this.statement.executeUpdate(query);
			return true;
		}catch(Exception e){
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - DELETE", 1);
		}
		
	    int posicao = Find(object,false);
	    if(posicao != -1){
	    	listaAluno.remove(posicao);	
	    	return true;
	    }
	    return false;
	}
	
	public String ShowDisciplinasMatriculadas(Aluno aluno) {
		msg = "";
		int pos = Find(aluno,false);
		if(pos != -1){
			listaDisciplina = aluno.getMaterias();
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
	
	public int FindMateria(Aluno aluno, Disciplina disc){
		int pos = Find(aluno,true);
		if(pos != -1){
			int posAux = 0;
			pos = -1;
			listaDisciplina = aluno.getMaterias();
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
	
	public void RemoverGrade(Aluno aluno, Disciplina disc){
		int pos = FindMateria(aluno, disc);
		if(pos != -1){
			aluno.removeDisciplina(pos);
		}
	}
	
	public boolean AddNota(Aluno aluno, Disciplina disc, Double nota){
		int pos = FindMateria(aluno, disc);
		if(pos != -1){
			listaDisciplina = aluno.getMaterias();
			listaDisciplina.get(pos).setNota(nota);
			return true;
		}
		return false;
	}
	
	public void LimparLista(){
		Aluno.zerarGerador();
		listaAluno.clear();
	}
	
	public void Disconnect() {
		// TODO Desconectar
		try{
			this.connection.close();
		}catch(Exception e){
			InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - DISCONNECT", 0);
		}
	}
}
