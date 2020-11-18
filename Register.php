<?php
    $con = mysqli_connect("localhost", "cnb", "yerimk09!","cnb");
    mysqli_query($con,'SET NAMES utf8');

    $id = $_POST["id"];
    $pw = $_POST["pw"];
    $name = $_POST["name"];
    $pnum = $_POST["pnum"];

    $statement = mysqli_prepare($con, "INSERT INTO member VALUES (?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssss", $id, $pw, $name, $pnum);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;


    echo json_encode($response);



?>
