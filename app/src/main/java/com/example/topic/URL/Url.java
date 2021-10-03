package com.example.topic.URL;

public class Url {

    private String LoginBase = "http://192.168.64.2/android/Login/";

    private String InfoBase = "http://192.168.64.2/android/";

    private String ImageBase = "http://192.168.64.2/upload-image/";

    private String MessageBase = "http://192.168.64.2/android/Message/";

    public String urlSingIn = LoginBase + "member_login.php";

    public String urlSingUp = LoginBase + "member_register.php";

    public String urlGetPwd = LoginBase + "member_forget.php";

    public String urlSightseeingSelect = InfoBase + "json_data_select_sightseeing.php";

    public String urlDataDelete = InfoBase + "json_data_delete.php";

    public String urlDataSelect = InfoBase + "json_data_select.php";

    public String urlDataUpload = InfoBase + "json_data_upload.php";

    public String urlDataUpdate = InfoBase + "json_data_update.php";

    public String urlGetImage = ImageBase + "uploads/";

    public String urlMoveImage = ImageBase + "picture_move.php";

    public String urlMessageGet = MessageBase + "message_select.php";

    public String urlMessageSet = MessageBase + "message_insert.php";
}
