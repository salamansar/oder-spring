<#macro mainLayout title headSection="">
<!DOCTYPE html>
<html>
    <head>
        <title>${title}</title>
        <meta charset="UTF-8">
		<link rel="icon" type="image/x-icon" href="/img/favicon.ico" / >
		<link rel="stylesheet" type="text/css" href="/style.css">
		<script type="text/javascript" src="/lib/jquery-3.3.1.min.js"></script>
		<#include headSection ignore_missing=true/>
    </head>
    <body>
		<div class="page">
			<div class="header">
				<div>
					<a href="/index.html"> 
						<img src="/img/logo.png" class="logo"/>
					</a>
				</div>
				<div class="menu">
					<a href="/payments/incomes/list">
						<div class="linkWrapper">
							Доходы
						</div>
					</a>
				</div>
				<div class="menu">
					<a href="/payments/taxes">
						<div class="linkWrapper">
							Налоги
						</div>
					</a>
				</div>
			</div>
			<div class="content"> 
				<#nested/>
			</div>
		</div>
    </body>
</html>
</#macro>
