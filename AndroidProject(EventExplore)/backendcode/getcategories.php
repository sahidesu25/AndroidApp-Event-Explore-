<?php

function getCategories() {
	
	$conn=geteventDB();
	$sql="SELECT * FROM categories";
	if(!$result = $conn->query($sql)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	$return_arr=array();
	while($row=$result->fetch_assoc())
	{
		array_push($return_arr,$row);
	}
	echo json_encode($return_arr);
	$conn->close();
}	



?>	