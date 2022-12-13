<?php 
    $con = mysqli_connect("localhost", "safelab_user", "1234", "SafeLabDB");
    mysqli_query($con,'SET NAMES utf8');

    $id = isset($_POST["id"]) ? $_POST["id"] : "";
    $pw = isset($_POST["pw"]) ? $_POST["pw"] : "";
    $name = isset($_POST["name"]) ? $_POST["name"] : "";
    $email = isset($_POST["email"]) ? $_POST["email"] : "";
    $depart = isset($_POST["depart"]) ? $_POST["depart"] : "";

    // Check if the id already exists in the database
    $check_id_query = mysqli_query($con, "SELECT * FROM user WHERE id='$id'");
    if (mysqli_num_rows($check_id_query) > 0) {
        // The id already exists
        $response = array();
        $response["success"] = false;
        echo json_encode($response);
        return;
    }

    // If we reach this point, the id does not exist yet and we can insert the data
    $statement = mysqli_prepare($con, "INSERT INTO user VALUES (?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssss", $id, $pw, $name, $email, $depart);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);

    mysqli_close($con);
?>
