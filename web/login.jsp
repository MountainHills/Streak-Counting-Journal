<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=, initial-scale=1.0">
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <title>NoFap Journal</title>
</head>
<body>
    <div id="login-page-wrapper">
        <header>
            <p></p>
            <p></p>
        </header>

        <div class="information">
            <h1>NoFap Journal</h1>
            <p>A simple web application that records your NoFap streak. It allows you to download your records and see your best streaks.</p>
        </div>

        <div class="form-grid-container">
            <div class="form-container">
                <form action="<%= request.getContextPath() %>/LoginServlet" method="post">
                    <input type="text" name="usernameLogin" placeholder="Username" required><br>

                    <input type="password" name="passwordLogin" placeholder="Password" required><br>

                    <input class="button-form" type="submit" value="Log In">  
                </form>

                <hr>

                <a href="register" class="button">Create New Account</a>
            </div>
        </div>        
    </div>
</body>
</html>