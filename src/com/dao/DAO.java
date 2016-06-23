package com.dao;

public interface DAO {
	public void Create (Object o);
	public String Show ();
	public int Find (Object o);
	public int Find (Object o, boolean alterar);
	public void Update (Object o);
	public boolean Delete (Object o);
}
