<?php
if ($_SERVER['REQUEST_METHOD'] != "POST"){
  die();
}

$Data = getallheaders();

$Database_Info = [
  "Host" => "localhost",
  "User" => "u254629197_JoaoFabioFCT",
  "Password" => "BE?J=Mmn",
  "DatabaseName" => "u254629197_JoaoFabioFCT"
];

$Options = [
  "cost" => 12,
];


$AuthToken = substr(md5(openssl_random_pseudo_bytes(20)),-32);

$DataNasc = $Data["DataNascimento"];
$Email = getallheaders()["Email"];
$Database = new mysqli($Database_Info["Host"],$Database_Info["User"],$Database_Info["Password"],$Database_Info["DatabaseName"]);
$EncPassword = password_hash($Data["Password"],PASSWORD_BCRYPT,$Options);
$AuthTokenEnc = password_hash($AuthToken,PASSWORD_BCRYPT,$Options);
$Sql = "INSERT INTO `Contas` VALUES(null,'".$Data["Name"]."','".$Email."','".$DataNasc."','".$Data["Sex"]."','".$EncPassword."','".$AuthToken."');";
$SqlResult = $Database -> query($Sql);

if ($SqlResult == "1"){
  die('
  {
    "Result":{
      "Email":"'.getallheaders()["Email"].'",
      "ResultCode":10,
      "RsultType":"Sucess",
      "ResultDesc":"Register Done",
      "AuthToken":"'.$AuthTokenEnc.'",
      "Data":"'.$DataNasc.'",
      "Sql":"'.$Sql.'"
    }
  }
  ');
}else{

  if (str_replace("Duplicate","",$Database->error)){
    die('
    {
      "Result":{
        "Email":"'.getallheaders()["Email"].'",
        "ResultCode":7,
        "RsultType":"Error",
        "ResultDesc":"No Register Done",
        "AuthToken":""
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
        "ResultDesc":"No Register Done",
        "AuthToken":""
      }
    }
    ');
  }


}



?>
