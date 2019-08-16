<!DOCTYPE html>
<html>
    <head>
        <title>Oder - авторизация</title>
        <meta charset="UTF-8">
		<link rel="icon" type="image/x-icon" href="/oder/public/img/favicon.ico" / >
		<link rel="stylesheet" type="text/css" href="/oder/public/style.css">
		<script type="text/javascript" src="/oder/public/lib/jquery-3.3.1.min.js"></script>
    </head>
    <body>
		<div class="page">
			<div class="authForm">
				<div class="authHeader">
					<img src="/oder/public/img/logo.png" class="logo centerBlock"/>
				</div>
				<div class="inputForm">
					<form action="" method="POST">
						<table>
							<tr>
								<td>Логин</td>
								<td><input type="text" name="username"/></td>
							</tr>
							<tr>
								<td>Пароль</td>
								<td><input name="password" type="password"/></td>
							</tr>
							<tr>
								<td colspan="2">
									<div class="button">
										<input type="submit" value="Войти"/>
									</div>
								</td>
						</table>
					</form>
				</div>
			</div>
			<div class="errorBlock">
				<#if error?? && error>
					Неверный логин или пароль!
				</#if>
			</div>
		</div>
    </body>
</html>
