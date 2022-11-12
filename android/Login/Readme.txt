message_select.php
輸入:KEY=>action，VALUE=>{"products_id":"9"}
輸出:成功->{"message_pk":"1", "name":"大中天", "message":"123", "time":"2021-05-10 15:51:56"}
失敗->{"result":"系統錯誤"}

message_insert.php
輸入:KEY=>action，VALUE=>{"products_id":"9","name":"michael","message":"michael"}
輸出:成功->{"result":"ok"}
	失敗->{"result":"系統錯誤"}

member_register.php
輸入:KEY=>action，VALUE=>{"name":"9","phone":"098888888","password":"michael"}
輸出:成功->{"result":"ok"}
	失敗->{"result":"系統錯誤"}//系統錯誤
	失敗-已註冊->{"result":"此電話已註冊"}

member_login.php
輸入:KEY=>action，VALUE=>{"phone":"098888888","password":"michael"}
輸出:成功->{"name":"大中天"}
	失敗->{"result":"此電話無註冊"}
	失敗->{"result":"系統錯誤"}//系統錯誤

member_forget.php
輸入:KEY=>action，VALUE=>{"name":"9","phone":"098888888"}
輸出:成功->{"password":"123"}
	失敗->{"result":"此電話或人名無註冊"}








