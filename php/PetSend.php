<?php 
    $con = mysqli_connect("localhost", "ID", "PASSWORD", "casio2978");
    mysqli_query($con,'SET NAMES utf8');

    $temp = $_POST["temp"];
    $humidity = $_POST["humidity"];
    $day = $_POST["day"];
    $time = $_POST["time"];

    $statement = mysqli_prepare($con, "INSERT INTO pet VALUES (?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssss", $temp, $humidity, $day, $time);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);
?>
