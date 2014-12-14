<?php
	include 'connect_db.php';
  include 'PasswordHash.php';
?>

<?php
   
	$mail = mysqli_real_escape_string($con, $_POST['mail']);	
	$password = mysqli_real_escape_string($con, $_POST['password']);

  $status = "";
  $id = "";
  $isAdmin = "";
  $session_id = "";

  $result = mysqli_query($con,"SELECT id,status,password,readerOrAdmin FROM iths_users WHERE mail = '$mail'");

	$count = mysqli_num_rows($result);

  if ($count > 0) {
    while ($row = mysqli_fetch_assoc($result)) {
      $id = $row["id"];
      $status = $row["status"];
      $hashedPassword = $row["password"];
      $isAdmin = $row["readerOrAdmin"];
    }
    if (validate_password($password,$hashedPassword)) { // Valid password.
      if ($status == 1) { // Correct credentials and registration complete (status=1, i.e confirmed).
        $session_id = md5(uniqid("yourcredentialsarecorrectandthisisyournewsessionid"));
        if (mysqli_query($con,"UPDATE iths_users SET sessionID='$session_id' WHERE mail = '$mail'")) {
          echo $session_id . "$$$" . $isAdmin . "$$$" . $id;
        } else {
          echo "LogInFailed"; 
        }        
      } else { // Correct credentials, but registration pending (status=0, i.e confirmed).
        echo "StatusPending";
      }
    } else { // Password invalid.
      echo "NotFound";
    }    
  } else { 
    echo "NotFound";
  }

?>

<?php
  mysqli_free_result($result); 
	mysqli_close($con);
?>