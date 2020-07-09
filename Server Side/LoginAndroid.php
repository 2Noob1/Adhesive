<?php

  if ($_SERVER['REQUEST_METHOD'] != "POST"){
    die();
  }



  $Database_Info = [
    "Host" => "localhost",
    "User" => "u254629197_JoaoFabioFCT",
    "Password" => "BE?J=Mmn",
    "DatabaseName" => "u254629197_JoaoFabioFCT"
  ];

  $Database = new mysqli($Database_Info["Host"],$Database_Info["User"],$Database_Info["Password"],$Database_Info["DatabaseName"]);

  $Sql = 'SELECT * FROM `Contas` WHERE `Email`="'.getallheaders()["Email"].'";';
  $SqlResult = $Database -> query($Sql);

  if ($SqlResult -> num_rows == 0){
    die('
      {
        "Result":{
          "Email":"'.getallheaders()["Email"].'",
          "ResultCode":8,
          "RsultType":"Error",
          "ResultDesc":"Conta Não Encontrada",
          "AuthToken":""
        }
      }
    ');
  }else{
    while($Row = $SqlResult->fetch_assoc()){

      if (password_verify(getallheaders()["Password"],$Row["Password"])){
        $Options = [
          "cost" => 12,
        ];
        $AuthToken = substr(md5(openssl_random_pseudo_bytes(20)),-32);
        $SqlUpdate = 'UPDATE `Contas` SET `Auth`="'.$AuthToken.'" where `Email` = "'.getallheaders()["Email"].'";';
        $NewResult = $Database->query($SqlUpdate);
        if ($NewResult == "1"){
          $AuthTokenEnc = password_hash($AuthToken,PASSWORD_BCRYPT,$Options);
          die('
            {
              "Result":{
                "Email":"'.getallheaders()["Email"].'",
                "ResultCode":9,
                "RsultType":"Sucesso",
                "ResultDesc":"Login Com Sucesso",
                "AuthToken":"'.$AuthTokenEnc.'"
              }
            }
          ');
        }else{
          die('
            {
              "Result":{
                "Email":"'.getallheaders()["Email"].'",
                "ResultCode":3,
                "RsultType":"Error",
                "ResultDesc":"Erro de servidor",
                "AuthToken":""
              }
            }
          ');
        }
      }else{
        die('
          {
            "Result":{
              "Email":"'.getallheaders()["Email"].'",
              "ResultCode":6,
              "RsultType":"Error",
              "ResultDesc":"Password Inválida",
              "AuthToken":""
            }
          }
        ');
      }
    }
  }


?>
