package com.qst1.persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.qst1.dao.AlunoDAO;
import com.qst1.dao.GradeEscolar;
import com.qst1.vo.Aluno;
import com.recursos.InOut;

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
	
	public boolean LoadData(GradeEscolar grade, String file, AlunoDAO listaAluno){
		File arq = new File(file);
		Gson gson = new Gson();
		try {
			FileReader fr = new FileReader(arq);
			BufferedReader br = new BufferedReader(fr);
			String linha = "";			
			
			while((linha = br.readLine())!= null){
				Aluno aluno = gson.fromJson(linha, Aluno.class);
				aluno.setGerador(aluno.getMatricula());
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
