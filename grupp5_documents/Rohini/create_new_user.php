<?php
	include 'connect_db.php';
?>

<?php
   
	$name = mysqli_real_escape_string($con, $_POST['name']);
	$mail = mysqli_real_escape_string($con, $_POST['email']);	
	$password = mysqli_real_escape_string($con, $_POST['password']);
	if (empty($mail)) { // Checks if email is empty.
		echo "EmailEmpty";
	} elseif (!filter_var($mail, FILTER_VALIDATE_EMAIL)) {
		echo "EmailInvalid";
	} elseif (empty($name)) { // Checks if name is empty.
		echo "NameEmpty";
	} elseif (empty($password)) { // Checks if password is empty.
		echo "PasswordEmpty";
	} else {
		echo $name . " + " . $mail . " + " . $password;
	}

	
   ?>

<?php
	
	mysqli_close($con);
?>