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
		$result = mysqli_query($con,"SELECT 1 FROM iths_users WHERE mail = '$mail' AND status = 1");
		$count = mysqli_num_rows($result); 
		if($count > 0) {  // User already confirmed.
			echo "RegistrationConfirmed"; // Echo "0"
		} else {
			$result = mysqli_query($con, "SELECT 1 FROM iths_users WHERE mail = '$mail' AND status = 0");
			$count = mysqli_num_rows($result); 
			if($count > 0) { // User still pending.
				echo "RegistrationPending";
				// Resend registration confirmation email?? create a resend verification script??
			} else {
				echo "Can be registered";
			}
		}
	}

	
   ?>

<?php
	
	mysqli_close($con);
?>