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

		echo "success, post added to database".$text;

		if ($image != "0") {

			mysqli_query($con,"UPDATE iths_posts SET image=1 WHERE id=$last_id");

			$base=$_REQUEST['image'];
	     	$binary=base64_decode($base);
		    header('Content-Type: bitmap; charset=utf-8');
		    $file = fopen('images/'.$last_id.'.jpg', 'wb');
		    fwrite($file, $binary);
		    fclose($file);


			// make an thumbnail			
			include("resize-class.php");

			// *** 1) Initialise / load image
			$resizeObj = new resize('images/'.$last_id.'.jpg');

			// Porttrait or landscape?			
			list($width, $height, $type, $attr) = getimagesize('images/'.$last_id.'.jpg');		

			$bredd = 200;

			// *** 2) Resize image (options: exact, portrait, landscape, auto, crop)
			$resizeObj -> resizeImage($bredd, 200, 'landscape');

			// *** 3) Save image
			$resizeObj -> saveImage('images/'. $last_id. '_thumb'.'.jpg', 70);	

		}

	} else {
		echo "error";
	}

?>
<?php
	mysqli_close($con);

?>