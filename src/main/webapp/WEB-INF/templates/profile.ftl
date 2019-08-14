<#import "mainLayout.ftl" as layout>
<@layout.mainLayout 
	title="Профиль пользователя"
	selectedTab="profile">

	<div>Пользователь: <strong>${user.fullName}</strong></div>
	<div>Login: <strong>${user.login}</strong></div>

	<table id="incomesSummary" class="dataTable">
		<tr>
			<th>Год</th>
			<th>Сумма доходов</th>
		</tr>     
		<#if yearsIncomes?size==0>
			<tr>
				<td colspan=2>
					Доходы отсутствуют
				</td>
			</tr>
		<#else>
			<#list yearsIncomes as income>
				<tr>
					<td>${income.period.year}</td>
					<td>${income.incomeAmount}</td>
				</tr>
			</#list>
		</#if>
	</table>
	              
</@layout.mainLayout>
