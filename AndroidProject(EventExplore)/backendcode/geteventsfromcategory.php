<?php

function getEventsFromCategories($categoryid) {
	
	$conn=geteventDB();
	
	
$sql="SELECT events.EventId,events.EventName,events.StartDateandTime,events.rating,events.EndDateandTime,events.EventPrice,events.MaxCapacity,events.LogoImageUrl,location.State,location.City,location.Address,location.Zipcode,categories.CategoryName,categories.Categoryid from events inner join location inner join categories on events.Categoryid=categories.Categoryid and events.LocationId=location.LocationId where events.Categoryid=$categoryid";
$query="SELECT categories.CategoryName,events.EventId,events.Description,events.EventName,events.StartDateandTime,events.EndDateandTime,events.EventPrice,events.MaxCapacity,events.LogoImageUrl FROM `events` inner join categories on events.Categoryid=categories.Categoryid";


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