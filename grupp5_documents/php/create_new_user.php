<?php
	include 'connect_db.php';
	include 'PasswordHash.php';
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
				// Resend registration confirmation email??
			} else { // Create and send email
				$verificationCode = md5(uniqid("yourveryownuniqueverificationcodefortheithsbloggapp")); // generate verification code, acts as 'key'.

				$confirmationLink = "http://jonasekstrom.se/ANNAT/iths_blog/confirm_registration.php?code=" . $verificationCode;

                $message = "
                <html>
                <head>
                	<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
                	<title>Confirm Registration (ITHS Blogg)</title>
                </head>
                	<body>
                		<p> 
                			Hi! 
                			<br><br>
                			Please click the button below to confirm your registration.
                			<br><br><br>
                			<a href='{$confirmationLink}' target='_blank' style='padding:1em; font-weight:bold; background-color:black; color:white; text-decoration:none'>CONFIRM REGISTRATION</a>
                			<br><br><br>
                			Kind regards,
                			<br>
                			ITHS Blogg
                			<br>
         				</p>
                	</body>
                </html>
                ";
               
                $subject = "Registration Confirmation | ITHS Blogg ";
                
                $recipient_email = $mail;

                $headers  = 'MIME-Version: 1.0' . "\r\n";
				$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";

				// Additional headers
				$headers .= 'To: ' . $recipient_email  . "\r\n";
				$headers .= 'From: ITHS Blogg <no-reply@jonasekstrom.se>' . "\r\n";

                // Create hashed password
                $hashedPassword = create_hash($password);
                
                if (mysqli_query($con,"INSERT INTO iths_users (name,readerOrAdmin,mail,password,confirm_code) VALUES ('$name',0,'$mail','$hashedPassword','$verificationCode')")) {
                	if (mail($recipient_email,$subject,$message,$headers)) {
                		echo "MailSent";
                	} else {
                		echo "MailUnsent";
                	}
                } else {
                	echo "NotCreated";
                }             
			}
		}
	}
	
?>

<?php
	mysqli_free_result($result);
	mysqli_close($con);
?>