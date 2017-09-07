/**
 * 
 */

$(function(){
	login.init();
});

var login = {
	init: function(){
		this.initEvent();
	},
	
	initEvent: function(){
		$("#loginBtn").on("click", function(){
			console.log("param: " + $('#loginForm').serialize());
			$.ajax({
				type: 'POST',
				url: $('#loginForm').attr("action"),
				data: $('#loginForm').serialize(),
				dataType: "json",
				success: function (data) {
					console.log(data);
					//var response = JSON.parse(data);
					if (data.success == true) {
						console.info("Authentication Success!");
						location.href = "../";
					}else {
						console.error("Unable to login");
					}
				},
				error: function (data) {
					console.error("Login failure");
				}
			});
		});
	}
}