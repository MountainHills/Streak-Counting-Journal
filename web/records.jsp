<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <!-- TODO: Create Web App Logo -->
    <title>Index Page for Web Application</title>
</head>
<body>
    <div id="page-wrapper">

        <!-- Header -->
        <header>
            <p></p>
            <p><a href="login">Logout</a></p>
        </header>

        <!-- Menu Bar -->
        <div class="menu-bar">
            <div class="menu-pages">
                 <img class="icons" src="icons/timer.png" alt="Timer Icon"> 
                 <p>Timer</p>
                 <a href="index">
                    <span class="link-spanner"></span>
                </a>
            </div>
            <div class="menu-pages">
                <img class="icons" src="icons/table.png" alt="Table Icon"> 
                <p>Streak Records</p>
                <a href="records">
                    <span class="link-spanner"></span>
                </a>
            </div>
            <div class="menu-pages">
                <img class="icons" src="icons/download-xls.png" alt="Download Icon"> 
                <p>Download Records</p>
                <!-- TODO: Make an XLS file. -->
                <a href="https://www.google.com">
                    <span class="link-spanner"></span>
                </a>
            </div>
        </div>
        
        <!-- Content -->
        <main class="main-content">
            <!-- TODO: Create Table -->
            <table id="myTable" class="stripe" style="width:100%">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">First</th>
                    <th scope="col">Last</th>
                    <th scope="col">Handle</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th scope="row">1</th>
                    <td>Mark</td>
                    <td>Otto</td>
                    <td>@mdo</td></tr>
               <!--  <tr>
                    <th scope="row">2</th>
                    <td>Jacob</td>
                    <td>Thornton</td>
                    <td>@fat</td></tr>
                <tr>
                    <th scope="row">3</th>
                    <td>Larry</td>
                    <td>the Bird</td>
                    <td>@twitter</td></tr> -->
                </tbody>
            </table>
        </main>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

    <script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>

    <script>  
        $(document).ready( function () {
            $('#myTable').DataTable({
                'pagingType': 'full_numbers',
                'bLengthChange': false,
                'pagelength': 10,
                'searching': false,
                order: [[0, 'desc']]
            });

            $('.dataTables_wrapper').css("width", "95%");
            
        } );
    </script>

</body>
</html>