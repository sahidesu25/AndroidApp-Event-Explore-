<?php

function getNumberOfTicketsSoldForEvent($id) {
	
	$conn=geteventDB();
	
	
$query="SELECT sum(nooftickets) as Peoplegoing FROM `ticketscart` WHERE EventId=$id GROUP by EventId";

	if(!$result = $conn->query($query)){
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