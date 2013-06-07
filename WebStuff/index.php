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
		//$json["error"] = 1;
		//echo json_encode($json);
	}
	if(!empty($_POST['username']) && !empty($_POST['pwd'])){
		$response = $user->validate_user($_POST['username'], $_POST['pwd']);
		
	}
}

$dayseed = date("d");
$monthseed = date("m");
$yearseed = date("y");
$mdyseed = date("Ymd");
?>

<!DOCTYPE html>
<html>
<head>
<title>World of Workout - Home</title>
<link rel="stylesheet" type="text/css" href="CSS/template.css">

</head>
<body>
	<header>
		<img src="images/wolf.png" width="100%">
	</header>
	<div class = "bar">
		
		<div style="float:right"><a href="register.php"> <img style="padding:6px 5px;" src="images/signup.png"> </a></div>
		
	</div>
	<div class = "content">
		<div class = "leftColumn">
			<div class = "login">
				<div class = "loginWrap">
					<h1>Login</h1>
					<section class="main">				
						<form method="post" action="">
							<p>
								<label for="username"><unpw>Username: <unpw/></label> <br/>
								<input type="text" name="username" id='form1' placeholder="Username" />
							</p>
							<p>
								<label for="pwd"><unpw>Password: </unpw></label> <br/>
								<input type="password" name="pwd" id="form1" placeholder="Password"/>
							</p>
								<input type="image" src="images/loginbutton.png" id="submit" value="Login" name="submit" />
						</form>
					</section>
					<div class="output"><?php if(isset($response)) echo $response; ?></div>
				</div>
			</div>
			<div class="demo">
				bonus of the day!
				<?php
					$numofday = (((24 * $dayseed) + (83 * $monthseed) + (51 * $yearseed) * (394 * $ymdseed) + ($dayseed * $monthseed + 42) + $ymdseed + ($ymdseed * $yearseed)) % 12) + 1;
					echo $numofday;
				?>
			</div>
		</div>
		<div class="rightColumn">
			stuff in here
		</div>
	</div>
</body>
</html>