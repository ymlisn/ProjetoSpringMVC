<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="isExistingRecord" value="${funcionario.id gt 0}" />
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${isExistingRecord ? 'Edit' : 'New'} Funcionario</title>
	</head>
	<body>
		<div align="center">
			<h1>${isExistingRecord ? 'Edit' : 'New'} Funcionario</h1>
			<h4><a href="/list">Home</a></h4>
			<form:form action="save" method="post" modelAttribute="funcionario">
				<table>
					<form:hidden path="id"/>
					<tr>
						<td>Nome:</td>
						<td><form:input path="nome" /></td>
					</tr>
					<tr>
						<td>Email:</td>
						<td><form:input path="email" /></td>
					</tr>
					<tr>
						<td>Endereco:</td>
						<td><form:input path="endereco" /></td>
					</tr>
					<tr>
						<td>Telefone:</td>
						<td><form:input path="telefone" /></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input type="submit" value="Save"></td>
					</tr>
				</table>
			</form:form>
		</div>
	</body>
</html>