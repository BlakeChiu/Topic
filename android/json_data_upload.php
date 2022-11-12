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
		
		try
		{
			//注意，使用PDO方式連結，需要指定一個資料庫，否則將拋出異常
			$conn = new PDO("mysql:host=localhost;dbname=android","admin","aefdu6359783");
			$conn -> exec("SET CHARACTER SET utf8");
			$conn -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
			$sql_ = $conn -> prepare("INSERT INTO products (status,address,name,phone,housetype,price,image) VALUES (0,?,?,?,?,?,?)");
			$sql_ -> execute(array($address, $name, $phone, $housetype, $price, $image));
			
			unset($conn);
            
            echo "suuuu";
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
