<?php

function insertticketscart($data)
{
	 $conn=geteventDB();
	$eventid=$data['eventid'];
	$username=$data['username'];
	$nooftickets=$data['noofticketstaken'];
	$check="SELECT COUNT(1) as number FROM ticketscart WHERE eventid = $eventid and username='$username'";
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
		echo "Updating the exsisting table";
		
		
	$update_="Update ticketscart set nooftickets=nooftickets+$nooftickets where eventid = $eventid and username='$username' ";
			if(!$_result = $conn->query($update_)){
	die('There was an error running the query [' .$conn->error . ']\n');
		
	}
	}
	else
	{
		echo "Inserting new Record";
		 $sql="INSERT INTO ticketscart(eventid,username,nooftickets) VALUES($eventid,'$username',$nooftickets)";
		
		if(!$result = $conn->query($sql)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	}
	
	
	
	
	
	
	   
		//$stmt->execute();
		  $conn->close();
	
}
?>
