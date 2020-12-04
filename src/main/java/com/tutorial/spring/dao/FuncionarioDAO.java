package com.tutorial.spring.dao;

import java.util.HashMap;
import java.util.List;

import com.tutorial.spring.model.Funcionario;

public interface FuncionarioDAO {

	public void save(Funcionario funcionario);
	
	public void delete(int id);
	
	public Funcionario findById(int id);
	
	public List<Funcionario> findAll(HashMap<String, Object> paramMap);

	public int getCount();
}