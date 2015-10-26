<?php

function getEventsWeekEnd() {
	
	$conn=geteventDB();
	
	
$sql="SELECT events.EventId,events.EventName,events.StartDateandTime,events.rating,events.EndDateandTime,events.EventPrice,events.MaxCapacity,events.LogoImageUrl,location.State,location.City,location.Address,location.Zipcode,categories.CategoryName,categories.Categoryid from events inner join location inner join categories on events.Categoryid=categories.Categoryid and events.LocationId=location.LocationId WHERE DAYOFWEEK(StartDateandTime) = 7
or DAYOFWEEK(StartDateandTime) = 1 or (DATE_FORMAT(StartDateandTime, '%T') > '17:30:00' AND DAYOFWEEK(StartDateandTime) = 6)";


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