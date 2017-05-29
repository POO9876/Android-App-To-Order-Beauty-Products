<?php
include('connections.php');
$response = array();
$array = array();
$subArray=array();


if($_SERVER['REQUEST_METHOD']=='GET')
{
	
	$qry_staff = mysqli_query($conn,"SELECT * FROM product_orders WHERE order_status='approved'");
	if(!$qry_staff)
	{
		die('cannot get staff:'.mysqli_error($conn));
	}
	
	while($row = mysqli_fetch_array($qry_staff))
	{

		 $subArray['customer_name']=$row['customer_name'];
		 $subArray['product_name']=$row['product_name'];
		 $subArray['quantity_ordered']=$row['quantity_ordered'];
		 $subArray['date_of_delivery']=$row['date_of_delivery'];
		 $subArray['image']=$row['image'];
		

		  $array[] =  $subArray ;

	}
	
}
else
{
	    $response['error'] = true;
	 	$response['message'] = "There was a problem establishing the request";
}
echo'{"orders":'.json_encode($array).'}';
?>