<#import "mainLayout.ftl" as layout>
<@layout.mainLayout 
	title="Профиль пользователя"
	selectedTab="profile">

	<h2>Пользователь: ${user.fullName}</h2>
	<h2>Login: ${user.login}</h2>
	              
</@layout.mainLayout>
