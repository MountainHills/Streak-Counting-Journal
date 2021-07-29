<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=, initial-scale=1.0">
    <link href="<%= request.getContextPath() %>/error/error.css" rel="stylesheet" type="text/css"/>
     <link rel="icon" href="icon_path" type="image/icon type">
    <title>NoFAP Journal</title>
</head>
<body>
    <div id="error-page-wrapper">
        <p>There are no compelete records to download</p>
        <p> </p>
        <div id="button-container">
            <a href="<%= request.getContextPath() %>/RecordServlet" class="button">Go Back</a>
        </div>
    </div>
</body>
</html>