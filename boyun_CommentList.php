<?php
    $con = mysqli_connect('localhost', 'cnb', 'yerimk09!','cnb');
    mysqli_query($con,'SET NAMES utf8');

    $company = $_POST["company"];

    //mysqli_query($con,"CREATE TABLE IF NOT EXISTS $company (num TEXT PRIMARY KEY, id TEXT, review TEXT)");

    $statement = mysqli_query($con, "SELECT * FROM comment_list WHERE company = '$company' ORDER BY `num` ASC;");

    if($statement == false) {
      die("<pre>".mysqli_error($conn).PHP_EOL.$query."</pre>");
    }

    $response = array();

    while($row = mysqli_fetch_row($statement)){
        $response["num"]=$row[0];
        $response["id"]=$row[2];
        $response["review"]=$row[3];
        echo json_encode($response);
        echo '<br />';
  }

  //mysql_close($con);

?>
