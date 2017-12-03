<!DOCTYPE html>
<html>
    <head>
        <title>Список доходов</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <div>
            <form action="add" method="GET">
                <input type="submit" value="Добавить доход" />
            </form>
        </div>
        <table>
            <tr>
                <td>Номер платежа</td>
                <td>Дата платежа</td>
                <td>Сумма платежа</td>
                <td>Описание</td>
            </tr>      
            <#list incomes as income>
                <tr>
                    <td>${income.documentNumber}</td>
                    <td>${income.incomeDate?date}</td>
                    <td>${income.amount}</td>
                    <td>${income.description}</td>
                </tr>
            </#list>
        </table>        
    </body>
</html>
