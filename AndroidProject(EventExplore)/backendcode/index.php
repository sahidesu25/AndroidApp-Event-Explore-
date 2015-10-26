<?php
/**
 * Step 1: Require the Slim Framework
 *
 * If you are not using Composer, you need to require the
 * Slim Framework and register its PSR-0 autoloader.
 *
 * If you are using Composer, you can skip this step.
 */
require 'Slim/Slim.php';

require 'routes/eventdb.php';
require 'routes/getcategories.php';
require 'routes/geteventsfromcategory.php';
require 'routes/geteventspopular.php';
require 'routes/usersavedevents.php';
require 'routes/geteventinfo.php';
require 'routes/getticketsfromcart.php';
require 'routes/getticketssoldforevent.php';
require 'routes/inserthost.php';
require 'routes/insertticketcart.php';
require 'routes/insertlocation.php';
require 'routes/insertevent.php';
require 'routes/insertsaved.php';
require 'routes/geteventsweekend.php';
require 'routes/geteventsmonth.php';
require 'routes/geteventsnextmonth.php';
//require 'routes/deletesavedevent.php';


\Slim\Slim::registerAutoloader();

/**
 * Step 2: Instantiate a Slim application
 *
 * This example instantiates a Slim application using
 * its default settings. However, you will usually configure
 * your Slim application now by passing an associative array
 * of setting names and values into the application constructor.
 */
$app = new \Slim\Slim();

/**
 * Step 3: Define the Slim application routes
 *
 * Here we define several Slim application routes that respond
 * to appropriate HTTP request methods. In this example, the second
 * argument for `Slim::get`, `Slim::post`, `Slim::put`, `Slim::patch`, and `Slim::delete`
 * is an anonymous function.
 */

// GET route
$app->get(
    '/',
    function () {
      echo "<h1> HomeWork Assignment 2</h1>";
    }
);
$app->get(
    '/events/categories',
    function () {
      getCategories();
    }
);
$app->get(
    '/events/eventinfo/:eventid',
    function ($eventid) {
      getEventsInfo($eventid);
    }
);
$app->get(
     '/events/categoryid/:categoryid',
	 function ($categoryid){
		 getEventsFromCategories($categoryid);
	 }
);
$app->get(
     '/events/weekend/',
	 function (){
		  getEventsWeekEnd();
	 }
);
$app->get(
     '/events/month/',
	 function (){
		  getEventsMonth();
	 }
);
$app->get(
     '/events/nextmonth/',
	 function (){
		  getEventsNextMonth();
	 }
);



$app->get(
     '/events/popular/:rating',
	 function ($rating){
		 getEventsPopular($rating);
	 }
);
$app->get(
     '/events/saved/:username',
	 function ($username){
		 userSavedEvents($username);
	 }
);
$app->get(
     '/events/tickets/:username',
	 function ($username){
		 getticketsfromCart($username);
	 }
);
$app->get(
     '/events/ticketssold/:eventid',
	 function ($eventid){
		 getNumberOfTicketsSoldForEvent($eventid);
	 }
);


// POST route

$app->post(
    '/events/eventinfo/post/',function () use ($app){

        echo 'This is a POST route';
		
		$json=$app->request->getBody();
		$data=json_decode($json,true);
		echo $data['eventname'];
		echo $data['eventprice'];
		echo $data['maxcapacity'];
		echo $data['logourl'];
		echo $data['vediourl'];
		echo $data['category'];
		echo $data['rating'];
		echo $data['city'];
		echo $data['state'];
		echo $data['address'];
		echo $data['zipcode'];
		echo $data['hostname'];
		echo $data['contactnumber'];
		echo $data['email'];
		echo $data['startdateandtime'];
		echo $data['enddateandtime'];
		insertevent($data);
		
	    // insert($data);
		
    }
);

$app->post(
    '/events/insert/ticketscart/post/',function () use ($app){

        echo 'This is a POST route';
		
		$json=$app->request->getBody();
		$data=json_decode($json,true);
		echo $data['eventid'];
		echo $data['username'];
		echo $data['noofticketstaken'];
		 insertticketscart($data);
		
	  
		
    }
);
$app->post(
    '/events/insert/location/post/',function () use ($app){

        echo 'This is a POST route';
		
		$json=$app->request->getBody();
		$data=json_decode($json,true);
		echo $data['state'];
		echo $data['city'];
		echo $data['address'];
		echo $data['zipcode'];
		insertlocation($data);
		
		
	  
		
    }
);

$app->post(
    '/events/insert/hostinfo/post/',function () use ($app){

      //  echo 'Inserting hostinfo';
		
		$json=$app->request->getBody();
		$data=json_decode($json,true);
		//echo $data['hostname'];
		//echo $data['contactnumber'];
		//echo $data['email'];
	inserthost($data);
	    // insert($data);
		
    }
);

$app->post(
    '/events/saved/insert/post/',function () use ($app){

        echo 'This is a POST route';
		
		$json=$app->request->getBody();
		$data=json_decode($json,true);
		echo $data['EventId'];
		echo $data['username'];
		$conn=geteventDB();
        insertsavedevent($data);
	
	
/*$sql="DELETE FROM savedevents WHERE savedevents.eventid=$eventid and savedevents.username='$username'";
	if(!$result = $conn->query($sql)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	
	
	
	$conn->close();
		//DeleteSavedEvent($data);*/
		
	    
		
    }
);




$app->post(
    '/events/saved/delete/post/',function () use ($app){

        echo 'This is a POST route';
		
		$json=$app->request->getBody();
		$data=json_decode($json,true);
		echo $data['EventId'];
		echo $data['username'];
		$conn=geteventDB();
	$eventid=$data['EventId'];
	$username=$data['username'];
	
	
$sql="DELETE FROM savedevents WHERE savedevents.eventid=$eventid and savedevents.username='$username'";
	if(!$result = $conn->query($sql)){
		die('There was an error running the query [' .$conn->error . ']\n');
	}
	
	
	
	$conn->close();
		//DeleteSavedEvent($data);
		
	    
		
    }
);

// PUT route
$app->put(
    '/put',
    function () {
        echo 'This is a PUT route';
    }
);

// PATCH route
$app->patch('/patch', function () {
    echo 'This is a PATCH route';
});

// DELETE route
$app->delete(
    '/delete',
    function () {
        echo 'This is a DELETE route';
    }
);

/**
 * Step 4: Run the Slim application
 *
 * This method should be called last. This executes the Slim application
 * and returns the HTTP response to the HTTP client.
 */
$app->run();
