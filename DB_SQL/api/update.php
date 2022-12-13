<?php
    $con = mysqli_connect("localhost", "safelab_user", "1234", "SafeLabDB");
    mysqli_query($con,'SET NAMES utf8');

    $current_pw = isset($_POST["current_pw"]) ? $_POST["current_pw"] : "";
    $new_pw = isset($_POST["new_pw"]) ? $_POST["new_pw"] : "";

    $user_id = isset($_POST["user_id"]) ? $_POST["user_id"] : "";

    $update_stmt = mysqli_prepare($con, "UPDATE user SET pw = ? WHERE id = ? AND pw = ?");
    mysqli_stmt_bind_param($update_stmt, "sss", $new_pw, $user_id, $current_pw);
    mysqli_stmt_execute($update_stmt);

    $response = array();
    if (mysqli_affected_rows($con) > 0) {
        $response["success"] = true;
    } else {
        $response["success"] = false;
    }

    echo json_encode($response);

    mysqli_close($con);
?>
