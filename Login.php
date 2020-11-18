<?php
    $con = mysqli_connect("localhost", "cnb", "yerimk09!","cnb");
    mysqli_query($con,'SET NAMES utf8');

    $id = $_POST["id"];
    $pw = $_POST["pw"];
    
    echo $id;
    echo $pw;

    $statement = mysqli_prepare($con, "SELECT * FROM member WHERE id = ? AND pw = ?");
    mysqli_stmt_bind_param($statement, "ss", $id, $pw);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $id, $pw, $name, $pnum);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["id"] = $id;
        $response["pw"] = $pw;
        $response["name"] = $name;
        $response["pnum"] = $pnum;
    }

    echo json_encode($response);

?>
