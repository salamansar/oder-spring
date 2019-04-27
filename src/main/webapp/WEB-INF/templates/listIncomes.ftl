<#import "mainLayout.ftl" as layout>
<@layout.mainLayout 
	title="Список доходов"
	selectedTab="incomes">

	<div class="widgetsLine">
		<form action="add" method="GET">
			<div class="button">
				<input type="submit" value="Добавить доход" />
			</div>
		</form>
	</div>
	<table>
		<tr>
			<th>Номер платежа</th>
			<th>Дата платежа</th>
			<th>Сумма платежа</th>
			<th>Описание</th>
		</tr>      
		<#list incomes as income>
			<tr>
				<td>${income.documentNumber}</td>
				<td>${income.incomeDate?date}</td>
				<td>${income.amount}</td>
				<td>
					<#if income.description??>
						${income.description}
					<#else>
						&nbsp;
					</#if>
				</td>
			</tr>
		</#list>
	</table>              
</@layout.mainLayout>
