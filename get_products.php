<?php
include('connections.php');
$response = array();
$array = array();
$subArray=array();


if($_SERVER['REQUEST_METHOD']=='GET')
{
	
	$qry_products = mysqli_query($conn,"SELECT * FROM products");
	if(!$qry_products)
	{
		die('cannot get products:'.mysqli_error($conn));
	}
	
	while($row = mysqli_fetch_array($qry_products))
	{
		 $subArray['product_name']=$row['product_name'];
		 

		  $array[] =  $subArray ;

	}
	
}
else
{
	    $response['error'] = true;
	 	$response['message'] = "There was a problem conducting the fetch request";
}
echo'{"products":'.json_encode($array).'}';
?>