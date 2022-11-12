<?php
/*
輸入:KEY=>action，VALUE=>{"products_id":"9","name":"michael","message":"michael"}
輸出:成功->{"result":"ok"}
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
		$products_id = $act_arr -> products_id;
		$name = $act_arr -> name;
		$message = $act_arr -> message;
		
		try
		{
			//注意，使用PDO方式連結，需要指定一個資料庫，否則將拋出異常
			$conn = new PDO("mysql:host=localhost;dbname=android","admin","aefdu6359783");
			$conn -> exec("SET CHARACTER SET utf8");
			$conn -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
			
			$sql_ = $conn -> prepare("SELECT message_pk FROM message_board where products_id = ? order by message_pk DESC");
			$sql_ -> execute(array($products_id));
			$total_row = $sql_ -> rowCount();
			
			if($total_row == 0)
			{
				
				$message_pk = 1;
			}
			else
			{
				$rs = $sql_ -> fetchAll(PDO::FETCH_NUM);
				$message_pk = $rs[0][0] + 1;
			}
			
			$sql_ = $conn -> prepare("INSERT INTO message_board (products_id,message_pk,name,message) VALUES (?,?,?,?)");
			$sql_ -> execute(array($products_id, $message_pk, $name, $message));
			
			unset($conn); 
			
			$json_display = ["result" => "ok"];
			
			$display = json_encode($json_display, JSON_UNESCAPED_UNICODE);
			echo $display;
		}
		catch(PDOException $e)
		{
			//echo $e;
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
}//practice
?>
