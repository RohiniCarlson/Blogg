<?php
	include 'connect_db.php';
?>

<?php
   
	$name = mysql_real_escape_string($_POST['name']);
	$mail = mysql_real_escape_string($_POST['email']);	
	$password = mysql_real_escape_string($_POST['password']);

	$result = mysqli_query($con,"SELECT 1 FROM iths_users WHERE mail = '$mail' AND password = '$password'");
	
	$count = mysqli_num_rows($result); 
    
    if($count == 0){  // User does not exist. Create new user.
   		$add = mysqli_query($con,"INSERT INTO iths_users (name,readerOrAdmin,mail,password) 
		VALUES ('$name',0,'$mail','$password')");
		if($add) {
			echo "1"; // User was added to the database.
		} else {
			echo "-1"; // User could not be added to the database.
		}
        
   } else { // User exists.
   	echo "0";		
   }
       
   ?>

<?php
	mysqli_free_result($result); 

	mysqli_close($con);
?>