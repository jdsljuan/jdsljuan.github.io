<?PHP
// Llamada a la función
include_once('./utils.php');
sessionHandler();

$query = makeQuery();
$results = db_getQueryExec($query, true);

?>
<!DOCTYPE html>
<html lang="es">

<head>
    <link rel="shortcut icon" type="image/png" href="favicon.png"/>
    <script src="./assests/popper.min.js"></script>
    <!-- Enlace a las fuentes e iconos de FontAwesome -->
    <link rel="stylesheet" href="./assests/font-awesome_5.15.3_css_all.min.css">
    <!-- Enlace al CSS de Bootstrap -->
    <link href="./assests/bootstrap.min.css" rel="stylesheet">
    <!-- Enlace al CSS de Bootstrap Tables -->
    <link href="./assests/bootstrap-table.min.css" rel="stylesheet">
    <!-- Enlace al JS de Bootstrap (jQuery es necesario para Bootstrap Tables) -->
    <script src="./assests/jquery-3.6.0.min.js"></script>
    <!-- Enlace al JS de Bootstrap -->
    <script src="./assests/bootstrap.min.js"></script>
    <!-- Enlace al JS de Bootstrap Tables -->
    <script src="./assests/bootstrap-table.min.js"></script>
    <title>Resumen de Datos</title>
</head>

<body class="m-5">
    <div class="row mb-1">
        <div class="col-3">
            <div class="btn-group">
                <button type="button" class="btn btn-dark dropdown-toggle" data-bs-toggle="dropdown" data-bs-display="static" aria-expanded="false"><i class="fas fa-code"></i>
                </button>
                <div class="dropdown-menu mt-2" style="min-width: 400px; width: auto; height:auto;">
                    <form class="px-3 py-3" action="data_state.php" method="post" enctype="multipart/form-data">
                        <h5 class="h5 text-center">Consulta Cruda</h5>
                        <div class="form-group">
                            <label for="user_query">SQL:</label>
                            <textarea name="user_query" id="user_query" class="form-control mb-2"></textarea>
                        </div>
                        <button type="submit" class="btn btn-dark">Ejecutar</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-6">
            <h2 class="h2 text-center">Analizador de Columnas</h2>
        </div>
        <div class="col-3 text-end">
            <form action="index.php" class="text-right" method="post" enctype="multipart/form-data">
                <input type="hidden" name="close_session" value="1">
                <button class="btn btn-dark " title="Cerrar Sesion" type="submit">
                    <i class="fas fa-sign-out-alt"></i>
                </button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-12 text-center">
            <span class="p-3"><i><?php echo resolveEvaluationType(); ?><br><small><?php echo resolveCriterias(); ?></small></i></span>
        </div>
    </div>
    <hr>
    <div id="toolbar">
        <button class="btn btn-dark" type="button" onclick="location.href = './';">
            < Atras</button>
                <button class="btn btn-light border-dark" type="button" onclick="location.href = './get_evaluation.php?'+location.href.split('?')[1];">Descargar</button>
    </div>
    <table id="table1" class="table" data-show-columns="true" data-pagination="true" data-show-export="true" data-export-types="['excel']" data-show-search-clear-button="true" data-search="true" data-toolbar="#toolbar">
        <!-- Your table content here -->
    </table>
    <!-- Footer  -->
    <hr>
    <br>
    <footer>
        <div class="col-12 text-center">
            <span><b>Alcaldía de Saravena, Arauca - Oficina de Salud Publica</b></span><br>
            <span><b>Juan David Sanchez Leal &copy; 2023</b></span>
        </div>
    </footer>
</body>
<script>
    // Inicializar la tabla con los datos
    $(function() {
        var $data = <?php echo json_encode($results, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE); ?>;


        $('#table1').bootstrapTable({
            data: $data,
            columns: [
                <?php $keys = array_keys($results[0]); ?>
                <?php for ($i=0; $i < sizeof($keys); $i++) { ?>
                {
                    field: '<?php echo $keys[$i]; ?>',
                    title: '<?php echo $keys[$i]; ?>'
                },
                <?php } ?>
            ]
        });
    });
</script>

</html>