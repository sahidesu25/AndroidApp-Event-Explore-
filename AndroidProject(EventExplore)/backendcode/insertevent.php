<?php

function insertevent($data)
{
	 $conn=geteventDB();
	$state=$data['state'];
	$city=$data['city'];
	$address=$data['address'];
	$zipcode=$data['zipcode'];
	$eventname= $data['eventname'];
	$eventprice=$data['eventprice'];
	$maxcapacity= $data['maxcapacity'];
	$logourl= $data['logourl'];
	$vediourl= $data['vediourl'];
	$category=$data['category'];
	$rating= $data['rating'];
	
	$hostname= $data['hostname'];
	$contactnumber= $data['contactnumber'];
	$email= $data['email'];
	$startdateandtime= $data['startdateandtime'];
	$enddateandtime= $data['enddateandtime'];
		echo "Getting Categoryid";
	$categoryid="SELECT Categoryid as categoryid FROM categories WHERE CategoryName='$category'";
	if(!$result = $conn->query($categoryid)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	$return_arr=array();
	$category_id=0;
	while($row=$result->fetch_assoc())
	{
		array_push($return_arr,$row);
		$category_id=$row['categoryid'];

	}
	echo "Getting Locationid";
	$locationid="SELECT LocationId as locid FROM location WHERE State='$state' and City='$city' and Address='$address' and Zipcode='$zipcode'";
	if(!$result = $conn->query($locationid)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	$return_arr_=array();
	$location_id=0;
	while($row=$result->fetch_assoc())
	{
		array_push($return_arr_,$row);
		$location_id=$row['locid'];

	}
		echo "Getting Hostid";
	$hostid="SELECT HostId as hosid FROM eventhostinfo WHERE HostName='$hostname' and HostEmail='$email' and ContactNumber='$contactnumber'";
	if(!$result = $conn->query($hostid)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	$_return_arr=array();
	$host_id=0;
	while($row=$result->fetch_assoc())
	{
		array_push($_return_arr,$row);
		$host_id=$row['hosid'];

	}
	 $des="aaaaaa";
	
		echo "Inserting  Record";
		 $sql="INSERT INTO events(EventName,Categoryid,LocationId,StartDateandTime,EndDateandTime,LogoImageUrl,EventPrice,IsFree,HostId,MaxCapacity,Rating,IsSelected,VedioUrl,Description) VALUES('$eventname',$category_id,$location_id,'$startdateandtime','$enddateandtime','$logourl',$eventprice,0,$host_id,$maxcapacity,$rating,0,'$vediourl','$des')";
		
		if(!$result = $conn->query($sql)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	
	   
		//$stmt->execute();
		  $conn->close();
	
}
?>
