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
				$verificationCode = md5(uniqid("yourveryownuniqueverificationcodefortheithsbloggapp")); // generate verification code, acts as 'key'.

				$confirmationLink = "http://jonasekstrom.se/ANNAT/iths_blog/confirm_registration.php?code=" . $verificationCode;

				$htmlStr = "";
                $htmlStr .= "Hi " . $mail . ",<br /><br />";
                 
                $htmlStr .= "Please click the button below to confirm your registration and be able to write comments.<br /><br /><br />";
                $htmlStr .= "<a href='{$confirmationLink}' target='_blank' style='padding:1em; font-weight:bold; background-color:blue; color:#fff;'>CONFIRM REGISTRATION</a><br /><br /><br />";
                 
                $htmlStr .= "Kind regards,<br />";
                $htmlStr .= "<a href='http://jonasekstrom.se/' target='_blank'>ITHS Blogg</a><br />";
                
                $body = $htmlStr; 
         
                $name_sender = "ITHS Blogg";
                $email_sender = "no-reply@jonasekstrom.se";
                $subject = "Registration Confirmation | ITHS Blogg ";
                $recipient_email = $mail;

                $headers  = "MIME-Version: 1.0rn";
                $headers .= "Content-type: text/html; charset=iso-8859-1rn";
                $headers .= "From: {$name_sender} <{$email_sender}> n";
                if (mail($recipient_email,$subject,$body,$headers)) {
                	echo "MailSent";
                } else {
                	echo "MailUnsent";
                }
			}
		}
	}

	
   ?>

<?php
	
	mysqli_close($con);
?>