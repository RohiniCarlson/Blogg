<?php
	include 'connect_db.php';
?>
<?php
		
	// $postkey = mysqli_real_escape_string($con, $_POST['postkey']);

	$comment = mysqli_real_escape_string($con, $_POST['comment']);
	$user_id = mysqli_real_escape_string($con, $_POST['user_id']);
	$post_id = mysqli_real_escape_string($con, $_POST['post_id']);

	if ($postkey == null) {
		mysqli_query($con,"INSERT INTO iths_comments (id_user,id_post,commenttext) 
		VALUES ('$post_id','$user_id','$comment')");
	}

?>
<?php
	mysqli_close($con);

?>