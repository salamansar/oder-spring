<!DOCTYPE html>
<html>
    <head>
        <title>Добавление дохода</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <form action="" method="POST">
            <table>
                <tr>
                    <td>Номер платежа</td>
                    <td><input name="documentNumber" /></td>
                </tr>
                <tr>
                    <td>Дата платежа</td>
                    <td><input name="incomeDate" type="date"/></td>
                </tr>
                <tr>
                    <td>Сумма платежа</td>
                    <td><input name="amount" /></td>
                </tr>
                <tr>
                    <td>Описание</td>
                    <td><input name="description" /></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Добавить"/></td>
                </tr>
            </table>
        </form>
    </body>
</html>
