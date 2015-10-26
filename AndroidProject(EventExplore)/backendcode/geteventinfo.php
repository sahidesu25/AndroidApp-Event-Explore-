<?php

function getEventsInfo($id) {
	
	$conn=geteventDB();
	
	
$sql="SELECT events.EventId,events.EventName,events.StartDateandTime,events.rating,events.VedioUrl,events.EndDateandTime,events.EventPrice,events.MaxCapacity,events.LogoImageUrl,location.State,location.City,location.Address,location.Zipcode,categories.CategoryName,categories.Categoryid,eventhostinfo.HostName,eventhostinfo.HostEmail,eventhostinfo.ContactNumber from events inner join location inner join categories INNER join eventhostinfo on events.Categoryid=categories.Categoryid and events.LocationId=location.LocationId and events.HostId=eventhostinfo.HostId where events.EventId=".$id;


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