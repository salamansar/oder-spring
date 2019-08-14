<#macro mainLayout title headSection="" selectedTab="">
<!DOCTYPE html>
<html>
    <head>
        <title>${title}</title>
        <meta charset="UTF-8">
		<link rel="icon" type="image/x-icon" href="/public/img/favicon.ico" / >
		<link rel="stylesheet" type="text/css" href="/public/style.css">
		<script type="text/javascript" src="/public/lib/jquery-3.3.1.min.js"></script>
		<#include headSection ignore_missing=true/>
    </head>
    <body>
		<div class="page">
			<div class="header">
				<div>
					<a href="/profile"> 
						<img src="/public/img/logo.png" class="logo"/>
					</a>
				</div>
				<div class='menu'>
					<a href="/payments/incomes/list">
						<div class='linkWrapper <#if selectedTab="incomes">selected</#if>'>
							Доходы
						</div>
					</a>
				</div>
				<div class='menu'>
					<a href="/payments/taxes">
						<div class='linkWrapper <#if selectedTab="taxes">selected</#if>'>
							Налоги
						</div>
					</a>
				</div>
				<div class='rightMenu'>
					<div class="menu">
						<a href="/profile">
							<div class='linkWrapper <#if selectedTab="profile">selected</#if>'>
								<img src="/public/img/profile.png" class="profileIcon"/>
								<span>
									${user.login}
								</span>
							</div>
						</a>
					</div>
					<div class="menu">
						<a href="/auth/logout">Выход</a>
					</div>
				</div>
			</div>
			<div class="content"> 
				<#nested/>
			</div>
		</div>
    </body>
</html>
</#macro>
