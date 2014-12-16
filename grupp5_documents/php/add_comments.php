<?php
	include 'connect_db.php';
?>
<?php
		
	$postkey = mysqli_real_escape_string($con, $_POST['postkey']);
	$comment = mysqli_real_escape_string($con, $_POST['comment']);
	$session_id = mysqli_real_escape_string($con, $_POST['session_id']);
	$post_id = mysqli_real_escape_string($con, $_POST['post_id']);

	$result = mysqli_query($con,"SELECT id FROM iths_users WHERE sessionID='$session_id'");

	$count=mysqli_num_rows($result);

   if($count > 0) {

	 	$id_user = "";

	   	while($r = mysqli_fetch_assoc($result))
		{											
			$id_user = $r['id'];	
		}

		if ($postkey == "rkyvlbXFGLHJ52716879") {
			mysqli_query($con,"INSERT INTO iths_comments (id_post,id_user,commenttext) VALUES ('$post_id','$id_user','$comment')");

			echo "success";
		} else {
			echo "error";
		}

	} else {
		echo "error session id";
	}

?>
<?php
	mysqli_close($con);

?>