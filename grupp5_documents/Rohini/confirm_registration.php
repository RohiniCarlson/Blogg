<?php
	include 'connect_db.php';
?>

<?php
   
	$code = mysqli_real_escape_string($con, $_GET['code']);
	echo "<div>Your confirmation code is " . $code . "</div>";

	// $result = mysqli_query($con,"SELECT 1 FROM iths_users WHERE confirm_code = '$code'");
	
	// $count = mysqli_num_rows($result); 
    
 //    if($count > 0){  // Record exists. Update status to confirmed.
 //   		$result = mysqli_query($con,"UPDATE iths_users SET status=1, confirm_code=null WHERE confirm_code='$code'"); 
	// 	if($result === TRUE) {
	// 		echo "<div>Your email is valid, thanks!. You may now login.</div>"; // User's status confirmed.
	// 		// Add link to app ?? Check it out!!!
	// 	} else {
	// 		echo "<div>Our aplologies! Your registration could not be confirmed at this time. Please try again later.</div>"; // User's satus could not be updated.
	// 	}        
 //    } else { // No record found.
 //   		echo "<div>You maybe in the wrong page.</div>";
	// }		          
   ?>

<?php
	//mysqli_free_result($result);  
	mysqli_close($con);
?>