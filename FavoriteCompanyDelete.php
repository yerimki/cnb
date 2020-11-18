<?php
    $con = mysqli_connect("localhost", "cnb", "yerimk09!","cnb");
    mysqli_query($con,'SET NAMES utf8');

    $id = $_POST["id"];
    $name = $_POST["name"];

    $statement = mysqli_query($con, "DELETE FROM $id WHERE name = '".$name."'");

    $response = array();

    if($statement == false) {
        die("<pre>".mysqli_error($conn).PHP_EOL.$query."</pre>");
    }
    else{
        $response["success"] = true;
    }
    
    echo json_encode($response);

?>