package com.tutorial.spring.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.tutorial.spring.model.Funcionario;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tutorial.spring.dao.FuncionarioDAO;
import com.tutorial.spring.utils.PaginationUtils;

@Controller
public class FuncionarioController {

	@Autowired
	FuncionarioDAO funcionarioDAO;
	
	@RequestMapping(value="/list")
	public ModelAndView listFuncionarios(HttpServletRequest request, ModelAndView model) throws IOException{
		String requestedPage = request.getParameter("page");
		int totalRecords = funcionarioDAO.getCount();
		HashMap<String, Object> paramMap = new HashMap<>();
		// add pagination
		PaginationUtils.addPaginationLimit(paramMap, totalRecords, String.valueOf(PaginationUtils.RECORDS_PER_PAGE), requestedPage);
		// list
		List<Funcionario> list = funcionarioDAO.findAll(paramMap);
		model.addObject("totalRecords", totalRecords);
		model.addObject("funcionariosLista", list);
		model.setViewName("list-funcionario");
		return model;
	}
	
	@RequestMapping(value = "/funcionario", method = RequestMethod.GET)
	public ModelAndView get(ModelAndView model, HttpServletRequest request) {
		Funcionario funcionario = new Funcionario();
		final String id = request.getParameter("id");
		if(StringUtils.isNumeric(id)) {
			funcionario = funcionarioDAO.findById(Integer.parseInt(id));
		}
		model.addObject("funcionario", funcionario);
		model.setViewName("funcionario-form");
		return model;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute Funcionario funcionario) {
		funcionarioDAO.save(funcionario);
		return new ModelAndView("redirect:/list");
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request) {
		final String id = request.getParameter("id");
		if(StringUtils.isNumeric(id)) {
			funcionarioDAO.delete(Integer.parseInt(id));
		}
		return new ModelAndView("redirect:/list");
	}
}