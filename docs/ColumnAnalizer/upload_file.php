<?PHP
include_once('./utils.php');
sessionHandler();

function uploadCSVAndInsertIntoTable($fileInputName, $tableName, $separator, $dbConnection)
{

    if ($_FILES[$fileInputName]['error'] === UPLOAD_ERR_OK) {
        $csvFile = $_FILES[$fileInputName]['tmp_name'];
        $_SESSION[$tableName] = $_FILES[$fileInputName]['name'];
        $query = "DELETE FROM $tableName";
        $stmt = $dbConnection->prepare($query);
        $stmt->execute();

        if (($handle = fopen($csvFile, "r")) !== false) {
            $query = "INSERT INTO $tableName(columna1, columna2, columna3, columna4, columna5) VALUES ";
            $initial = true;
            while (($data = fgetcsv($handle, 0, $separator)) !== false) {
                if (!$initial) {
                    $query .= ",";
                }
                $initial = false;
                $query .= "('" . implode("','", $data) . "')";
            }
            //echo $query;
            $stmt = $dbConnection->prepare($query);
            $stmt->execute();
            fclose($handle);
            return true;
        }
    }
    return false;
}

////Llamada a la función
//$host = "localhost";
//$username = "root";
//$password = "";
//$dbName = "prueba_jacke";

$host = "sql102.hyperphp.com";
$username = "hp_32545970";
$password = "mamasapo";
$dbName = "hp_32545970_columnanalizer";
$port = 3306;

try {
    $dbConnection = new PDO("mysql:host=$host;port=3306;dbname=$dbName", $username, $password);
    $dbConnection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if (uploadCSVAndInsertIntoTable('csv_file', $_POST['name'], $_POST['separator'], $dbConnection)) {
        $result = true;
    } else {
        $result = false;
    }
} catch (PDOException $error) {
    
}

?>
<!DOCTYPE html>
<html lang="es">

<head>
    <link rel="shortcut icon" type="image/png" href="favicon.png"/>
    <!-- Redireccionamiento. -->
    <meta http-equiv="refresh" content="3;URL=index.php">
    
    <script src="./assests/popper.min.js"></script>
    <!-- Enlace a las fuentes e iconos de FontAwesome -->
    <link rel="stylesheet" href="./assests/font-awesome_5.15.3_css_all.min.css">
    <!-- Enlace al CSS de Bootstrap -->
    <link href="./assests/bootstrap.min.css" rel="stylesheet">
    <!-- Enlace al JS de Bootstrap -->
    <script src="./assests/bootstrap.min.js"></script>
    <!-- Enlace al JS de Bootstrap (jQuery es necesario para Bootstrap Tables) -->
    <script src="./assests/jquery-3.6.0.min.js"></script>

    <title>ColumnAnalizer</title>
</head>
<body class="p-5">
    <?php if (isset($error) || !$result) { ?>
        <div class="col-12">
            <div class="alert alert-danger text-center" role="alert">
                <span class="text-danger"><b> No se ha Cargado el Archivo. <i class="fas fa-exclamation-circle"></i></b></span>
            </div>
        </div>
    <?php } else { ?>
        <div class="col-12">
            <div class="alert alert-success text-center" role="alert">
                <span class="text-success"><b>Archivo Cargado Correctamente <i class="fas fa-thumbs-up"></i></b></span>

            </div>
        </div>
    <?php } ?>
    <br>
    <h6 class="h6 text-center"><i><b>Redireccionando ...</b></i></h6>
</body>

</html>
<?php exit(); ?>