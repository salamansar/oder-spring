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
				<span>
					<a href="/index.html"> 
						<img src="/img/logo.png" class="logo"/>
					</a>
				</span>
				<span class="menu">
					<a href="payments/incomes/list">Доходы</a>
				</span>
				<span class="menu">
					<a href="payments/taxes">Налоги</a>
				</span>
			</div>
			<div class="content"> 
				<#nested/>
			</div>
		</div>
    </body>
</html>
</#macro>
