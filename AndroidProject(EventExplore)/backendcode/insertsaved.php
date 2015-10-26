<?php

function insertsavedevent($data)
{
	 $conn=geteventDB();
	$eventid=$data['EventId'];
	$username=$data['username'];
	$check="SELECT COUNT(1) as number FROM savedevents WHERE eventid = $eventid and username='$username'";
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
		echo "Already saved";
		
		
	
	}
	else
	{
		echo "Inserting new Record";
		 $sql="INSERT INTO savedevents(eventid,username) VALUES($eventid,'$username')";
		
		if(!$result = $conn->query($sql)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	}
	
	
	
	
	
	
	   
		//$stmt->execute();
		  $conn->close();
	
}
?>
