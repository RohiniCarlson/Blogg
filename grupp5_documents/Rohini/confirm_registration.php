<?php
	include 'connect_db.php';
?>

<?php
   
	$code = mysqli_real_escape_string($con, $_GET['code']);
	echo "<div>Your confirmation code is " . $code . "</div>";

<<<<<<< HEAD
	// $result = mysqli_query($con,"SELECT 1 FROM iths_users WHERE confirm_code = '$code'");
	
	// $count = mysqli_num_rows($result); 
    
 //    if($count > 0){  // Record exists. Update status to confirmed.
 //   		$result = mysqli_query($con,"UPDATE iths_users SET status=1, confirm_code=null WHERE confirm_code='$code'"); 
	// 	if($result === TRUE) {
=======
	$result = mysqli_query($con,"SELECT 1 FROM iths_users WHERE confirm_code = '$code'");
	
	$count = mysqli_num_rows($result); 
    
  	if($count > 0){  // Record exists. Update status to confirmed.
  		if (mysqli_query($con,"UPDATE iths_users SET status=1, confirm_code=null WHERE confirm_code='$code'")) {
  			echo "<div>Your email is valid, thanks! You may now login.</div>"; // User's status confirmed.	
  		} else { // Could not be updated.
  			echo "<div>Our aplologies! Your registration could not be confirmed at this time. Please try again later.</div>"; // User's satus could not be updated.
  		}
  	} else { // No record found.
  		echo "<div>You maybe in the wrong page.</div>";
  	}
 		
 		//$result = mysqli_query($con,"UPDATE iths_users SET status=1, confirm_code=null WHERE confirm_code='$code'"); 
		//if($result === TRUE) {
>>>>>>> c25105ca8cc4899206235bfcca80f173a67d6003
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
<<<<<<< HEAD
	//mysqli_free_result($result);  
=======
	mysqli_free_result($result);  
>>>>>>> c25105ca8cc4899206235bfcca80f173a67d6003
	mysqli_close($con);
?>