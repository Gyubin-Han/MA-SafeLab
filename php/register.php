<?php 
    $con = mysqli_connect("localhost", "safelab_user", "1234", "SafeLabDB");
    mysqli_query($con,'SET NAMES utf8');

    $id = isset($_POST["id"]) ? $_POST["id"] : "";
    $pw = isset($_POST["pw"]) ? $_POST["pw"] : "";
    $name = isset($_POST["name"]) ? $_POST["name"] : "";
    $email = isset($_POST["email"]) ? $_POST["email"] : "";
    $depart = isset($_POST["depart"]) ? $_POST["depart"] : "";

    $statement = mysqli_prepare($con, "INSERT INTO user VALUES (?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssss", $id, $pw, $name, $email, $depart);
    mysqli_stmt_execute($statement);

    
    $response = array();
    $response["success"] = true;

    echo json_encode($response);

    mysqli_close($con);

?>