<!DOCTYPE html>
<html>
    <head>
        <title>Список налогов</title>
        <meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="/style.css">
		<script type="text/javascript" src="/lib/jquery-3.3.1.min.js"></script>
		<script type="text/javascript">
			function forwardWithParameters() {
				year = $("#yearsSelector").val();
				roundUp = $("#roundUpCheckBox").is(":checked");
				url = "/payments/taxes";
				if(year != undefined) {
					url += "/" + year;
				}
				if(roundUp != undefined && roundUp) {
					url += "?roundUp=true";
				}
				window.location.href = url;
			}
		</script>
    </head>
    <body>
		<div class="widgetsLine">
			<form>
				<span>
					<div class="comboBox">
						<label for="yearsSelector">Год</label> 
						<span>
							<select id="yearsSelector" onchange="forwardWithParameters()">
								<option style="display:none;"></option>
								<#list years as year>
									<option value="${year?string.computer}" <#if year == selectedYear>selected="true"</#if>>${year?string.computer}</option>
								</#list>
							</select>
						</span>
					</div>
				</span>
				<span>
					<div class="checkBox">
						<input id="roundUpCheckBox" 
								type="checkbox" 
								onchange="forwardWithParameters()"
								<#if roundUp?? && roundUp>checked="true"</#if>
							/>
						<label for="roundUpCheckBox">Округлять вверх</label>
					</div>
				</span>
			</form>
		</div>
        <table>
            <tr>
				<th></th>
                <th>Квартал</th>
                <th>Налоговая база</th>
                <th>Начислено налога</th>
                <th>Обязательные платежи</th>
				<th>Налог к оплате</th>
				<th>1%</th>
                <th>Начислено всего</th>
                <th>Всего к оплате</th>
            </tr>
			<#list taxes as tax>
				<tr>
					<td>
						<#if tax.paymentPeriod??> 
							<a href="/payments/taxes/${tax.paymentPeriod.year?string.computer}/${tax.paymentPeriod.quarter.numberValue}">
								Подробнее
							</a>
                        <#else>
                            &nbsp;
                        </#if>
					</td>
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
