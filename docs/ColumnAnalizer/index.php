<?PHP
include_once('./utils.php');
sessionHandler();

$query = "SELECT (SELECT COUNT(*) FROM tabla1) AS CT1, (SELECT COUNT(*) FROM tabla2) AS CT2";
$query1 = "SELECT tabla1.columna1 AS P1, tabla1.columna2 AS P2, tabla1.columna3 AS P3, tabla1.columna4 AS P4, tabla1.columna5 AS P5 FROM tabla1 LIMIT 8";
$query2 = "SELECT tabla2.columna1 AS M1, tabla2.columna2 AS M2, tabla2.columna3 AS M3, tabla2.columna4 AS M4, tabla2.columna5 AS M5 FROM tabla2 LIMIT 8";
$results = db_getQueryExec($query);
$results1 = db_getQueryExec($query1);
$results2 = db_getQueryExec($query2);

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
    <!-- Enlace al JS de Bootstrap -->
    <script src="./assests/bootstrap.min.js"></script>
    <!-- Enlace al JS de Bootstrap (jQuery es necesario para Bootstrap Tables) -->
    <script src="./assests/jquery-3.6.0.min.js"></script>
    <title>ColumnAnalizer</title>
</head>

<body class="m-5">
    <div class="row mb-3">
        <div class="col-3">
            <!-- Dropdown Upload File -->
            <div class="btn-group">
                <button type="button" class="btn btn-dark dropdown-toggle" data-bs-toggle="dropdown" data-bs-display="static" aria-expanded="false">Subir Archivo
                </button>
                <div class="dropdown-menu mt-2" style="width: 300px;">
                    <form class="px-3 py-3" action="upload_file.php" method="post" enctype="multipart/form-data">
                        <h5 class="h5 text-center">Subir Datos</h5>
                        <div class="form-group">
                            <label for="csv_file">Archivo CSV:</label>
                            <input class="form-control mb-2" required type="file" name="csv_file" accept=".csv" required>
                        </div>
                        <div class="form-group">
                            <label for="name">Conjunto:</label>
                            <select class="form-control mb-3" required name="name" id="name">
                                <option value="tabla1">POBLACION</option>
                                <option value="tabla2">MUESTRA</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="name">Separador:</label>
                            <select class="form-control mb-3" required name="separator" id="separator">
                                <option value=";">CSV - Punto y Coma ;</option>
                                <option value=",">CSV - Coma ,</option>
                                <option value="|">CSV - Barra |</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-dark">Cargar</button>
                    </form>
                </div>
            </div>
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
    <hr>
    <!-- Current Data -->
    <div class="row">
        <div class="col-12 col-sm-2 text-center border-end">
            <h6>Filas Totales</h6>
            <div class="card rounded bg-light p-3 shadow">
                <h1><?php echo $results[0]['CT1']; ?></h1>
                <h5>Poblacion</h5>
            </div>
            <br>
            <div class="card rounded bg-light p-3 shadow">
                <h1><?php echo $results[0]['CT2']; ?></h1>
                <h5>Muestra</h5>
            </div>
            <br>
        </div>
        <div class="col-12 col-sm-5 text-center">
            <h6>Población <?php if(isset($_SESSION['tabla1'])){ echo " (".$_SESSION['tabla1'].")"; } ?></h6>
            <table class="table table-sm table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th>P1</th>
                        <th>P2</th>
                        <th>P3</th>
                        <th>P4</th>
                        <th>P5</th>
                    </tr>
                </thead>
                <tbody>
                    <?php for ($i = 0; $i < sizeof($results1); $i++) { ?>
                        <tr>
                            <td><?php echo $results1[$i]['P1']; ?></td>
                            <td><?php echo $results1[$i]['P2']; ?></td>
                            <td><?php echo $results1[$i]['P3']; ?></td>
                            <td><?php echo $results1[$i]['P4']; ?></td>
                            <td><?php echo $results1[$i]['P5']; ?></td>
                        </tr>
                    <?php } ?>
                </tbody>
            </table>
        </div>
        <div class="col-12 col-sm-5 text-center">
            <h6>Muestra<?php if(isset($_SESSION['tabla2'])){ echo " (".$_SESSION['tabla2'].")"; } ?></h6>
            <table class="table table-sm table-striped table-bordered  table-hover">
                <thead>
                    <tr>
                        <th>M1</th>
                        <th>M2</th>
                        <th>M3</th>
                        <th>M4</th>
                        <th>M5</th>
                    </tr>
                </thead>
                <tbody>
                    <?php for ($i = 0; $i < sizeof($results2); $i++) { ?>
                        <tr>
                            <td><?php echo $results2[$i]['M1']; ?></td>
                            <td><?php echo $results2[$i]['M2']; ?></td>
                            <td><?php echo $results2[$i]['M3']; ?></td>
                            <td><?php echo $results2[$i]['M4']; ?></td>
                            <td><?php echo $results2[$i]['M5']; ?></td>
                        </tr>
                    <?php } ?>
                </tbody>
            </table>
        </div>
    </div>
    <hr>
    <!-- Forms  -->
    <div class="row">
        <div class="col-12">
            <form class="border border-dark p-3 rounded" action="data_state.php" method="get">
                <h5 class="h5 text-center mb-3">Realizar Comparación</h5>
                <div class="row">
                <div class="col-6">
                    <div class="form-group">
                        <label for="tipoResumen">Tipo de Comparación:</label>
                        <select class="form-control mb-3" required name="tipoResumen" id="tipoResumen">
                            <option value="equal">COINCIDENCIA TOTAL</option>
                            <option value="poblacion">AQUELLOS DE LA POBLACION QUE ESTAN EN LA MUESTRA</option>
                            <option value="muestra">AQUELLOS DE LA MUESTRA QUE ESTEN EN LA POBLACION</option>
                            <option value="poblacion-negativo">AQUELLOS DE LA POBLACION QUE NO ESTAN EN LA MUESTRA</option>
                            <option value="muestra-negativo">AQUELLOS DE LA MUESTRA QUE NO ESTEN EN LA POBLACION</option>
                        </select>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="criterio2" id="check1">
                        <label class="form-check-label" for="check1">
                            Usar Segunda Caracteristica
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="criterio3" id="check2">
                        <label class="form-check-label" for="check2">
                            Usar Tercera Caracteristica
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="criterio4" id="check3">
                        <label class="form-check-label" for="check3">
                            Usar Cuarta Caracteristica
                        </label>
                    </div>
                    <div class="form-check mb-3">
                        <input class="form-check-input" type="checkbox" name="criterio5" id="check4">
                        <label class="form-check-label" for="check4">
                            Usar Quinta Caracteristica
                        </label>
                    </div>
                </div>
                <div class="col-6 border-start">
                    <h6 class="h6 text-center">Criterios Espesificos</h6>
                    <div class="row text-center mt-1">
                        <div class="col-1">On</div>
                        <div class="col-3">Columna</div>
                        <div class="col-3">Operador</div>
                        <div class="col-4">Valor</div>
                        <div class="col-1" title="Opcional"><i class="fas fa-question"></i></div>
                    </div>
                    <div class="row text-center mt-2">
                        <div class="col-1">
                            <input class="form-check-input esp" type="checkbox" name="esp_a" id="esp_a">
                        </div>
                        <div class="col-3">
                            <select disabled class="form-control esp_a" name="esp_a_col" id="esp_a_col">
                                <option value="P1">P1</option>
                                <option value="P2">P2</option>
                                <option value="P3">P3</option>
                                <option value="P4">P4</option>
                                <option value="P5">P5</option>
                                <option value="M1">M1</option>
                                <option value="M2">M2</option>
                                <option value="M3">M3</option>
                                <option value="M4">M4</option>
                                <option value="M5">M5</option>
                            </select>
                        </div>
                        <div class="col-3">
                            <select disabled class="form-control esp_a" name="esp_a_oper" id="esp_a_oper">
                                <option value="equal">igual a</option>
                                <option value="menor">menor que</option>
                                <option value="mallor">mayor que</option>
                                <option value="como">similar a</option>
                                <option value="diff">diferente</option>
                            </select>
                        </div>
                        <div class="col-4">
                            <input disabled type="text" class="form-control esp_a" name="esp_a_text" id="esp_a_text">
                        </div>
                        <div class="col-1">
                            <input class="form-check-input esp_a" type="checkbox" disabled name="esp_a_or" id="esp_a_or">
                        </div>
                    </div>
                    <div class="row text-center mt-2">
                        <div class="col-1">
                            <input class="form-check-input esp" type="checkbox" name="esp_b" id="esp_b">
                        </div>
                        <div class="col-3">
                            <select disabled class="form-control esp_b" name="esp_b_col" id="esp_b_col">
                                <option value="P1">P1</option>
                                <option value="P2">P2</option>
                                <option value="P3">P3</option>
                                <option value="P4">P4</option>
                                <option value="P5">P5</option>
                                <option value="M1">M1</option>
                                <option value="M2">M2</option>
                                <option value="M3">M3</option>
                                <option value="M4">M4</option>
                                <option value="M5">M5</option>
                            </select>
                        </div>
                        <div class="col-3">
                            <select disabled class="form-control esp_b" name="esp_b_oper" id="esp_b_oper">
                                <option value="equal">igual a</option>
                                <option value="menor">menor que</option>
                                <option value="mallor">mayor que</option>
                                <option value="como">similar a</option>
                                <option value="diff">diferente</option>
                            </select>
                        </div>
                        <div class="col-4">
                            <input disabled type="text" class="form-control esp_b" name="esp_b_text" id="esp_b_text">
                        </div>
                        <div class="col-1">
                            <input class="form-check-input esp_b" type="checkbox" disabled name="esp_b_or" id="esp_b_or">
                        </div>
                    </div>
                    <div class="row text-center mt-2">
                        <div class="col-1">
                            <input class="form-check-input esp" type="checkbox" name="esp_c" id="esp_c">
                        </div>
                        <div class="col-3">
                            <select disabled class="form-control esp_c" name="esp_c_col" id="esp_c_col">
                                <option value="P1">P1</option>
                                <option value="P2">P2</option>
                                <option value="P3">P3</option>
                                <option value="P4">P4</option>
                                <option value="P5">P5</option>
                                <option value="M1">M1</option>
                                <option value="M2">M2</option>
                                <option value="M3">M3</option>
                                <option value="M4">M4</option>
                                <option value="M5">M5</option>
                            </select>
                        </div>
                        <div class="col-3">
                            <select disabled class="form-control esp_c" name="esp_c_oper" id="esp_c_oper">
                                <option value="equal">igual a</option>
                                <option value="menor">menor que</option>
                                <option value="mallor">mayor que</option>
                                <option value="como">similar a</option>
                                <option value="diff">diferente</option>
                            </select>
                        </div>
                        <div class="col-4">
                            <input disabled type="text" class="form-control esp_c" name="esp_c_text" id="esp_c_text">
                        </div>
                        <div class="col-1">
                            <input class="form-check-input esp_c" type="checkbox" disabled name="esp_c_or" id="esp_c_or">
                        </div>
                    </div>
                    <div class="row text-center mt-2">
                        <div class="col-1">
                            <input class="form-check-input esp" type="checkbox" name="esp_d" id="esp_d">
                        </div>
                        <div class="col-3">
                            <select disabled class="form-control esp_d" name="esp_d_col" id="esp_d_col">
                                <option value="P1">P1</option>
                                <option value="P2">P2</option>
                                <option value="P3">P3</option>
                                <option value="P4">P4</option>
                                <option value="P5">P5</option>
                                <option value="M1">M1</option>
                                <option value="M2">M2</option>
                                <option value="M3">M3</option>
                                <option value="M4">M4</option>
                                <option value="M5">M5</option>
                            </select>
                        </div>
                        <div class="col-3">
                            <select disabled class="form-control esp_d" name="esp_d_oper" id="esp_d_oper">
                                <option value="equal">igual a</option>
                                <option value="menor">menor que</option>
                                <option value="mallor">mayor que</option>
                                <option value="como">similar a</option>
                                <option value="diff">diferente</option>
                            </select>
                        </div>
                        <div class="col-4">
                            <input disabled type="text" class="form-control esp_d" name="esp_d_text" id="esp_d_text">
                        </div>
                        <div class="col-1">
                            <input class="form-check-input esp_d" type="checkbox" disabled name="esp_d_or" id="esp_d_or">
                        </div>
                    </div>
                    <div class="row text-center mt-2">
                        <div class="col-1">
                            <input class="form-check-input esp" type="checkbox" name="esp_e" id="esp_e">
                        </div>
                        <div class="col-3">
                            <select disabled class="form-control esp_e" name="esp_e_col" id="esp_e_col">
                                <option value="P1">P1</option>
                                <option value="P2">P2</option>
                                <option value="P3">P3</option>
                                <option value="P4">P4</option>
                                <option value="P5">P5</option>
                                <option value="M1">M1</option>
                                <option value="M2">M2</option>
                                <option value="M3">M3</option>
                                <option value="M4">M4</option>
                                <option value="M5">M5</option>
                            </select>
                        </div>
                        <div class="col-3">
                            <select disabled class="form-control esp_e" name="esp_e_oper" id="esp_e_oper">
                                <option value="equal">igual a</option>
                                <option value="menor">menor que</option>
                                <option value="mallor">mayor que</option>
                                <option value="como">similar a</option>
                                <option value="diff">diferente</option>
                            </select>
                        </div>
                        <div class="col-4">
                            <input disabled type="text" class="form-control esp_e" name="esp_e_text" id="esp_e_text">
                        </div>
                        <div class="col-1">
                            <input class="form-check-input esp_e" type="checkbox" disabled name="esp_e_or" id="esp_e_or">
                        </div>
                    </div>    
                </div>
                <!-- Modal Trigger -->
                <div class="row">
                    <div class="col-4">
                        <button type="submit" class="btn btn-dark mt-4"  data-bs-toggle="modal" data-bs-target="#staticBackdrop">Evaluar</button>
                    </div>
                </div>
                </form>
        </div>
    </div>
    </div>
    <hr>

    <!-- Modal -->
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="staticBackdropLabel">Cargando ...</h1>
                <!--<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>-->
            </div>
            <div class="modal-body">
                Esto puede tardar unos segundos. La consulta se esta procesando por favor espere.
                <br>
                <br>
                <div class="text-center">
                    <i class="fas fa-praying-hands"></i>
                </div>
            </div>
            <!--<div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                <button type="button" class="btn btn-dark">Entendido</button>
            </div>-->
            </div>
        </div>
    </div>

    <!-- Footer  -->
    <br>
    <footer>
        <div class="col-12 text-center">
            <span><b>Alcaldía de Saravena, Arauca - Oficina de Salud Publica</b></span><br>
            <span><b>Juan David Sanchez Leal &copy; 2023</b></span>
        </div>
    </footer>
</body>
<script>
    function toggleDisabledClass(id) {

    }

    $(function() {
        $('.esp').on('click', function(event) {
            if ($('input#' + event.target.id + ':checked').length != 0) {
                $("." + event.target.id).prop('disabled', false);
            } else {
                $("." + event.target.id).prop('disabled', true);
            }
        });
    });
</script>

</html>