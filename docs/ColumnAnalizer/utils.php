<?php
//SELECT Basicamente.
function db_getQueryExec($query, $BIG_SELECT = false){
    $stmt = false;
    try {
        //$host = "localhost";
        //$username = "root";
        //$password = "";
        //$dbName = "prueba_jacke";

        $host = "sql102.hyperphp.com";
        $username = "hp_32545970";
        $password = "mamasapo";
        $dbName = "hp_32545970_columnanalizer";
        $port = 3306;
    
        $dbConnection = new PDO("mysql:host=$host;port=$port;dbname=$dbName", $username, $password);
        $dbConnection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        if ($BIG_SELECT) {
            //SET PARAMS MYSQL
            $stmt = $dbConnection->prepare("SET SQL_BIG_SELECTS=1");
            $stmt->execute();
        }

        //Real Execution
        $stmt = $dbConnection->prepare($query);
        $stmt->execute();
        if($stmt == true){
            $_SESSION['last_query'] = $query;
        }
        $results = $stmt->fetchAll(PDO::FETCH_ASSOC);
    } catch (PDOException $th) {
        if($stmt == true and isset($results) ){
            $results = array(array('Mensaje' => 'Consulta Ejecutada con Exito!. Sin Datos por Devolver. QUERY: '.$query));
        }else{
            $results = array(array('Mensaje' => 'Error al Ejecutar la Consulta: '.$query));
        }
    }
    return $results;
}

function makeQuery(){
    //-----------------------------------------
    if(isset($_POST['user_query']) and !empty($_POST['user_query'])){
        return $_POST['user_query'];
    }

    //------------------------------------------    
    $query = "SELECT DISTINCT tabla1.columna1 P1, tabla1.columna2 P2, tabla1.columna3 P3, tabla1.columna4 P4, tabla1.columna5 P5, tabla2.columna1 M1, tabla2.columna2 M2, tabla2.columna3 M3, tabla2.columna4 M4, tabla2.columna5 M5 FROM tabla1";

    //------------------------------------------
    if(isset($_GET['tipoResumen']) and $_GET['tipoResumen'] == 'equal'){
        $type = " INNER JOIN tabla2 ";  $modf = " WHERE tabla1.columna1 = tabla2.columna1";
    }
    if(isset($_GET['tipoResumen']) and $_GET['tipoResumen'] == 'poblacion'){
        $type = " LEFT JOIN tabla2 "; $modf = " WHERE tabla2.columna1 IS NOT NULL";
    }
    if(isset($_GET['tipoResumen']) and $_GET['tipoResumen'] == 'muestra'){
        $type = " RIGHT JOIN tabla2 "; $modf = " WHERE tabla1.columna1 IS NOT NULL";
    }
    if(isset($_GET['tipoResumen']) and $_GET['tipoResumen'] == 'poblacion-negativo'){
        $type = " LEFT JOIN tabla2 "; $modf = " WHERE tabla2.columna1 IS NULL";
    }
    if(isset($_GET['tipoResumen']) and $_GET['tipoResumen'] == 'muestra-negativo'){
        $type = " RIGHT JOIN tabla2 "; $modf = " WHERE tabla1.columna1 IS NULL";
    }
    
    //------------------------------------------
    $criteria = " ON tabla1.columna1 = tabla2.columna1";
    if(isset($_GET['criterio2'])){
        $criteria = $criteria." AND tabla1.columna2 = tabla2.columna2";
    }
    if(isset($_GET['criterio3'])){
        $criteria = $criteria." AND tabla1.columna3 = tabla2.columna3";
    }
    if(isset($_GET['criterio4'])){
        $criteria = $criteria." AND tabla1.columna4 = tabla2.columna4";
    }
    if(isset($_GET['criterio5'])){
        $criteria = $criteria." AND tabla1.columna5 = tabla2.columna5";
    }

    //-------------------------------------------------------------------
    if(isset($_GET['esp_a']) and $_GET['esp_a'] == 'on'){
        $modf .= " ".resolveLogicOperator(isset($_GET['esp_a_or']))." ".resolveColumnName($_GET['esp_a_col'])." ".resolveOperation($_GET['esp_a_oper'])." ".resolveParamText($_GET['esp_a_text']);
    } 
    if(isset($_GET['esp_b']) and $_GET['esp_b'] == 'on'){
        $modf .= " ".resolveLogicOperator(isset($_GET['esp_b_or']))." ".resolveColumnName($_GET['esp_b_col'])." ".resolveOperation($_GET['esp_b_oper'])." ".resolveParamText($_GET['esp_b_text']);
    }
    if(isset($_GET['esp_c']) and $_GET['esp_c'] == 'on'){
        $modf .= " ".resolveLogicOperator(isset($_GET['esp_c_or']))." ".resolveColumnName($_GET['esp_c_col'])." ".resolveOperation($_GET['esp_c_oper'])." ".resolveParamText($_GET['esp_c_text']);
    }
    if(isset($_GET['esp_d']) and $_GET['esp_d'] == 'on'){
        $modf .= " ".resolveLogicOperator(isset($_GET['esp_d_or']))." ".resolveColumnName($_GET['esp_d_col'])." ".resolveOperation($_GET['esp_d_oper'])." ".resolveParamText($_GET['esp_d_text']);
    }
    if(isset($_GET['esp_e']) and $_GET['esp_e'] == 'on'){
        $modf .= " ".resolveLogicOperator(isset($_GET['esp_e_or']))." ".resolveColumnName($_GET['esp_e_col'])." ".resolveOperation($_GET['esp_e_oper'])." ".resolveParamText($_GET['esp_e_text']);
    }

    return $query = $query.$type.$criteria.$modf;
}

function resolveLogicOperator($check){
    if($check){
        return "OR"; 
    }
    return "AND";
}

function resolveOperation($oper){
    $str = "";
    switch ($oper) {
    case 'equal':
        $str = "=";
        break;
    case 'menor':
        $str = "<";
        break;
    case 'mallor':
        $str = ">";
        break;
    case 'como':
        $str = "LIKE";
        break;
    case 'diff':
        $str = "!=";
        break;
    default:
        break;
    }
    return $str;
}

function resolveColumnName($column){
    $name = "";
    switch ($column) {
        case 'P1':
            $name = "tabla1.columna1";
            break;
        case 'P2':
            $name = "tabla1.columna2";
            break;
        case 'P3':
            $name = "tabla1.columna3";
            break;
        case 'P4':
            $name = "tabla1.columna4";
            break;
        case 'P5':
            $name = "tabla1.columna5";
            break;
        case 'M1':
            $name = "tabla2.columna1";
            break;
        case 'M2':
            $name = "tabla2.columna2";
            break;
        case 'M3':
            $name = "tabla2.columna3";
            break;
        case 'M4':
            $name = "tabla2.columna4";
            break;
        case 'M5':
            $name = "tabla2.columna5";
            break;
    }
    return $name;
}

function resolveParamText($text){
    if(intval($text) != 0){ return $text; }
    $name = "";
    switch ($text) {
        case 'P1':
            $name = "tabla1.columna1";
            break;
        case 'P2':
            $name = "tabla1.columna2";
            break;
        case 'P3':
            $name = "tabla1.columna3";
            break;
        case 'P4':
            $name = "tabla1.columna4";
            break;
        case 'P5':
            $name = "tabla1.columna5";
            break;
        case 'M1':
            $name = "tabla2.columna1";
            break;
        case 'M2':
            $name = "tabla2.columna2";
            break;
        case 'M3':
            $name = "tabla2.columna3";
            break;
        case 'M4':
            $name = "tabla2.columna4";
            break;
        case 'M5':
            $name = "tabla2.columna5";
            break;
    }
    if($name == ""){ $name = "'".$text."'"; }
    return $name;
}

function resolveEvaluationType(){
    $str_type = '';
    if(isset($_GET['tipoResumen']) and $_GET['tipoResumen'] == 'equal'){
        $str_type = " COINCIDENCIA TOTAL ";
    }
    else if(isset($_GET['tipoResumen']) and $_GET['tipoResumen'] == 'poblacion'){
        $str_type = " AQUELLOS DE LA POBLACION QUE ESTAN EN LA MUESTRA "; 
    }
    else if(isset($_GET['tipoResumen']) and $_GET['tipoResumen'] == 'muestra'){
        $str_type = " AQUELLOS DE LA MUESTRA QUE ESTEN EN LA POBLACION ";
    }
    else if(isset($_GET['tipoResumen']) and $_GET['tipoResumen'] == 'poblacion-negativo'){
        $str_type = " AQUELLOS DE LA POBLACION QUE NO ESTAN EN LA MUESTRA "; 
    }
    else if(isset($_GET['tipoResumen']) and $_GET['tipoResumen'] == 'muestra-negativo'){
        $str_type = " AQUELLOS DE LA MUESTRA QUE NO ESTEN EN LA POBLACION "; 
    }
    return $str_type;
}

function resolveCriterias(){
    $str_criteria = '(Caracteristica 1) ';
    if(isset($_GET['criterio2'])){
        $str_criteria .= '(Caracteristica 2) '; 
    }
    if(isset($_GET['criterio3'])){
        $str_criteria .= '(Caracteristica 3) '; 
    }
    if(isset($_GET['criterio4'])){
        $str_criteria .= '(Caracteristica 4) '; 
    }
    if(isset($_GET['criterio5'])){
        $str_criteria .= '(Caracteristica 5) '; 
    }
    return $str_criteria;
}

function sessionHandler(){
    session_start();
    $keylog = "SARAVENA".date('d');//date('ymd')."-".date("dmy");
    
    //Close the Current Session
    if(isset($_POST['close_session']) and $_POST['close_session'] == 1){
        $_SESSION['key_lock'] = ""; $_SESSION['last_query'] = ""; session_destroy();
    }

    //Condiciones para continuar
    if(isset($_POST['key_lock']) and $_POST['key_lock'] == $keylog){
        $_SESSION['key_lock'] = $keylog;
        return true;
    } 

    if(isset($_SESSION['key_lock']) and $_SESSION['key_lock'] == $keylog){
        return true;
    } 
    
    //Chao Lola
    header("Location: login.php");
    exit();
    return false;
}
?>