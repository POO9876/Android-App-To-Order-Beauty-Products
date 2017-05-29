<?php
include('connections.php');
$response = array();
$fname = $_POST['fname'];
$lname = $_POST['lname'];
$email = $_POST['email'];
$pnumber = $_POST['pnumber'];
$password = $_POST['password'];
$account_status = "not_verified";
if($_SERVER['REQUEST_METHOD']=='POST')
{

	if(empty($fname) or empty($lname) or empty($email) or empty($pnumber) or empty($password))
	{
		$response['error'] = true;
		$response['message'] = "Please Fill In All The Fields";
	}
	else
	{

        $qry_email = mysqli_query($conn,"SELECT * FROM users WHERE email='$email'");
        if(!$qry_email)
        {
        	$response['error'] = true;
		    $response['message'] = "Server Error Please Contact The Admin!";
        }
        $row = mysqli_fetch_array($qry_email);
        if(mysqli_num_rows($qry_email)>0)
        {
        	$response['error'] = true;
		    $response['message'] = "The email you entered exists in the server!";
        }
        else if(mysqli_num_rows($qry_email)==0)
        {

        	$qry_pnumber = mysqli_query($conn,"SELECT * FROM users WHERE pnumber='$pnumber'");
        	if(!$qry_pnumber)
        	{
        		$response['error'] = true;
		         $response['message'] = "Server Error Please Contact The Admin!";
        	}
        	$row = mysqli_fetch_array($qry_pnumber);
        	if(mysqli_num_rows($qry_pnumber)>0)
        	{
        		$response['error'] = true;
		        $response['message'] = "The phone number you entered exists in the server!";
        	}
        	else
        	{
        		$insert = "INSERT INTO users(fname,lname,email,pnumber,password,account_status)VALUES('$fname','$lname','$email','$pnumber','$password','$account_status')";
	            if(!mysqli_query($conn,$insert))
	            {
		              $response['error'] = true;
		              $response['message'] = "There was an error in the insertion query";
	            }
	         else
	         {
		             $response['error'] = false;
		              $response['message'] = "Registration Successful!";
	          }
        	}
        }

	
   }
}
else
{
	$response['error'] = true;
		$response['message'] = "Invalid Request";
}
echo json_encode($response);
?>