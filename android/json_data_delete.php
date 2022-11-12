<?php
ini_set('display_errors','1');
error_reporting(E_ALL);

class json_data_upload
{
	public function __construct()
	{
		$act = $_POST['action'];
		$act_arr = json_decode($act);
		$id = $act_arr -> id;
		
		try
		{
			//注意，使用PDO方式連結，需要指定一個資料庫，否則將拋出異常
			//$conn = new PDO("mysql:host=localhost;dbname=YCT_test","ma430104","75147514");//測試
			$conn = new PDO("mysql:host=localhost;dbname=android","admin","aefdu6359783");
			$conn -> exec("SET CHARACTER SET utf8");
			$conn -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
			$sql_ = $conn -> prepare("DELETE FROM products where id = ?");
			$sql_ -> execute(array($id));
			
			unset($conn); 
		}
		catch(PDOException $e)
		{
			echo $e;
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
