<!DOCTYPE html>
<html>
    <head>
        <title>Детали налога</title>
        <meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="/style.css">
		<script type="text/javascript" src="/lib/jquery-3.3.1.min.js"></script>
		<script type="text/javascript">
			function forwardWithParameters() {
				roundUp = $("#roundUpCheckBox").is(":checked");
				url = "/payments/taxes/${selectedYear?string.computer}/${selectedQuarter}";
				if(roundUp != undefined && roundUp) {
					url += "?roundUp=true";
				}
				window.location.href = url;
			}
		</script>
    </head>
    <body>
		<div>
			<form>
				<span>
					Округлять вверх:
					<input id="roundUpCheckBox" 
							type="checkbox" 
							onchange="forwardWithParameters()"
							<#if roundUp?? && roundUp>checked="true"</#if>
						/>
				</span>
			</form>
		</div>
        <table>
            <tr>
                <td>Квартал</td>
				<td>${tax.paymentPeriodMessage}</td>
			</tr>
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
    </body>
</html>
