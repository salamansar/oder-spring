<#import "mainLayout.ftl" as layout>
<@layout.mainLayout 
	title="Добавление дохода"
	selectedTab="incomes"
	headSection="addIncomeHead.ftl">

	<div class="inputForm">
		<form action="" method="POST">
			<table>
				<tr>
					<td>Номер платежа</td>
					<td><input type="text" name="documentNumber" /></td>
				</tr>
				<tr>
					<td>Дата платежа</td>
					<td><input name="incomeDate" type="date"/></td>
				</tr>
				<tr>
					<td>Сумма платежа</td>
					<td><input name="amount" type="text"/></td>
				</tr>
				<tr>
					<td>Описание</td>
					<td><input name="description" type="text"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="button">
							<input type="submit" value="Добавить"/>
						</div>
						<div class="button">
							<input type="button" value="Отмена" onclick="backToList()"/>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</@layout.mainLayout>