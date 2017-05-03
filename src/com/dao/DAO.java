package com.dao;

public interface DAO {
	public boolean Create (Object o);
	public int Read (Object o);
	public int Read (Object o, boolean alterar);
	public boolean Update (Object o);
	public boolean Delete (Object o);	
	public String Show ();
	public void SyncSystem();
}
