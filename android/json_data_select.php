<?php
ini_set('display_errors','1');
error_reporting(E_ALL);

class json_data_upload
{
	public function __construct()
	{
		$act = $_POST['action'];
		$act_arr = json_decode($act);
		$select_type = $act_arr -> select_type;//0=all,1=name,2=housetype,3=both
		
		//$conn = new PDO("mysql:host=localhost;dbname=YCT_test","ma430104","75147514");//測試
		$conn = new PDO("mysql:host=localhost;dbname=android","admin","aefdu6359783");
		$conn -> exec("SET CHARACTER SET utf8");
		$conn -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
		if($select_type == "0")
		{
			try
			{
				$sql_ = $conn -> prepare("SELECT * FROM products");
				$sql_ -> execute();
			}
			catch(PDOException $e)
			{
				echo $e;
				exit;
			}
			
		}
		else if($select_type == "1")
		{
			$name = $act_arr -> name;
			
			try
			{
				$sql_ = $conn -> prepare("SELECT * FROM products where name = ?");
				$sql_ -> execute(array($name));
			}
			catch(PDOException $e)
			{
				echo $e;
				exit;
			}
			
		}
		else if($select_type == "2")
		{
			$housetype = $act_arr -> housetype;
			
			try
			{
				$sql_ = $conn -> prepare("SELECT * FROM products where housetype = ?");
				$sql_ -> execute(array($housetype));
			}
			catch(PDOException $e)
			{
				echo $e;
				exit;
			}
			
		}
		else if($select_type == "3")
		{
			$name = $act_arr -> name;
			$housetype = $act_arr -> housetype;
			
			try
			{
				$sql_ = $conn -> prepare("SELECT * FROM products where name = ? and housetype = ? ");
				$sql_ -> execute(array($name, $housetype));
			}
			catch(PDOException $e)
			{
				echo $e;
				exit;
			}
			
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
			$json_display = ["id" => $rs[0], "address" => $rs[2], "name" => $rs[3], "phone" => $rs[4], "housetype" => $rs[5], "price" => $rs[6], "image" => $rs[7], "status" => $rs[1]];
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
