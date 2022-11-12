<?php
/*
輸入:KEY=>action，VALUE=>{"products_id":"9"}
輸出:成功->{"message_pk":"1", "name":"大中天", "message":"123", "time":"2021-05-10 15:51:56"}
無留言->{"result":"無留言"}
失敗->{"result":"系統錯誤"}
*/
ini_set('display_errors','1');
error_reporting(E_ALL);

class json_data_upload
{
	public function __construct()
	{
		$act = $_POST['action'];
		$act_arr = json_decode($act);
		$products_id = $act_arr -> products_id;//0=all,1=name,2=housetype,3=both
		$data_arr = array();
		$conn = new PDO("mysql:host=localhost;dbname=android","admin","aefdu6359783");
		$conn -> exec("SET CHARACTER SET utf8");
		$conn -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
		try
		{
			$sql_ = $conn -> prepare("SELECT message_pk,name,message,time FROM message_board where products_id = ?");
			$sql_ -> execute(array($products_id));
		}
		catch(PDOException $e)
		{
			$json_display = ["result" => "系統錯誤"];
			array_push($data_arr,$json_display);//used_qty
			$display = json_encode($data_arr, JSON_UNESCAPED_UNICODE);
			echo $display;
			
			exit;
		}
		
		$total_row = $sql_ -> rowCount();
		
		if($total_row == 0)
		{
			$json_display = ["result" => "無留言"];
			array_push($data_arr,$json_display);//used_qty
			$display = json_encode($data_arr, JSON_UNESCAPED_UNICODE);
			echo $display;
			
			exit;
		}
		
		$information_1 = $sql_ -> fetchAll(PDO::FETCH_NUM);
		
		
		
		foreach($information_1 as $rs)
		{
			$json_display = ["message_pk" => $rs[0], "name" => $rs[1], "message" => $rs[2], "time" => $rs[3]];
			array_push($data_arr,$json_display);//used_qty
			$display = json_encode($data_arr, JSON_UNESCAPED_UNICODE);
		}
		
		echo $display;
		
		unset($conn);
		//$json_display = ["id" => $id_arr, "address" => $address_arr, "name" => $name_arr, "phone" => $phone_arr, "housetype" => $housetype_arr, "price" => $price_arr, "image" => $image_arr];
		//$display = json_encode($json_display, JSON_UNESCAPED_UNICODE);
		 
		
		/*try
		{
			//注意，使用PDO方式連結，需要指定一個資料庫，否則將拋出異常
			$conn = new PDO("mysql:host=localhost;dbname=YCT_test","ma430104","75147514");
			//$conn = new PDO("mysql:host=127.0.0.1;dbname=PM2.5_test","ma430104","75147514");
			$conn -> exec("SET CHARACTER SET utf8");
			$conn -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
			$sql_ = $conn -> prepare("SELECT * FROM products where id = ?");
			$sql_ -> execute(array($id));
			$information_1 = $sql_ -> fetchAll(PDO::FETCH_NUM);
			
			$json_display = ["report" => "1", "report_type" => "2", "display" => $message];
			$display = json_encode($json_display, JSON_UNESCAPED_UNICODE);
			echo $display;
			
			unset($conn); 
		}
		catch(PDOException $e)
		{
			echo $e;
			exit;
		}*/
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
