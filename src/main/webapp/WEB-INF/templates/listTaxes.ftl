<!DOCTYPE html>
<html>
    <head>
        <title>Список налогов</title>
        <meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="/style.css">
    </head>
    <body>
		<div>
			<form>
                Год: 
				<select onchange="window.location.pathname='payments/taxes/' + this.value">
					<option style="display:none;"></option>
					<#list years as year>
						<option value="${year?string.computer}" <#if year == selectedYear>selected="true"</#if>>${year?string.computer}</option>
					</#list>
				</select>
			</form>
		</div>
        <table>
            <tr>
                <td>Квартал</td>
                <td>Налоговая база</td>
                <td>Начислено налога</td>
                <td>Обязательные платежи</td>
				<td>Налог к оплате</td>
				<td>1%</td>
                <td>Начислено всего</td>
                <td>Всего к оплате</td>
            </tr>
			<#list taxes as tax>
				<tr>
					<td>${tax.paymentPeriodMessage}</td>
					<td>
						<#if tax.incomesAmount??> 
							${tax.incomesAmount}
                        <#else>
                            &nbsp;
                        </#if>
					</td>
					<td>
						<#if tax.incomesTaxAmount??> 
							${tax.incomesTaxAmount}
                        <#else>
                            &nbsp;
                        </#if>
					</td>
					<td>
						<#if tax.fixedPaymentsTaxAmount??> 
							${tax.fixedPaymentsTaxAmount}
                        <#else>
                            &nbsp;
                        </#if>
					</td>
					<td>
						<#if tax.incomesDeductedTaxAmount??> 
							${tax.incomesDeductedTaxAmount}
                        <#else>
                            &nbsp;
                        </#if>
					</td>
					<td>
						<#if tax.onePercentTaxAmount??>
                            ${tax.onePercentTaxAmount}
                        <#else>
                            &nbsp;
                        </#if>
					</td>
					<td>
						<#if tax.summarizedTaxAmount??>
                            ${tax.summarizedTaxAmount}
                        <#else>
                            &nbsp;
                        </#if>
					</td>
					<td>
						<#if tax.summarizedDeductedTaxAmount??>
                            ${tax.summarizedDeductedTaxAmount}
                        <#else>
                            &nbsp;
                        </#if>
					</td>
				</tr>
			</#list>
        </table>        
    </body>
</html>
