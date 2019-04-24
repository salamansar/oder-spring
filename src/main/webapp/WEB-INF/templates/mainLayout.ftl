<#macro mainLayout title headSection="">
<!DOCTYPE html>
<html>
    <head>
        <title>${title}</title>
        <meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="/style.css">
		<script type="text/javascript" src="/lib/jquery-3.3.1.min.js"></script>
		<#include headSection ignore_missing=true/>
    </head>
    <body>
		<div class="header">
			Здесь будет хедер с меню
		</div>
		<div class="content">
			<#nested/>
		</div>
    </body>
</html>
</#macro>
