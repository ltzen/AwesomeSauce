<?php
session_start();
require_once 'memberClass.php';
$user = new User();
$tuser = new User();


if(isset($_GET['status']) && $_GET['status'] == 'loggedout') {
	$user->log_User_Out();
}
if (isset($_POST['tag']) && $_POST['tag'] != '') {
	$tag = $_POST['tag'];
 
    // response Array
    $json = array("tag" => $tag, "success" => 0, "error" => 0);
	$test=$tuser->getUserByEmailAndPassword($_POST['username'], $_POST['pwd']);
	$json["success"] = 1;
    $json["uid"] = $test["UserID"];
    $json["user"]["name"] = $test["Firstname"];
    $json["user"]["email"] = $test["Username"];
    $json["user"]["created_at"] = $test["DateCreated"];
    $json["user"]["updated_at"] = $test["LastLogin"];
	echo json_encode($json);
}

if($_POST){
	if(empty($_POST['username']) || empty($_POST['pwd'])){
		$response = "Please try again";
		$json["error"] = 1;
		echo json_encode($json);
	}
	if(!empty($_POST['username']) && !empty($_POST['pwd'])){
		$response = $user->validate_user($_POST['username'], $_POST['pwd']);
		
	}
}

?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Login Page</title>
</head>

<body>
<div>
	<form method="post" action="">
		<h2>Login</h2>
		<p>
			<label for="username">Username: </label>
			<input type="text" name="username" />
		</p>
		<p>
			<label for="pwd">Password: </label>
			<input type="password" name="pwd" />
		</p>
		<p>
			<input type="submit" id="submit" value="Login" name="submit" />
			<input type="button" value="Register" onClick="window.location.href='register.php'" />
		</p>
	</form>
	<?php if(isset($response)) echo $response; ?>
</div>
</body>
</html>