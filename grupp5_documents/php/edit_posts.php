<?php
	include 'connect_db.php';
?>
<?php
	
	$postkey = mysqli_real_escape_string($con, $_POST['postkey']);
	$post_id = mysqli_real_escape_string($con, $_POST['post_id']);
	$title = mysqli_real_escape_string($con, $_POST['title']);
	$text = mysqli_real_escape_string($con, $_POST['text']);
	$image = mysqli_real_escape_string($con, $_POST['image']);
	
	echo $post_id;

	if ($postkey == "rkyvlbXFGLHJ52716879") {
		
		mysqli_query($con,"UPDATE iths_posts SET title='$title', txt='$text' WHERE id='$post_id')");			

		echo "success, post changed in database";

		if ($image != null) {
		
			$base=$_REQUEST['image'];
	     	$binary=base64_decode($base);
		    header('Content-Type: bitmap; charset=utf-8');
		    $file = fopen('images/'.$post_id.'.jpg', 'wb');
		    fwrite($file, $binary);
		    fclose($file);

			// make an thumbnail			
			include("resize-class.php");

			// *** 1) Initialise / load image
			$resizeObj = new resize('images/'.$post_id.'.jpg');

			// Porttrait or landscape?			
			list($width, $height, $type, $attr) = getimagesize('images/'.$post_id.'.jpg');		

			$bredd = 200;

			// *** 2) Resize image (options: exact, portrait, landscape, auto, crop)
			$resizeObj -> resizeImage($bredd, 200, 'landscape');

			// *** 3) Save image
			$resizeObj -> saveImage('images/'. $post_id. '_thumb'.'.jpg', 70);	

		}

	} else {
		echo "error";
	}

?>
<?php
	mysqli_close($con);

?>