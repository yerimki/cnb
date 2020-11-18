<?php
    $con = mysqli_connect('localhost', 'cnb', 'yerimk09!','cnb');
    mysqli_query($con,'SET NAMES utf8');

    $company = $_POST["company"];
    $num = $_POST["num"];
    $id = $_POST["id"];
    $review = $_POST["review"];

    //mysqli_query($con,"CREATE TABLE IF NOT EXISTS $company (num TEXT PRIMARY KEY, id TEXT, review TEXT)");

    mysqli_query($con,"INSERT INTO comment_list VALUES ('".$num."','".$company."','".$id."', '".$review."')");

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
