<?php
	include 'connect_db.php';
?>
<?php
	
	$postkey = mysqli_real_escape_string($con, $_POST['postkey']);
	$title = mysqli_real_escape_string($con, $_POST['title']);
	$text = mysqli_real_escape_string($con, $_POST['text']);
	$image = mysqli_real_escape_string($con, $_POST['image']);
	
	if ($postkey == "rkyvlbXFGLHJ52716879") {
		
		mysqli_query($con,"INSERT INTO iths_posts (title,txt) 
		VALUES ('$title','$text')");
		
		$last_id = mysqli_insert_id($con);

		echo "success, post added to database";

		if ($image != "0") {

			mysqli_query($con,"UPDATE iths_posts SET image=1 WHERE id=$last_id");

			$base=$_REQUEST['image'];
	     	$binary=base64_decode($base);
		    header('Content-Type: bitmap; charset=utf-8');
		    $file = fopen('images/'.$last_id.'.jpg', 'wb');
		    fwrite($file, $binary);
		    fclose($file);
		}

	} else {
		echo "error";
	}

?>
<?php
	mysqli_close($con);

?>