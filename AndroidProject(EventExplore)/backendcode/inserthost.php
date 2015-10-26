<?php

function inserthost($data)
{
   $conn=geteventDB();
   
   $hostname= $data['hostname'];
$contactnumber= $data['contactnumber'];

$email= $data['email'];
$check="SELECT COUNT(1) as count_ FROM eventhostinfo WHERE hostemail = '$email'";
if(!$result = $conn->query($check)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	
	$count=0;
	$return_arr=array();
	while($row=$result->fetch_assoc())
	{
		array_push($return_arr,$row);
		$count=$row['count_'];

	}
	if($count>=1)
	{
		echo "This Information already exists, Press Next to Continue";
	}
	
	
	else
	{
		$sql="INSERT INTO eventhostinfo(hostname,hostemail,contactnumber) VALUES('$hostname','$email','$contactnumber')";
		
		//die('There was an error running the query [' .$conn->error . ']\n');
		if(!$result = $conn->query($sql)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	else
	echo "New Record Sucessfully inserted, Press Next to Continue";
	}
	
	
	
	    
		//$stmt->execute();
		  $conn->close();
	
}
?>
