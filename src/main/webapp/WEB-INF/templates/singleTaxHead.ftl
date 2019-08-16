<script type="text/javascript">
	function forwardWithParameters() {
		roundUp = $("#roundUpCheckBox").is(":checked");
		url = "/oder/payments/taxes/${paymentPeriod.year?string.computer}/${paymentPeriod.quarter}";
		if(roundUp != undefined && roundUp) {
			url += "?roundUp=true";
		}
		window.location.href = url;
	}
</script>
