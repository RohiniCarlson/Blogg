<?php
	include ’connectfil.php';
?>

<?php
   
	$mail = $_GET["mail"];	
	$password = $_GET["password"];

	// To protect MySQL injection 
	// $mail = stripslashes($mail);
	// $password = stripslashes($password);
	// $mail = mysql_real_escape_string($mail);
	// $password = mysql_real_escape_string($password);


	// mysqli_query($con,"INSERT INTO iths_users (name,readerOrAdmin,mail) 
	// VALUES ('MR_Test',0,'test')");

	$sql="SELECT * FROM iths_users WHERE mail = '$mail' AND readerOrAdmin = 1";
	$result=mysqli_query($con,$sql);
	$count=mysqli_num_rows($result);
    
   if($count < 1){
    echo "0"; //Indicates username failure
   } else {
   	echo "1";		
   }    

	

   // $password = str_replace("'","''",$password);
   // $password = md5($password);	


	

		
       
   ?>


<?php
	mysqli_close($con);
?>