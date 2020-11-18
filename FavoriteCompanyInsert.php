<?php
    $con = mysqli_connect("localhost", "cnb", "yerimk09!","cnb");
    mysqli_query($con,'SET NAMES utf8');

    $id = $_POST["id"];
    $local = $_POST["local"];
    $name = $_POST["name"];
    $content = $_POST["content"];
    $type = $_POST["type"];
    $category = $_POST["category"];
    $owner = $_POST["owner"];
    $pnum = $_POST["pnum"];
    $address = $_POST["address"];
    $cite = $_POST["cite"];

    mysqli_query($con,"CREATE TABLE IF NOT EXISTS $id (local TEXT, name TEXT, content TEXT, type TEXT, category TEXT, owner TEXT, pnum TEXT, address TEXT, cite TEXT)");

    mysqli_query($con,"INSERT INTO $id VALUES ('".$local."','".$name."', '".$content."', '".$type."', '".$category."', '".$owner."', '".$pnum."', '".$address."', '".$cite."')");

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>