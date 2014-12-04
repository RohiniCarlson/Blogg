<?php
	include 'connect_db.php';
?>

<?php
   
	$name = $_POST['name'];
	$mail = $_POST['email'];	
	$password = $_POST['password'];

	$result = mysqli_query($con,"SELECT COUNT(*) FROM iths_users WHERE mail = '$mail' AND password = '$password'");
	
	$count = mysqli_num_rows($result); 
    
    if($count == 0){
   		mysqli_query($con,"INSERT INTO iths_users (name,readerOrAdmin,mail,password) 
		VALUES ('$name',0,'$mail','$password')");
        echo "1"; //User does not exist. Create new user.
   } else {
   	echo "0";		
   }
       
   ?>

<?php
	mysqli_free_result($result); 

	mysqli_close($con);
?>