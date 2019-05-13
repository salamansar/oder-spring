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
	<table id="incomesList" class="dataTable">
		<tr>
			<th>Номер платежа</th>
			<th>Дата платежа</th>
			<th>Сумма платежа</th>
			<th>Описание</th>
		</tr>      
		<#list incomes as income>
			<tr>
				<td>${income.documentNumber}
					<div class="row-tooltip">
						<div>
							<a href="/payments/incomes/edit/${income.id}">
								Изменить
							</a>
						</div>
						<div>
							<a	href="/payments/incomes/delete/${income.id}" 
								onclick="return confirm('Будет удалена запись №${income.documentNumber}. Подтвердить?')">
								Удалить
							</a>
						</div>
					</div>
				</td>
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
