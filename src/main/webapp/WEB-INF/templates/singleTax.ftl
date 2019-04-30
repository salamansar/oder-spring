<#import "mainLayout.ftl" as layout>
<@layout.mainLayout 
	title="Детали налога"
	headSection="singleTaxHead.ftl"
	selectedTab="taxes">

	<div class="widgetsLine">
		<form>
			<div class="back-arrow-wrapper">
				<a href="/payments/taxes/${paymentPeriod.year?string.computer}">
					<div class="back-arrow"></div>
				</a>
			</div>
			<div class="messageWidget">
				Период: <span class="paymentPeriod">${paymentPeriod.formattedMessage}</span>
			</div>
			<div class="checkBox">
				<input id="roundUpCheckBox" 
						type="checkbox" 
						onchange="forwardWithParameters()"
						<#if roundUp?? && roundUp>checked="true"</#if>
					/>
				<label for="roundUpCheckBox">Округлять вверх</label>
			</div>
		</form>
	</div>
	<table>
		<tr>
			<td>Налоговая база</td>
			<td>
				<#if tax.incomesAmount??> 
					${tax.incomesAmount}
				<#else>
					&nbsp;
				</#if>
			</td>
		</tr>
		<tr>
			<td>Платеж в ПФР</td>
			<td>
				<#if tax.pensionTaxAmount??> 
					${tax.pensionTaxAmount}
				<#else>
					&nbsp;
				</#if>
			</td>
		</tr>
		<tr>
			<td>Платеж в ФФОМС</td>
			<td>
				<#if tax.healthInsuranceTaxAmount??> 
					${tax.healthInsuranceTaxAmount}
				<#else>
					&nbsp;
				</#if>
			</td>
		</tr>
		<tr>
			<td>1%</td>
			<td>
				<#if tax.onePercentTaxAmount??> 
					${tax.onePercentTaxAmount}
				<#else>
					&nbsp;
				</#if>
			</td>
		</tr>
		<tr>
			<td>Начислено налога УСН</td>
			<td>
				<#if tax.incomesTaxAmount??> 
					${tax.incomesTaxAmount}
				<#else>
					&nbsp;
				</#if>
			</td>
		</tr>
		<tr>
			<td>Налог УСН к оплате</td>
			<td>
				<#if tax.incomesDeductedTaxAmount??> 
					${tax.incomesDeductedTaxAmount}
				<#else>
					&nbsp;
				</#if>
			</td>
		</tr>
		<tr>
			<td>Начислено всего</td>
			<td>
				<#if tax.summarizedTaxAmount??> 
					${tax.summarizedTaxAmount}
				<#else>
					&nbsp;
				</#if>
			</td>
		</tr>
		<tr>
			<td>Всего к оплате</td>
			<td>
				<#if tax.summarizedDeductedTaxAmount??> 
					${tax.summarizedDeductedTaxAmount}
				<#else>
					&nbsp;
				</#if>
			</td>
		</tr>
	</table>        
</@layout.mainLayout>