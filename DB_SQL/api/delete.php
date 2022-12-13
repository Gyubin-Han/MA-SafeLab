<?php
    $con = mysqli_connect("localhost", "safelab_user", "1234", "SafeLabDB");
    mysqli_query($con,'SET NAMES utf8');

    $user_id = isset($_POST["user_id"]) ? $_POST["user_id"] : "";
    $current_pw = isset($_POST["current_pw"]) ? $_POST["current_pw"] : "";

    $check_stmt = mysqli_prepare($con, "SELECT * FROM user WHERE id = ? AND pw = ?");
    mysqli_stmt_bind_param($check_stmt, "ss", $user_id, $current_pw);
    mysqli_stmt_execute($check_stmt);
    mysqli_stmt_store_result($check_stmt);

    $response = array();
    if (mysqli_stmt_num_rows($check_stmt) > 0) {
        $delete_stmt = mysqli_prepare($con, "DELETE FROM user WHERE id = ?");
        mysqli_stmt_bind_param($delete_stmt, "s", $user_id);
        mysqli_stmt_execute($delete_stmt);

        if (mysqli_affected_rows($con) > 0) {
            $response["success"] = true;
        } else {
            $response["success"] = false;
        }
    } else {
        $response["success"] = false;
    }

    echo json_encode($response);

    mysqli_close($con);
?>
