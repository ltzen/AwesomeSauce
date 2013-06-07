<?php
// hide server info for security
require_once 'databaseConnect.php';

class Mysql{
	private $link;
	
	function __construct(){
		$this->link= new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DEFAULT_DB) or 
					die('Could not connect to MySQL: ' . mysql_error());
	}
//validating username and password for login
	function verify($un, $pwd){
		$query = 	"SELECT *
					FROM users
					WHERE Username = ? AND Password = ?
					LIMIT 1";
			
		if($stmt = $this->link->prepare($query)){
			$stmt->bind_param('ss', $un, $pwd);
			$stmt->execute();
			
			if($stmt->fetch()){
				$_SESSION['un']=$un;
				$_SESSION['pwd']=$pwd;
				$stmt->close();
				return true;
			}
		}		
	}
	
	
//function for setting user ID	
	function get_UserID($sessionUN){
		$queryNew ="SELECT UserID
					FROM users
					WHERE Username = ?
					LIMIT 1";

		$stmt = $this->link->prepare($queryNew);	
		$stmt->bind_param('s', $sessionUN);
		$stmt->execute();
		$stmt->bind_result($id);

		$stmt->fetch();
		$stmt->close();
		
		return $id;
	}
		
//functions for retrieving member information
	function get_Username($sessionID){
		$queryNew ="SELECT Username
					FROM users
					WHERE ID = ?
					LIMIT 1";
	
		$stmt = $this->link->prepare($queryNew);
		$stmt->bind_param('s', $sessionID);
		$stmt->execute();
		$stmt->bind_result($username);
	
		$stmt->fetch();
		$stmt->close();
	
		return $username;
	}

	function get_FirstName($sessionID){
		$queryNew ="SELECT FirstName
					FROM users
					WHERE ID = ?
					LIMIT 1";

		$stmt = $this->link->prepare($queryNew);	
		$stmt->bind_param('s', $sessionID);
		$stmt->execute();
		$stmt->bind_result($nameFirst);

		$stmt->fetch();
		$stmt->close();
		
		return $nameFirst;
	}

	function get_LastName($sessionID){
		$queryNew ="SELECT LastName
					FROM users
					WHERE ID = ?
					LIMIT 1";

		$stmt = $this->link->prepare($queryNew);	
		$stmt->bind_param('s', $sessionID);
		$stmt->execute();
		$stmt->bind_result($nameLast);

		$stmt->fetch();
		$stmt->close();
		
		return $nameLast;
	}

	function get_LastLogin($sessionID){
		$queryNew ="SELECT LastLogin
					FROM users
					WHERE ID = ?
					LIMIT 1";

		$stmt = $this->link->prepare($queryNew);	
		$stmt->bind_param('s', $sessionID);
		$stmt->execute();
		$stmt->bind_result($lastLogin);

		$stmt->fetch();
		$stmt->close();
		
		return $lastLogin;
	}

	function get_Gender($sessionID){
		$queryNew ="SELECT Gender
					FROM users
					WHERE ID = ?
					LIMIT 1";

		$stmt = $this->link->prepare($queryNew);	
		$stmt->bind_param('s', $sessionID);
		$stmt->execute();
		$stmt->bind_result($gender);

		$stmt->fetch();
		$stmt->close();
		
		return $gender;
	}

	function get_Weight($sessionID){
		$queryNew ="SELECT Weight
					FROM users
					WHERE ID = ?
					LIMIT 1";

		$stmt = $this->link->prepare($queryNew);	
		$stmt->bind_param('d', $sessionID);
		$stmt->execute();
		$stmt->bind_result($weight);

		$stmt->fetch();
		$stmt->close();
		
		return $weight;
	}

	function get_Height($sessionID){
		$queryNew ="SELECT height
					FROM users
					WHERE ID = ?
					LIMIT 1";

		$stmt = $this->link->prepare($queryNew);	
		$stmt->bind_param('d', $sessionID);
		$stmt->execute();
		$stmt->bind_result($height);

		$stmt->fetch();
		$stmt->close();
		
		return $height;
	}

	function get_State($sessionID){
		$queryNew ="SELECT State
					FROM users
					WHERE ID = ?
					LIMIT 1";
	
		$stmt = $this->link->prepare($queryNew);
		$stmt->bind_param('s', $sessionID);
		$stmt->execute();
		$stmt->bind_result($state);
	
		$stmt->fetch();
		$stmt->close();
	
		return $state;
	}

	function get_Country($sessionID){
		$queryNew ="SELECT Country
					FROM users
					WHERE ID = ?
					LIMIT 1";

		$stmt = $this->link->prepare($queryNew);	
		$stmt->bind_param('s', $sessionID);
		$stmt->execute();
		$stmt->bind_result($country);

		$stmt->fetch();
		$stmt->close();
		
		return $country;
	}

	function get_DateCreated($sessionID){
		$queryNew ="SELECT DateCreated
					FROM users
					WHERE ID = ?
					LIMIT 1";
	
		$stmt = $this->link->prepare($queryNew);
		$stmt->bind_param('s', $sessionID);
		$stmt->execute();
		$stmt->bind_result($dateCreated);
	
		$stmt->fetch();
		$stmt->close();
	
		return $dateCreated;
	}
}

class User{
	function validate_user($un, $pwd) {
		$mysql = New Mysql();
		$check = $mysql->verify($un, $pwd);
		
		if($check){
			$_SESSION['status'] = 'authorized';
			header("location: sessions/index.php");
		} else return "Please enter correct username and password";
	}
	function log_User_Out() {
		if(isset($_SESSION['status'])) {
			unset($_SESSION['status']);
			
			if(isset($_COOKIE[session_name()])) 
				setcookie(session_name(), '', time() - 1000);
				session_destroy();
		}
	}
	public function getUserByEmailAndPassword($email, $password) {
        $result = mysql_query("SELECT * FROM users WHERE Username = '$email'") or die(mysql_error());
        // check for result
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysql_fetch_array($result);
            $check = $mysql->verify($un, $pwd);
            // check for password equality
            if ($check) {
                // user authentication details are correct
                return $result;
            }
        } else {
            // user not found
            return false;
        }
    }
	
	function confirm_User() {
		session_start();
		if($_SESSION['status'] !='authorized') header("location: ../login.php");
	}
	
}
?>