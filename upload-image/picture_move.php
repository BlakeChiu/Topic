<?php
ini_set('display_errors','1');
error_reporting(E_ALL);
class move_picture
{
	public function __construct()
	{
		//$input = file_get_contents('php://input'); 
		$tmp_filename = $_FILES['myupload']['tmp_name'];
		echo $tmp_filename.";";
		echo $_FILES['myupload']['name'].";";
		echo $_FILES['myupload']['tmp_name'].";";		
		//file_put_contents($picture_file, $input);
		//move_uploaded_file($tmp_filename,"/picture/{$tmp_filename}");
		if(!move_uploaded_file($_FILES['myupload']['tmp_name'],"uploads/{$_FILES['myupload']['name']}")) 
		{
			echo "An error has occurred moving the uploaded file.<BR>";
			echo "Please ensure that if safe_mode is on that the " . "UID PHP is using matches the file.";
			exit;
		} 
		else 
		{
			echo "The file has been successfully uploaded!";
		}
	}
	
}
if(isset($_FILES['myupload']))
{
	$controller = new move_picture();
}
else
{
    echo "123";
	exit;
}//practice
?>
