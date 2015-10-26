<?php

function insertlocation($data)
{
	 $conn=geteventDB();
	$state=$data['state'];
	$city=$data['city'];
	$address=$data['address'];
	$zipcode=$data['zipcode'];
	$check="SELECT COUNT(1) as number FROM location WHERE state = '$state' and city='$city' and address='$address' and zipcode='$zipcode'";
	if(!$result = $conn->query($check)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	
	
	$return_arr=array();
	$count=0;
	while($row=$result->fetch_assoc())
	{
		array_push($return_arr,$row);
		$count=$row['number'];

	}
	if($count>=1)
	{
		echo "Location Info is already there";
		
		
	
	}
	else
	{
		echo "Inserting new Record";
		 $sql="INSERT INTO location(state,city,address,zipcode) VALUES('$state','$city','$address','$zipcode')";
		
		if(!$result = $conn->query($sql)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	}
	
	
	
	
	
	
	   
		//$stmt->execute();
		  $conn->close();
	
}
?>
