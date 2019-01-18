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
				<select value="2018">
					<option value="2018">2018</option>
					<option value="2017">2017</option>
					<option value="2016">2016</option>
				</select>
			</form>
		</div>
        <table>
            <tr>
                <td>Квартал</td>
                <td>Налоговая база</td>
                <td>Начислено налога</td>
                <td>Обязательные платежи</td>
				<td>1%</td>
                <td>Начислено всего</td>
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
				</tr>
			</#list>
        </table>        
    </body>
</html>
