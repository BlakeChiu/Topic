<?php
/*
輸入:KEY=>action，VALUE=>{"name":"9","phone":"098888888","password":"michael"}
輸出:成功->{"result":"ok"}
	失敗->{"result":"系統錯誤"}//系統錯誤
	失敗-已註冊->{"result":"此電話已註冊"}
*/

/*ini_set('display_errors','1');
error_reporting(E_ALL);*/

class json_data_upload
{
	public function __construct()
	{
		$act = $_POST['action'];
		$act_arr = json_decode($act);
		$phone = $act_arr -> phone;
		$name = $act_arr -> name;
		$password = $act_arr -> password;
		
		try
		{
			//注意，使用PDO方式連結，需要指定一個資料庫，否則將拋出異常
			$conn = new PDO("mysql:host=localhost;dbname=android","admin","aefdu6359783");
			$conn -> exec("SET CHARACTER SET utf8");
			$conn -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
			
			$sql_ = $conn -> prepare("SELECT name FROM member where phone = ? ");
			$sql_ -> execute(array($phone));
			$total_row = $sql_ -> rowCount();
			
			if($total_row > 0)
			{
				$json_display = ["result" => "此電話已註冊"];
				unset($conn); 
				exit;
			}
			
			$sql_ = $conn -> prepare("INSERT INTO member (name,phone,password) VALUES (?,?,?)");
			$sql_ -> execute(array($name, $phone, $password));
			unset($conn); 
			
			$json_display = ["result" => "ok"];
			
			$display = json_encode($json_display, JSON_UNESCAPED_UNICODE);
			echo $display;
		}
		catch(PDOException $e)
		{
			$json_display = ["result" => "系統錯誤"];
			
			$display = json_encode($json_display, JSON_UNESCAPED_UNICODE);
			echo $display;
			
			exit;
		}
	}
	
}
if(isset($_POST['action']))
{
	$controller = new json_data_upload();
}
else
{
	exit;
}
