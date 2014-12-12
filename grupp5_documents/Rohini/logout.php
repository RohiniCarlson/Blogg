<?php
	include 'connect_db.php';
?>

<?php
   
	$session_id = mysqli_real_escape_string($con, $_POST['sessionid']);	

  echo $session_id;

  // if (mysqli_query($con,"UPDATE iths_users SET sessionID=null WHERE sessionID ='$session_id'")) {
  //   echo "LoggedOut";
  // } else {
  //   "CouldNotLogOut";
  // }
    
?>

<?php
	mysqli_close($con);
?>