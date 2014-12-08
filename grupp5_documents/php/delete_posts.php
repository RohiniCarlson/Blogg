<?php
	include 'connect_db.php';
?>
<?php
		
	$postkey = mysqli_real_escape_string($con, $_POST['postkey']);
	$post_id = mysqli_real_escape_string($con, $_POST['post_id']);

	if ($postkey == "rkyvlbXFGLHJ52716879") {
		mysqli_query($con,"DELETE FROM iths_posts WHERE id=$post_id");

		mysqli_query($con,"DELETE FROM iths_comments WHERE id_post=$post_id");

		echo "post deleted, the id was: ".$post_id;
	} else {
		echo "error";
	}

?>
<?php
	mysqli_close($con);

?>