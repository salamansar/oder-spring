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
