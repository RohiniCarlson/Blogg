<?php
	include 'connect_db.php';
?>
<?php

	$post = mysqli_real_escape_string($con, $_POST['post1']);


	if ($post == "value1") {
		mysqli_query($con,"INSERT INTO iths_users (name,readerOrAdmin,mail) 
		VALUES ('MR_PHP',0,'$post')");
	}

	$result = mysqli_query($con,"SELECT * FROM iths_posts");

 	$response = array();
 	
	//Loop through all response and add them to our array
	while($r = mysqli_fetch_assoc($result))
	{				
		$response[] = $r;			
	}

	echo json_encode($response);

?>
<?php
	mysqli_close($con);

?>