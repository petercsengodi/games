<?php

  global $LOCAL;

  $LOCAL["conn"] = false;
  
  // try to connect public server
  $LOCAL["conn"] = mysql_connect("host", "account", "password");
  
  if($LOCAL["conn"]){

    // try to select database 
    mysql_select_db("worstofcsega_rpg", $LOCAL["conn"]);

  } else {
  
    // try to connect local server
    $LOCAL["conn"] = mysql_connect("localhost", "root", "csegajol");

    if(!$LOCAL["conn"]){
      die ("Could not connect database<br/>\n");
    }

    // try to select database 
    mysql_select_db("rpg", $LOCAL["conn"]);
  }

  $error_message = mysql_error(); 
  if($error_message) { 
    mysql_close($LOCAL["conn"]); 
    $LOCAL["conn"] = false;
    die ("Can not connect to database:<br/>($error_message)<br/>\n"); 
  }

  mysql_query("SET NAMES utf8", $LOCAL["conn"]);
  mysql_query("SET CHARACTER SET utf8", $LOCAL["conn"]);

  function safeQuery(){
    global $LOCAL;

    $numargs = func_num_args();
    if($numargs < 1){
      die("Query argument is missing!");
    }
    
    $arg_list = func_get_args();
    $query_string = $arg_list[0];
    
    $matches = array();
    preg_match_all("/{[0-9]+}/", $query_string, $matches, PREG_OFFSET_CAPTURE);
    
    $query = "";
    $last_pos = 0;
    
    // insert query arguments
    foreach($matches as $subarray){
      foreach($subarray as $param){
      
        // cut until next expression
        $index = $param[1];
        if($last_pos < $index){
          $query .= substr($query_string, $last_pos, $index - $last_pos);
        }
        
        // get argument index
        $expression = $param[0];
        $arg = intval(substr($expression, 1, strlen($expression) - 2)) + 1;
        if($arg >= $numargs){
          die("Query has missing arguments! ($queryString)");
        }
        
        // safety transform of argument value
        $value = $arg_list[$arg];
        $answer = "null";
        if($value === false){
          $answer = "'0'";
        } else if($value === true){
          $answer = "'1'";
        } else if($value !== NULL) {
          $answer = "'" . str_replace(array("\\", "'"), array("\\\\", "\\'"), strval($value)) . "'";
        }
        
        // add argument to query
        $query .= $answer;
        
        // updateing last position
        $last_pos = $index + strlen($expression);
        
      } // end foreach $param
    } // end foreach 
    
    // add end of query
    $len = strlen($query_string);
    if($last_pos < $len){
      $query .= substr($query_string, $last_pos);
    }

    $reply = mysql_query($query, $LOCAL["conn"]);
    
    // report error if exists
    $error_message = mysql_error();
    if($error_message) { 
      die("Error in query:<br/>\n($error_message)<br/>\n$query"); 
    }
    
    return $reply;
  }
  
  function safeSql($filename) {
    // temporary solution: double code
  
    global $LOCAL;

    $numargs = func_num_args();
    if($numargs < 1){
      die ("SQL file argument is missing!");
    }
    
    $arg_list = func_get_args();
    $query_string = file_get_contents("sql/" . $arg_list[0] . ".sql");
    
    $matches = array();
    preg_match_all("/{[0-9]+}/", $query_string, $matches, PREG_OFFSET_CAPTURE);
    
    $query = "";
    $last_pos = 0;
    
    // insert query arguments
    foreach($matches as $subarray){
      foreach($subarray as $param){
      
        // cut until next expression
        $index = $param[1];
        if($last_pos < $index){
          $query .= substr($query_string, $last_pos, $index - $last_pos);
        }
        
        // get argument index
        $expression = $param[0];
        $arg = intval(substr($expression, 1, strlen($expression) - 2)) + 1;
        if($arg >= $numargs){
          die ("Query has missing arguments! ($queryString)");
        }
        
        // safety transform of argument value
        $value = $arg_list[$arg];
        $answer = "null";
        if($value === false){
          $answer = "'0'";
        } else if($value === true){
          $answer = "'1'";
        } else if($value !== NULL) {
          $answer = "'" . str_replace(array("\\", "'"), array("\\\\", "\\'"), strval($value)) . "'";
        }
        
        // add argument to query
        $query .= $answer;
        
        // updateing last position
        $last_pos = $index + strlen($expression);
        
      } // end foreach $param
    } // end foreach 
    
    // add end of query
    $len = strlen($query_string);
    if($last_pos < $len){
      $query .= substr($query_string, $last_pos);
    }

    $reply = mysql_query($query, $LOCAL["conn"]);
    
    // report error if exists
    $error_message = mysql_error();
    if($error_message) { 
      die ("Error in query:<br/>\n($error_message)<br/>\n$query"); 
    }
    
    return $reply;
  }

?>
