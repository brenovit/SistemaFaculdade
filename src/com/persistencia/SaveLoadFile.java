package com.persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.dao.AlunoDAO;
import com.dao.DisciplinaDAO;
import com.google.gson.Gson;
import com.recursos.InOut;
import com.vo.Aluno;

public class SaveLoadFile {	
	public void SaveData(String file, List<Aluno> listAluno){
		File arq = new File(file);
		try {
			FileWriter fw = new FileWriter(arq, false);
			PrintWriter pw = new PrintWriter(fw);
			Gson gson = new Gson();
			
			for(Aluno aluno : listAluno){				
				String aux = gson.toJson(aluno);
				pw.println(aux);
			}
			pw.flush();
			pw.close();
		} catch (IOException e) {
			InOut.OutMessage(e.getMessage(), "ERRO",0);
		}
	}
	
	public boolean LoadData(DisciplinaDAO grade, String file, AlunoDAO listaAluno){
		File arq = new File(file);
		Gson gson = new Gson();
		try {
			FileReader fr = new FileReader(arq);
			BufferedReader br = new BufferedReader(fr);
			String linha = "";			
			
			while((linha = br.readLine())!= null){
				Aluno aluno = gson.fromJson(linha, Aluno.class);
				listaAluno.Create(aluno);
			}
			fr.close();
			br.close();		
			
		} catch (IOException e) {
			InOut.OutMessage(e.getMessage(), "ERRO", 0);
			return false;
		}
		return true;
	}
}
