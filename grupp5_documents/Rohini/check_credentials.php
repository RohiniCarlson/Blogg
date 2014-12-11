<?php
	include 'connect_db.php';
?>

<?php
   
	$mail = mysqli_real_escape_string($con, $_GET['mail']);	
	$password = mysqli_real_escape_string($con, $_GET['password']);

  $result = mysqli_query($con,"SELECT status, id, readerOrAdmin FROM iths_users WHERE mail = '$mail'");

	$count = mysqli_num_rows($result);	
  if ($count > 0) {
    echo "Found";

  }	else {
    echo "Not Found";
  }

  // if ($count > 0) {
  //   while ($row = mysqli_fetch_assoc($result)) {
  //     $status = $row["status"];
  //     $user_id = $row["id"];
  //   }
  //   if ($status == 0) { // Correct credentials, but registration incomplete (status=0, i.e pending).
  //     echo "2"; 
  //   }else {  // Correct credentials and registration complete (status=1, i.e confirmed).
  //    // echo "1"; 
  //     // Need to decrypt password and authenticate it.
  //     $session_id = md5(uniqid("yourcredentialsarecorrectandthisisyournewsessionid"));
  //     if (mysqli_query($con,"UPDATE iths_users SET sessionID='$session_id' WHERE id = '$user_id'")) {
  //       //TO DO
  //       $result = mysqli_query($con,"SELECT sessionID, readerOrAdmin, FROM iths_users WHERE sessionID='$session_id'");
  //       $count = mysqli_num_rows($result); 
  //       if ($count > 0) { // Found record with correct sessionId 
  //         while ($row = mysqli_fetch_assoc($result)) {
  //           $sessionId = $row["sessionID"];
  //           $admin = $row["readerOrAdmin"]; 
  //         }         
  //         $values = "" . $sessionId . "+" . $admin;         
  //         echo $values;
  //       } else { // Could not find sesionID.
  //         echo "-1"; 
  //       }       
  //     } else { // Could not create a sesionID.
  //       echo "-1"; 
  //     }    
  //   }
  // } else { // Incorrect credentials. No matching row.
  //   echo "0";
  // }
    
?>

<?php
 // mysqli_free_result($result); 
	mysqli_close($con);
?>