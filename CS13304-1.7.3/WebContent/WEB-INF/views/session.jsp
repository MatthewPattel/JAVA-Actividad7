<log:setLogger logger="me.jmll.controller.Session" />
<log:entry/>
<b:base title="${initParam.tema}">
	<log:debug message="Hit por SessionID: ${sessionScope.sessionId }"/>
	<div class="container">
		<h1>Bienvenido, ${sessionScope.user}</h1>
		<h2>Tu Session</h2>
		<ul>
			<li>Session Id: <strong>${sessionScope.sessionId }</strong></li>
			<li>Creation Time: <strong>${sessionScope.creationTime}</strong></li>
			<li>Last Accessed Time: <strong>${sessionScope.lastAccessedTime}</strong></li>
			<li>Max Inactive Interval: <strong>${sessionScope.maxInactiveInterval}</strong></li>
			<li>Visits: <strong>${sessionScope.visits }</strong></li>
		</ul>
		<form action="${pageContext.request.contextPath }/Sessions">
			<button class="btn btn-default btn-sm" type="submit">
	  			<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Add visit
			</button>
		</form>
		<br>
		<form action="${pageContext.request.contextPath }/Sessions">
			<input type="text" hidden="true" name="reset" value=""/>
			<button class="btn btn-default btn-sm" type="submit">
	  			<span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Reset visits
			</button>
		</form>
		<h2>Tus Cookies</h2>
		<ul>
		<log:info message="Cantidad de cookies ${fn:length(cookie)}"/>
			<c:forEach var="ck" items="${cookie}">
				<li>${ck.key}: <strong>${ck.value.value}</strong></li>
			</c:forEach>
		</ul>
		<br>
		<form action="${pageContext.request.contextPath}/Logout">
			<button class="btn  btn-sm btn-primary" type="submit">
			<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>Log out</button>
		</form>

	</div>
</b:base>
<log:exit/>