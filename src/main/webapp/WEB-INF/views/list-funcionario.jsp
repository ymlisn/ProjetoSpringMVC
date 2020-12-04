<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="pagination" uri="/WEB-INF/paginationTag.tld"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Lista de Funcionarios</title>
	</head>
	<body>
		<div align="center">
	        <h1>Lista de funcionarios</h1>
	        <h4><a href="/funcionario">Novo Funcionario</a></h4>
	        <span class="pagination-total-records"></span>
	        <table border="1" cellspacing="5" cellpadding="5">
	        	<tr>
		        	<th>ID</th>
		        	<th>Nome</th>
		        	<th>Email</th>
		        	<th>Endereço</th>
		        	<th>Telefone</th>
		        	<th>Ação</th>
	        	</tr>
				<c:forEach var="funcionario" items="${funcionariosLista}">
		        	<tr>
		        		<td>${funcionario.id}</td>
						<td>${funcionario.nome}</td>
						<td>${funcionario.email}</td>
						<td>${funcionario.endereco}</td>
						<td>${funcionario.telefone}</td>
						<td>
							<a href="funcionario?id=${funcionario.id}">Edit</a>&nbsp;
							<a href="delete?id=${funcionario.id}">Delete</a>
						</td>
		        	</tr>
				</c:forEach>	        	
			</table>
			<pagination:nav totalRecords="${totalRecords}" action="/list" />
			<script type="text/javascript" src="/js/jquery-3.3.1.js"></script>
			<script type="text/javascript">
				$(document).ready(function() {
					var customPaginationDom = $(".custom-pagination-total-records");
					var paginationDom = $(".pagination-total-records");
					paginationDom.replaceWith(customPaginationDom);
				});
			</script>
    	</div>
	</body>
</html>