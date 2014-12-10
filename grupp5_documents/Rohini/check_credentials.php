<?php
	include 'connect_db.php';
?>

<?php
   
	$mail = mysqli_real_escape_string($con, $_GET['mail']);	
	$password = mysqli_real_escape_string($con, $_GET['password']);

  $result = mysqli_query($con,"SELECT status FROM iths_users WHERE mail = '$mail' AND password = '$password'");
  
  //$sql="SELECT status FROM iths_users WHERE mail = '$mail' AND password = '$password'";
	//$result=mysqli_query($con,$sql);
	$count = mysqli_num_rows($result);		

  if ($count > 1) {
    while ($row = mysqli_fetch_assoc($result)) {
      $status = $row["status"];
    }
    if ($status == 0) {
      echo "2"; // Correct credentials, but registration incomplete (status=0, i.e pending).
    }else {
      echo "1";  // Correct credentials and registration complete (status=1, i.e confirmed).
    }
  } else { // Incorrect credentials. No matching row.
    echo "0";
  }
    
   //  if($count == 0){
   //      echo "0"; // Incorrect credentials. No matching row.
   // } else {
   	 	
   //    while($row = mysqli_fetch_assoc($result)) {
   //        $status = $row["status"];
   //    }
      
   //    if ($status == 0) {
   //      echo "2"; // Correct credentials, but registration incomplete (status=0, i.e pending).
   //    } else {
   //        echo "1";  // Correct credentials and registration complete (status=1, i.e confirmed).
   //    }	
   // }
      
   ?>

<?php
  mysqli_free_result($result); 
	mysqli_close($con);
?>