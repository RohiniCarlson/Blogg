<?php
	include 'connect_db.php';
?>
<?php

	$post = mysqli_real_escape_string($con, $_POST['post_id']);
	$offset = mysqli_real_escape_string($con, $_POST['offset']);

	$result = mysqli_query($con,"SELECT commenttext, date, name FROM iths_comments c, iths_users u WHERE c.id_user = u.id AND c.id_post=$post ORDER BY c.id DESC LIMIT 10 OFFSET {$offset}");
	// $result = mysqli_query($con,"SELECT user_id, date, commenttext, name FROM iths_comments c, iths_users u WHERE c.id_user = u.id AND c.id_post=$post");

	function get_time_ago($time_stamp)
	{
	    $time_difference = strtotime('now') - $time_stamp;
	
	    if ($time_difference >= 60 * 60 * 24 * 365.242199)
	    {
		/*
		 * 60 seconds/minute * 60 minutes/hour * 24 hours/day * 365.242199 days/year
		 * This means that the time difference is 1 year or more
		 */
		return get_time_ago_string($time_stamp, 60 * 60 * 24 * 365.242199, 'year');
	    }
	    elseif ($time_difference >= 60 * 60 * 24 * 30.4368499)
	    {
		/*
		 * 60 seconds/minute * 60 minutes/hour * 24 hours/day * 30.4368499 days/month
		 * This means that the time difference is 1 month or more
		 */
		return get_time_ago_string($time_stamp, 60 * 60 * 24 * 30.4368499, 'month');
	    }
	    elseif ($time_difference >= 60 * 60 * 24 * 7)
	    {
		/*
		 * 60 seconds/minute * 60 minutes/hour * 24 hours/day * 7 days/week
		 * This means that the time difference is 1 week or more
		 */
		return get_time_ago_string($time_stamp, 60 * 60 * 24 * 7, 'week');
	    }
	    elseif ($time_difference >= 60 * 60 * 24)
	    {
		/*
		 * 60 seconds/minute * 60 minutes/hour * 24 hours/day
		 * This means that the time difference is 1 day or more
		 */
		return get_time_ago_string($time_stamp, 60 * 60 * 24, 'day');
	    }
	    elseif ($time_difference >= 60 * 60)
	    {
		/*
		 * 60 seconds/minute * 60 minutes/hour
		 * This means that the time difference is 1 hour or more
		 */
		return get_time_ago_string($time_stamp, 60 * 60, 'hour');
	    }
	    else
	    {
		/*
		 * 60 seconds/minute
		 * This means that the time difference is a matter of minutes
		 */
		return get_time_ago_string($time_stamp, 60, 'minute');
	    }
	}
	
	function get_time_ago_string($time_stamp, $divisor, $time_unit)
	{
	    $time_difference = strtotime("now") - $time_stamp;
	    $time_units      = floor($time_difference / $divisor);
	
	    settype($time_units, 'string');
	
	    if ($time_units === '0')
	    {
		return 'less than 1 ' . $time_unit . ' ago';
	    }
	    elseif ($time_units === '1')
	    {
		return '1 ' . $time_unit . ' ago';
	    }
	    else
	    {
		/*
		 * More than "1" $time_unit. This is the "plural" message.
		 */
		// TODO: This pluralizes the time unit, which is done by adding "s" at the end; this will not work for i18n!
		return $time_units . ' ' . $time_unit . 's ago';
	    }
	}	

 	$response = array();

 	$currentTime = (date("Y-m-d-h:m:s"));
 	
	//Loop through all response and add them to our array
	while($r = mysqli_fetch_assoc($result))
	{				
		$newtime = get_time_ago(strtotime($r['date']));						
		$r['date'] = $newtime;

		$response[] = $r;			
	}

	echo json_encode($response);

	mysqli_free_result($result);

?>
<?php
	mysqli_close($con);

?>