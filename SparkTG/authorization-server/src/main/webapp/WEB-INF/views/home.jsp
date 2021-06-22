<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.min.js"></script>
<link rel="stylesheet" href="css/font-awesome.min.css">
<meta charset="ISO-8859-1">
<title>Home Page</title>
<style type="text/css">
* {
  box-sizing: border-box;
}

/* Create two equal columns that floats next to each other */
.column {
  float: left;
  width: 45%;
  padding: 10px;
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}
	.buttonload {
	  background-color: #04AA6D; /* Green background */
	  border: none; /* Remove borders */
	  color: white; /* White text */
	  padding: 11px 24px; /* Some padding */
	  font-size: 16px; /* Set a font-size */
	  border-radius: 5px;
	  cursor: pointer;
	}
	
	/* Add a right margin to each icon */
	.fa {
	  margin-left: -12px;
	  margin-right: 8px;
	}
	
	input[type=text],input[type=date] {
	width: 45%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	box-sizing: border-box;
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$("#showInfo").click(function(){
			$("#response").hide();
			var showId = $("#showId").val();
			if(undefined==showId || null==showId || ""==showId){
				alert("Please enter the show Id.");
				return false;
			}
			var accessToken = getCookie("access_token");
			
		    $("#icon1").addClass('fa fa-refresh fa-spin');
			$.ajax({
			    type: 'GET',
			    url: "http://localhost:8080/sparktg-test-app/show-info?show-id="+showId,
			    beforeSend: function (xhr) {
			        xhr.setRequestHeader ("Authorization", "Bearer "+accessToken);
			    },
			    success: function(data){
			    	var formattedData = JSON.stringify(data.response, null, '\t');
			    	 $('#showsJson').text(formattedData);
		             $("#response").show();
		             $("#icon1").removeClass('fa fa-refresh fa-spin');
	            },
	            error: function(data){
	            	$("#icon1").removeClass('fa fa-refresh fa-spin');
		             alert(data);
	            }
			});
		});

		$("#filterShows").click(function(){
			var premierDate = $("#premierDate").val();
			if(undefined==premierDate || null==premierDate || ""==premierDate){
				alert("Please select premier date.");
				return false;
			}
			$("#response").hide();
			var accessToken = getCookie("access_token");
			$("#icon2").addClass('fa fa-refresh fa-spin');
			$.ajax({
			    type: 'GET',
			    url: "http://localhost:8080/sparktg-test-app/filter-shows?premier-date="+premierDate,
			    beforeSend: function (xhr) {
			        xhr.setRequestHeader ("Authorization", "Bearer "+accessToken);
			    },
			    success: function(data){
			    	var formattedData = JSON.stringify(data.response, null, '\t');
			    	 $('#showsJson').text(formattedData);
		             $("#response").show();
		             $("#icon2").removeClass('fa fa-refresh fa-spin');
	            },
	            error: function(data){
		             alert(data);
		             $("#icon2").removeClass('fa fa-refresh fa-spin');
	            }
			});
		});

		function disableBack() {
			window.history.forward();
		}
		window.onload = disableBack();
		window.onpageshow = function (evt) {
			if (evt.persisted){ 
				disableBack();
			}
		}
	});
	function getCookie(cname) {
		  var name = cname + "=";
		  var ca = document.cookie.split(';');
		  for(var i = 0; i < ca.length; i++) {
		    var c = ca[i];
		    while (c.charAt(0) == ' ') {
		      c = c.substring(1);
		    }
		    if (c.indexOf(name) == 0) {
		      return c.substring(name.length, c.length);
		    }
		  }
		  return "";
		}
</script>
</head>
<body>

<div class="row">
  <div class="column">
    <form action="">
    	<label for="show"><b>Enter Show ID</b></label> 
    		<input type="text"
				placeholder="Enter show Id" name="showId" id="showId" required>
			<button type="button" class="buttonload" id="showInfo">
				<i id="icon1" class=""></i>Get Show Info
			</button>
	</form>
  </div>
  <div class="column">
    <form action="">
		<label for="show"><b>Select Premier Date</b></label> 
    		<input type="date"
				placeholder="Enter show Id" name="premierDate" id="premierDate" required>
		<button type="button" class="buttonload" id="filterShows">
			<i id="icon2" class=""></i>Filter Shows
		</button>
	</form>
  </div>
  <div class="column" style="width: 10%;padding-top: 32px;">
  	<a href="logout" style="float: right;padding-right: 50px;">Logout</a>
  </div>
</div>

<div style="display: none;" id="response">
	<p>Response JSON:</p>
	<pre id="showsJson" style="background-color: darkgray;color: darkred;font-weight: bold;"></pre>
</div>
</body>
</html>