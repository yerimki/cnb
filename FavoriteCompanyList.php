<?php
    $con = mysqli_connect("localhost", "cnb", "yerimk09!","cnb");
    mysqli_query($con,'SET NAMES utf8');

    $id = $_POST["id"];

    $statement = mysqli_query($con, "SELECT DISTINCT * FROM $id");

    if($statement == false) {
      die("<pre>".mysqli_error($conn).PHP_EOL.$query."</pre>");
    }

    $response = array();

    while($row = mysqli_fetch_row($statement)){
        $response["local"]=$row[0];
        $response["name"]=$row[1];
        $response["content"]=$row[2];
        $response["type"]=$row[3];
        $response["category"]=$row[4];
        $response["owner"]=$row[5];
        $response["pnum"]=$row[6];
        $response["address"]=$row[7];
        $response["cite"]=$row[8];
        echo json_encode($response);
        echo '<br />';
}

?>