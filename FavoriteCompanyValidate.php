<?php
    $con = mysqli_connect("localhost", "cnb", "yerimk09!", "cnb");

    $id = $_POST["id"];
    $name = $_POST["name"];

    $statement = mysqli_prepare($con, "SELECT name FROM $id WHERE name = ?");
    mysqli_stmt_bind_param($statement, "s", $name);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $name);

    $response = array();
    $response["success"] = true;

    while(mysqli_stmt_fetch($statement)){
        $response["success"]=false;
        $response["name"]=$name;
    }

    echo json_encode($response);
?>