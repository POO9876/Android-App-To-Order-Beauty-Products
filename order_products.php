<?php
include('connections.php');
$response = array();
$customer_name = $_POST['customer_name'];
$customer_email = $_POST['customer_email'];
$product_name = $_POST['product_name'];
$quantity_ordered = $_POST['quantity_ordered'];
$date_of_delivery = $_POST['date_of_delivery'];
$order_status = "pending";
$image = "https://previews.123rf.com/images/smaglov/smaglov1410/smaglov141000065/32250612-Golden-letter-p-lowercase-high-quality-3d-render-isolated-on-white-Stock-Photo.jpg";
if($_SERVER['REQUEST_METHOD']=='POST')
{

	if(empty($customer_name) or empty($customer_email) or empty($product_name) or empty($quantity_ordered) or empty($date_of_delivery))
	{
		$response['error'] = true;
		$response['message'] = "Please Fill In All The Fields";
	}
	else
	{
       $insert = "INSERT INTO product_orders(customer_name,customer_email,product_name,quantity_ordered,date_of_delivery,order_status,image)VALUES('$customer_name','$customer_email','$product_name','$quantity_ordered','$date_of_delivery','$order_status','$image')";
       if(!mysqli_query($conn,$insert))
       {
           $response['error'] = true;
          $response['message'] = "There was an error in the insertion query";
       }
       else
       {
                      $response['error'] = false;
                      $response['message'] = "You Successfully Made An Order!";
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