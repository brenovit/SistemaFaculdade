package com.conexao;

import java.sql.Connection;
import java.sql.DriverManager;

import com.recursos.InOut;

public class Banco {
	
	private static Connection connection = null;		//manager the connection
		
	//verificar se tem que abrir e fechar o banco a cada uso ou abrir uma vez e fecha s� no final
		public static Connection Connect() {
			// TODO Conectar no banco
			String URL = "jdbc:mysql://localhost:3306/faculdade";	//URL where the database is
			String user = "root";								//main user
			String pass = "";								//main user password
			String driver = "com.mysql.jdbc.Driver";			//database controller
			try{
				Class.forName(driver);											//Registering the controller
				connection = DriverManager.getConnection(URL,user,pass);	//Establishing connection whith the database
				return connection;
			}catch(Exception e){
				InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - CONNECT", 0);
				return null;
			}
		}

		public static boolean isConnected() {
			// TODO Verificar conex�o
			if(connection != null){
				return true;
			}
			return false;
		}
		
		public static void Disconnect() {
			// TODO Desconectar
			try{
				connection.close();
			}catch(Exception e){
				InOut.OutMessage("Erro: \n"+e.getMessage(), "ERROR - DISCONNECT", 0);
			}
		}
}
