<?php
include('connections.php');
$response = array();

$email = $_POST['email'];
$password = $_POST['password'];
$account_is_verified = 'verified';

if($_SERVER['REQUEST_METHOD']=='POST')
{
	 $qry_login = mysqli_query($conn,"SELECT * FROM users WHERE email='$email' AND password='$password'");
	 if(!$qry_login)
	 {
	 	$response['error'] = true;
	 	$response['message'] = "There was a problem conducting the login request";
	 }
	 $row = mysqli_fetch_array($qry_login);
	 if(mysqli_num_rows($qry_login)>0)
	 {
	 	$account_verified = $row['account_status'];
        if($account_verified== $account_is_verified)
        {
        	$response['error'] = false;
	 	   $response['user_id'] = $row['user_id'];
	 	   $response['fname'] = $row['fname'];
	 	   $response['email'] = $row['email'];
        }
        else if($account_verified!=$account_is_verified)
        {
        	 $response['error'] = true;
            $response['message'] = "Your account has not been activated or it has been suspended.Please contact the admin for further details.";
        }

	 	
	 }
	 else if(mysqli_num_rows($qry_login)==0)
	 {
         $response['error'] = true;
         $response['message'] = "Username and Password do not match";
	 }
}
else
{
	    $response['error'] = true;
	 	$response['message'] = "There was a problem conducting the login request";
}
echo  json_encode($response);
?>