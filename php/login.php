<?php
    $con = mysqli_connect("localhost", "safelab_user", "1234", "SafeLabDB");
    mysqli_query($con,'SET NAMES utf8');

    $id = isset($_POST["id"]) ? $_POST["id"] : "";
    $pw = isset($_POST["pw"]) ? $_POST["pw"] : "";
    
    $statement = mysqli_prepare($con, "SELECT * FROM user WHERE id = ? AND pw = ?");
    mysqli_stmt_bind_param($statement, "ss", $id, $pw);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $id, $pw, $name, $email, $depart);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["id"] = $id;
        $response["pw"] = $pw;
        $response["name"] = $name;
        $response["email"] = $email;
    }

    echo json_encode($response);

    mysqli_close($con);
?>