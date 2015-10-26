<?php

function userSavedEvents($username) {
	
	$conn=geteventDB();
	
	
$query="SELECT events.EventId,events.EventName,events.StartDateandTime,events.rating,events.EndDateandTime,events.EventPrice,events.MaxCapacity,events.LogoImageUrl,location.State,location.City,location.Address,location.Zipcode,categories.CategoryName,categories.Categoryid from events inner join location inner join categories inner join savedevents on events.Categoryid=categories.Categoryid and events.LocationId=location.LocationId and events.EventId=savedevents.eventid where savedevents.username='$username'";

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