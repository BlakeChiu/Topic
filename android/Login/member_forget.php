<?php
/*
輸入:KEY=>action，VALUE=>{"name":"9","phone":"098888888"}
輸出:成功->{"password":"123"}
	失敗->{"result":"此電話或人名無註冊"}
*/
ini_set('display_errors','1');
error_reporting(E_ALL);

class json_data_upload
{
	public function __construct()
	{
		$act = $_POST['action'];
		$act_arr = json_decode($act);
		$name = $act_arr -> name;
		$phone = $act_arr -> phone;
		
		$conn = new PDO("mysql:host=localhost;dbname=android","admin","aefdu6359783");
		$conn -> exec("SET CHARACTER SET utf8");
		$conn -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
		try
		{
			$sql_ = $conn -> prepare("SELECT password FROM member where phone = ? and name = ?");
			$sql_ -> execute(array($phone, $name));
			
			$total_row = $sql_ -> rowCount();
			
			if($total_row == 0)
			{
				$json_display = ["result" => "此電話或人名無註冊"];
				$display = json_encode($json_display, JSON_UNESCAPED_UNICODE);
				echo $display;
				unset($conn); 
				exit;
			}
			
			
		}
		catch(PDOException $e)
		{
			$json_display = ["result" => "系統錯誤"];
			
			$display = json_encode($json_display, JSON_UNESCAPED_UNICODE);
			echo $display;
			
			exit;
		}
		
		$information_1 = $sql_ -> fetchAll(PDO::FETCH_NUM);
		
		$data_arr = array();
		
		foreach($information_1 as $rs)
		{
			$json_display = ["password" => $rs[0]];
			/*array_push($data_arr,$json_display);//used_qty
			$display = json_encode($data_arr, JSON_UNESCAPED_UNICODE);*/
			$display = json_encode($json_display, JSON_UNESCAPED_UNICODE);
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
