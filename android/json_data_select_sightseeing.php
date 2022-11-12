<?php
ini_set('display_errors','1');
error_reporting(E_ALL);

class json_data_upload
{
	public function __construct()
	{
		$act = $_POST['action'];
		$act_arr = json_decode($act);
		//$select_type = $act_arr -> select_type;//0=all,1=name,2=housetype,3=both
		//$item = "'%".$act_arr -> item."%'";
		//$title = "'%".$act_arr -> title."%'";
		//$name = "'%".$act_arr -> name."%'";
		$address = "'%".$act_arr -> address."%'";
		//$phone = "'%".$act_arr -> phone."%'";
		//$introduce = "'%".$act_arr -> introduce."%'";
		
		$conn = new PDO("mysql:host=localhost;dbname=android","admin","aefdu6359783");
		//$conn = new PDO("mysql:host=127.0.0.1;dbname=YCT_test","ma430104","75147514");//測試
		$conn -> exec("SET CHARACTER SET utf8");
		$conn -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		//$sql_ = $conn -> prepare("SELECT * FROM sightseeing where item like $item and title like $title and name like $name and address like $address and phone like $phone and introduce like $introduce");
		$sql_ = $conn -> prepare("SELECT * FROM sightseeing where address like $address");
		$sql_ -> execute();
		$total_row = $sql_ -> rowCount();
				
		if($total_row == 0)
		{
			$json_display = ["id" => "null", "status" => "null", "item" => "null", "title" => "null", "image" => "null", "address" => "null", "introduce" => "null"];
			array_push($data_arr,$json_display);//used_qty
			$display = json_encode($data_arr, JSON_UNESCAPED_UNICODE);
			
			echo $display;
		
			unset($conn);
			
			exit;
		}
		$information_1 = $sql_ -> fetchAll(PDO::FETCH_NUM);
		
		$id_arr = array();
		$address_arr = array();
		$name_arr = array();
		$phone_arr = array();
		$housetype_arr = array();
		$price_arr = array();
		$image_arr = array();
		$data_arr = array();
		
		foreach($information_1 as $rs)
		{
			$json_display = ["id" => $rs[0], "status" => $rs[1], "item" => $rs[2], "title" => $rs[3], "image" => $rs[4], "address" => $rs[5], "introduce" => $rs[6]];
			array_push($data_arr,$json_display);//used_qty
			$display = json_encode($data_arr, JSON_UNESCAPED_UNICODE);
		}
		
		echo $display;
		
		unset($conn);
	}
	
}
if(isset($_POST['action']))
{
	$controller = new json_data_upload();
}
else
{
	exit;
}//practice
?>
