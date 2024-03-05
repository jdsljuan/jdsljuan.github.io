<?php session_start(); session_destroy(); ?>
<!DOCTYPE html>
<html lang="es">
<head>
    <!-- Enlace a las fuentes e iconos de FontAwesome -->
    <link rel="stylesheet" href="./assests/font-awesome_5.15.3_css_all.min.css">
    <!-- Enlace al CSS de Bootstrap -->
    <link href="./assests/bootstrap.min.css" rel="stylesheet">
    <!-- Enlace al JS de Bootstrap -->
    <script src="./assests/bootstrap.min.js"></script>
    <title>ColumnAnalizer</title>
</head>
<body class="p-5">
    <div class="row">
        <div class="col-12">
            <h1 class="h1 text-center">Analizador de Columnas</h1>
        </div>
    </div>
    <div class="row p-3">
        <div class="col-2"></div>
        <div class="col-8">
            <hr>
            <br>
            <h6 class="h6 text-center text-secondary"><small><i><i class="fa fa-key" aria-hidden="true"></i> Frase de Seguridad:</i></small></h6>
            <form class="border border-black rounded p-3 text-center" action="./index.php" method="post">
                <input class="form-control" required type="password" name="key_lock" id="key_lock" value=""><br>
                <button class="btn btn-dark btn-sm" type="submit"><i class="fa fa-unlock" aria-hidden="true"></i></button>
            </form>
            <br>
            <hr>
        </div>
    </div>
    <br>
    <footer>
        <div class="col-12 text-center">
            <span><b>Alcaldía de Saravena, Arauca - Oficina de Salud Publica</b></span><br>
             <span><b>Juan David Sanchez Leal &copy; 2023</b></span>
        </div>
    </footer>
</body>
</html>