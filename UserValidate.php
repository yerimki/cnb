<?php
    $con = mysqli_connect("localhost", "cnb", "yerimk09!", "cnb");

    $id = $_POST["id"];

    $statement = mysqli_prepare($con, "SELECT id FROM member WHERE id = ?");
    mysqli_stmt_bind_param($statement, "s", $id);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $id);


    $response = array();
    $response["success"] = true;

    while(mysqli_stmt_fetch($statement)){
        $response["success"]=false;
        $response["id"]=$id;
    }

    echo json_encode($response);



?>
