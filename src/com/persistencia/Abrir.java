package com.persistencia;

import java.io.File;
import java.io.IOException;

public class Abrir {
	public static void AbrirArquivo(String endArq) throws IOException{
		try{
			java.awt.Desktop.getDesktop().open(new File(endArq));
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}
	}
}
