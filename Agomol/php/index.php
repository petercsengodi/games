<?php 

  header("Cache-Control: no-cache, must-revalidate");

  if(isset($_GET["action"]) && $_GET["action"] == "roll") {

    echo rand(1, 6);
    exit;

  } else {

    echo "v00.00.0001";
    exit;

  }

  include "db.php";

  
?>
