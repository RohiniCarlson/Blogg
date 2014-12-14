<?php
	include 'connect_db.php';
?>

<?php
   
	$code = mysqli_real_escape_string($con, $_GET['code']);

	$result = mysqli_query($con,"SELECT 1 FROM iths_users WHERE confirm_code = '$code'");
	
	$count = mysqli_num_rows($result); 
    
  	if($count > 0){  // Record exists. Update status to confirmed.
  		if (mysqli_query($con,"UPDATE iths_users SET status=1, confirm_code=null WHERE confirm_code='$code'")) { // User's status confirmed.  
  			echo "<html>   
                <body style='background-color:GhostWhite;'>
                  <div style='top:0; bottom:0; left:0; right:0; margin:auto; position:absolute; width:60%; height:30%; padding-top:50px; border:solid 1px; border-color:LightGray; background-color:White; box-shadow: 7px 7px 7px LightSteelBlue;text-align:center'> 
                    <h1 style='color:#9999FF; font-weight:bold'>Welcome to the ITHS-Blogg!</h1>
                    <p style='font-size:20px'>Your registration has been confirmed. You may now login.</p>
                  </div>
                </body>
              </html>";
  		} else { // Status could not be updated.
  			echo "<html>   
                <body style='background-color:GhostWhite;'>
                  <div style='top:0; bottom:0; left:0; right:0; margin:auto; position:absolute; width:60%; height:30%; padding-top:50px; border:solid 1px; border-color:LightGray; background-color:White; box-shadow: 7px 7px 7px LightSteelBlue;text-align:center'> 
                    <h1 style='color:#9999FF; font-weight:bold'>Our aplologies!<small> - ITHS-Blogg</small></h1>
                    <p style='font-size:20px'>Your registration could not be confirmed at this time.<br>Please try again later.</p>
                  </div>
                </body>
              </html>"; 
  		}
  	} else { // No record found.
  		echo "<html>   
              <body style='background-color:GhostWhite;'>
                <div style='top:0; bottom:0; left:0; right:0; margin:auto; position:absolute; width:60%; height:30%; padding-top:50px; border:solid 1px; border-color:LightGray; background-color:White; box-shadow: 7px 7px 7px LightSteelBlue;text-align:center'> 
                  <h1 style='color:#9999FF; font-weight:bold'>Attention Please!<small> - ITHS-Blogg</small></h1>
                  <p style='font-size:20px'>Your registration is already confirmed or<br>you maybe in the wrong page!</p>
                </div>
              </body>
            </html>";
  	}	 		         
?>

<?php
	mysqli_free_result($result);  
	mysqli_close($con);
?>