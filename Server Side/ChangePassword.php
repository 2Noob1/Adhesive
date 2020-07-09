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
    die('invalid');
  }else{
    while($Row = $SqlResult->fetch_assoc()){
      if (password_verify($Row["Auth"],getallheaders()["Key"])){
        $options = [
          "cost" => 12,
        ];
        $PasswordHased = password_hash(getallheaders()["Password"],PASSWORD_BCRYPT,$options);
        $Sql = 'UPDATE `Contas` SET `Password` = "'.$PasswordHased.'" where `Email` = "'.getallheaders()["Email"].'";';
        $DatabaseResult = $Database->query($Sql);
        if($DatabaseResult == "1"){
          die("valid");
        }else{
          die("error");
        }
      }else{
        echo('invalid');
        $Sql = 'Update `Contas` set `Auth` = NULL WHERE `Email` = "'.getallheaders()["Email"].'";';
        $SqlResult = $Database -> query($Sql);
        die();
      }
    }
  }


?>
