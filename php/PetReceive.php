<?php
    $con = mysqli_connect("localhost", "casio2978", "qkralsdn1!", "casio2978");

    if (mysqli_connect_errno($con)) { 
        echo "MySQL접속 실패: " . mysqli_connect_error();
    } 

    mysqli_set_charset($con,"utf8");

    $res = mysqli_query($con, "select * from pet"); 
    $result = array(); 
    while($row = mysqli_fetch_array($res)) { 
        array_push($result, array('temp'=>$row[0],'humidity'=>$row[1],'day'=>$row[2],'time'=>$row[3])); 
    } 
    // 배열형식의 결과를 json으로 변환 
    echo json_encode(array("result"=>$result),JSON_UNESCAPED_UNICODE); 
    // DB 접속 종료 
    mysqli_close($con); 

?>