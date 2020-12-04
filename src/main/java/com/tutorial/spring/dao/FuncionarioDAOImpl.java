package com.tutorial.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import com.tutorial.spring.model.Funcionario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class FuncionarioDAOImpl implements FuncionarioDAO {

	private JdbcTemplate jdbcTemplate;

	public FuncionarioDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(Funcionario funcionario) {
		if (funcionario != null && funcionario.getId() > 0) {
			// update
			final String sql = "UPDATE funcionario SET nome=?, email=?, endereco=?, "
						+ "telefone=? WHERE id=?";
			jdbcTemplate.update(sql, funcionario.getNome(), funcionario.getEmail(),
					funcionario.getEndereco(), funcionario.getTelefone(), funcionario.getId());
		} else {
			// insert
			final String sql = "INSERT INTO funcionario (nome, email, endereco, telefone)"
						+ " VALUES (?, ?, ?, ?)";
			jdbcTemplate.update(sql, funcionario.getNome(), funcionario.getEmail(),
					funcionario.getEndereco(), funcionario.getTelefone());
		}

	}

	@Override
	public void delete(int id) {
		final String sql = "DELETE FROM funcionario WHERE id=?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Funcionario> findAll(HashMap<String, Object> paramMap) {
		if(paramMap == null) {
			paramMap = new HashMap<>();
		}
		String limitQuery = "";
		if(paramMap.containsKey("LIMIT_START") && paramMap.containsKey("LIMIT_END") ) {
			limitQuery = " LIMIT " + paramMap.get("LIMIT_START") + " , " + paramMap.get("LIMIT_END");
		}
		String sql = "SELECT * FROM funcionario WHERE 1=1";
		if(paramMap.get("ID") != null) {
			sql += " AND id = " + paramMap.get("ID");
		}
		sql += " " + limitQuery;
		final List<Funcionario> list = jdbcTemplate.query(sql, new RowMapper<Funcionario>() {
			@Override
			public Funcionario mapRow(ResultSet rs, int rowNum) throws SQLException {
				Funcionario funcionarioVO = new Funcionario();
				funcionarioVO.setId(rs.getInt("id"));
				funcionarioVO.setNome(rs.getString("nome"));
				funcionarioVO.setEmail(rs.getString("email"));
				funcionarioVO.setEndereco(rs.getString("endereco"));
				funcionarioVO.setTelefone(rs.getString("telefone"));
				return funcionarioVO;
			}
		});
		return list;
	}

	@Override
	public Funcionario findById(int id) {
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("ID", id);
		List<Funcionario> list = findAll(paramMap);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public int getCount() {
		String sql = "SELECT count(*) FROM funcionario";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}