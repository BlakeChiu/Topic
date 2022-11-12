<?php
ini_set('display_errors','1');
error_reporting(E_ALL);

class json_data_upload
{
	public function __construct()
	{
		$act = $_POST['action'];
		$act_arr = json_decode($act);
		$address = $act_arr -> address;
		$name = $act_arr -> name;
		$phone = $act_arr -> phone;
        $housetype = $act_arr -> housetype;
        $price = $act_arr -> price;
		$image = $act_arr -> image;
		$id = $act_arr -> id;
		
		try
		{
			//注意，使用PDO方式連結，需要指定一個資料庫，否則將拋出異常
			//$conn = new PDO("mysql:host=localhost;dbname=YCT_test","admin","aefdu6359783");//測試
			$conn = new PDO("mysql:host=localhost;dbname=android","admin","aefdu6359783");
			$conn -> exec("SET CHARACTER SET utf8");
			$conn -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
			
			if($image == null || $image == "")
			{
				$sql_ = $conn -> prepare("update products set address = ?,name = ?,phone = ? ,housetype = ? ,price = ? where id = ?");
				$sql_ -> execute(array($address, $name, $phone, $housetype , $price, $id));
			}
			else
			{
				$sql_ = $conn -> prepare("update products set address = ?,name = ?,phone = ?, housetype = ? ,price = ? image = ? where id = ?");
				$sql_ -> execute(array($address, $name, $phone, $housetype , $price, $image, $id));
			}
			
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
