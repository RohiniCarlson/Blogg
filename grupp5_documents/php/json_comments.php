<?php
	include 'connect_db.php';
?>
<?php

	$post = mysqli_real_escape_string($con, $_POST['post_id']);

	$result = mysqli_query($con,"SELECT commenttext, name FROM iths_comments c, iths_users u WHERE c.id_user = u.id AND c.id_post=$post");

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