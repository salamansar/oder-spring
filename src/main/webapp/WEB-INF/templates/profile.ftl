<#import "mainLayout.ftl" as layout>
<@layout.mainLayout 
	title="Профиль пользователя"
	selectedTab="profile">

	<div>Пользователь: <strong>${user.fullName}</strong></div>
	<div>Login: <strong>${user.login}</strong></div>
	              
</@layout.mainLayout>
