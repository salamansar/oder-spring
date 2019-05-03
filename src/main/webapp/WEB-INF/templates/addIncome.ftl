<#import "mainLayout.ftl" as layout>
<@layout.mainLayout 
	title="Добавление/редактирование дохода"
	selectedTab="incomes"
	headSection="addIncomeHead.ftl">

	<div class="inputForm">
		<form action="" method="POST">
			<table>
				<tr>
					<td>Номер платежа</td>
					<td><input type="text" name="documentNumber" <#if income?? && income.documentNumber??>value="${income.documentNumber?string.computer}"</#if> /></td>
				</tr>
				<tr>
					<td>Дата платежа</td>
					<td><input name="incomeDate" type="date" <#if income?? && income.incomeDate??>value="${income.incomeDate?date?iso_utc}"</#if> /></td>
				</tr>
				<tr>
					<td>Сумма платежа</td>
					<td><input name="amount" type="text" <#if income?? && income.amount??>value="${income.amount?string.computer}"</#if> /></td>
				</tr>
				<tr>
					<td>Описание</td>
					<td><input name="description" type="text" <#if income?? && income.description??>value="${income.description}"</#if> /></td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="button">
							<input type="submit" <#if mode?? && mode=="edit">value="Сохранить"<#else>value="Добавить"</#if>/>
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