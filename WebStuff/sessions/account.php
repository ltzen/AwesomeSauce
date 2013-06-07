<?php
require_once '../memberClass.php';
$user = New User();
$user->confirm_User();
$tempUN=$_SESSION['un'];
$mysql = New Mysql();
$tempID=$mysql->get_UserID($tempUN);
$_SESSION['id']=$tempID;

?>

<!DOCTYPE html>
<html>
<head>
<title>World of Workout - Home</title>
<link rel="stylesheet" type="text/css" href="../CSS/template.css">

</head>
<body>
	<header>
		<img src="../images/wolf.png" width="100%">
	</header>
	<div id='cssmenu'>
		<ul>
		   <li><a href='/sessions/index.php'><span>Home</span></a></li>
		   <li><a href='/sessions/charstat.php'><span>Character Stats</span></a></li>
			 <li><a href='/sessions/exertrack.php'><span>Exercise Tracking</span></a></li>
		   <li><a href='/sessions/friend.php'><span>Friends</span></a></li>
		   <li class='active'><a href='/sessions/account.php'><span>Account Settings</span></a></li>
		</ul>
	</div>
	<div class = "contentbg">
		Account settings
		<br/>
				hello ICanHazTests
				<br/>
			stuff in here
			<br/>
			<a href="../index.php?status=loggedout">Log Out</a>
	</div>
</body>
</html>