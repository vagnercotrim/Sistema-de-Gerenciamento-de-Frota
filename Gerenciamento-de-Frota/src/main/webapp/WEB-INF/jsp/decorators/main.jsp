<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><decorator:title default="Sistema de Gerenciamento de Frota"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/all.min.css"/>
	<decorator:head/>
</head>

<body>

<div id="header">
	<div class="shell">
		<div id="top">
			<h1><a href="${pageContext.request.contextPath}/">Sistema de Gerenciamento de Frota</a></h1>
			<div id="top-navigation">
				<c:if test="${usuarioSession.logado == true}">
					${usuarioSession.usuario.funcionario.nome}(${usuarioSession.usuario.perfil})
					<span>|</span>
					<a href='<c:url value='/logout' />'>Sair</a>
				</c:if>
				<c:if test="${usuarioSession.logado != true}">
					<a href='<c:url value='/login' />'>Entrar</a>
				</c:if>
			</div>
		</div>
		
		<div id="navigation">
			<ul>
			    <li><a href="${pageContext.request.contextPath}/veiculo"><span>Veículos</span></a></li>
			    <li><a href="${pageContext.request.contextPath}/combustivel"><span>Combustíveis</span></a></li>
			    <li><a href="${pageContext.request.contextPath}/funcionario"><span>Funcionários</span></a></li>
			    <li><a href="${pageContext.request.contextPath}/usuario"><span>Usuários</span></a></li>
			    <li><a href="${pageContext.request.contextPath}/motorista"><span>Motoristas</span></a></li>
			    <li><a href="${pageContext.request.contextPath}/hodometro"><span>Hodometro</span></a></li>
			</ul>
		</div>
	</div>
</div>

<div id="container">
	<div class="shell">
		
		<div class="small-nav">
			<a href="<c:url value="/" />">Início</a>
			<span>&gt;</span> Current Articles
		</div>
				
		<div class="small-nav">
			<tag:notice />
			
			<c:if test="${errors != null}">
				<div class="notice-error">
					<c:forEach items="${errors}" var="error">
						<p>${error.category}: ${error.message}</p>
					</c:forEach>
				</div>
			</c:if>
		</div>
		
		<decorator:getProperty property="page.breadcrumb"/>
		
		<div id="main">
			<div class="cl">&nbsp;</div>
			
			<div id="content">
				<decorator:body/>
			</div>
			
			<div class="cl">&nbsp;</div>			
		</div>
	</div>
</div>

<div id="footer">
	<div class="shell">
		<span class="left">&copy; 2010 - CompanyName</span>
		<span class="right">
			Design by <a href="http://chocotemplates.com" target="_blank" title="The Sweetest CSS Templates WorldWide">Chocotemplates.com</a>
		</span>
	</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/javascripts/all.min.js"></script>
<decorator:getProperty property="page.scripts"/>

</body>
</html>
