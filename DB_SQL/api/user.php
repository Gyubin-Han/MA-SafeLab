<?php

session_start();

if(!empty($_GET['reset']) && $_GET['reset']=='y'){
	session_unset();
	return;
}

$con = mysqli_connect('localhost:3310','safelab_user','1234','SafeLabDB');

$result=array();

if(empty($_SESSION['id'])){
	$_SESSION['id']=1;
	$query_result=mysqli_query($con,"SELECT * FROM user");
	while($row=mysqli_fetch_assoc($query_result)){
		array_push($result,array('id'=>$row['id']."(1)",'name'=>$row['name'],'depart'=>$row['depart'],'email'=>$row['email']));
	}
}

$query_result=mysqli_query($con,"SELECT * FROM user");
if(empty($_POST)){
	while($row=mysqli_fetch_assoc($query_result)){
		array_push($result,array('id'=>$row['id'],'name'=>$row['name'],'depart'=>$row['depart'],'email'=>$row['email']));
	}
}




//while($row=mysqli_fetch_assoc($query_result)){
//	if(strcmp($_POST['id'],$row['id'])===0){
//		array_push($result,array('id'=>$row['id'],'name'=>$row['name'],'depart'=>$row['depart'],'email'=>$row['email']));
//		break;
//	}
//}

echo json_encode(array("result"=>$result));

mysqli_close($con);

?>