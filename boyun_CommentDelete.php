<?php
    $con = mysqli_connect('localhost', 'cnb', 'yerimk09!','cnb');
    mysqli_query($con,'SET NAMES utf8');

    $num = $_POST["num"];

    //mysqli_query($con,"CREATE TABLE IF NOT EXISTS comment_list (num TEXT PRIMARY KEY, id TEXT, review TEXT)");

    mysqli_query($con,"DELETE FROM comment_list WHERE num = '$num';");

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
