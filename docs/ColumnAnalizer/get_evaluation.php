<?PHP
include_once('./utils.php');
//sessionHandler();
session_start();

try {
    $query = $_SESSION['last_query'];
    $results = db_getQueryExec($query, true);
    exportToCSV($results, 'adc_evaluacion');
} catch(PDOException $e) {
    echo "Error de conexión: " . $e->getMessage();
}

function exportToCSV($data, $filename) {
    header('Content-Type: text/csv');
    header('Content-Disposition: attachment; filename="' . $filename . '.csv"');
    $output = fopen('php://output', 'w');
    fputcsv($output, array_keys($data[0]));
    foreach ($data as $row) {
        fputcsv($output, $row);
    }
    fclose($output);
}

exit();
?>