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
          if (mail("fabiopicadosousa@gmail.com",getallheaders()["Email"]." - ".getallheaders()["Assunto"],getallheaders()["Mensagem"])){
            die("valid");
          }else{
            die("error");
          }
      }else{
        echo('invalid');
        $Sql = 'UPDATE `Contas` SET `Auth` = NULL WHERE `Email` = "'.getallheaders()["Email"].'";';
        $SqlResult = $Database -> query($Sql);
        die();
      }
    }
  }


?>
