package com.dao;

public interface DAO {
	public void Create (Object o);
	public int Read (Object o);
	public int Read (Object o, boolean alterar);
	public void Update (Object o);
	public boolean Delete (Object o);	
	public String Show ();
	public void SyncSystem();
}
