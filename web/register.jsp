<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=, initial-scale=1.0">
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <title>Registration Page for Web Application</title>
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
                <form action="<%= request.getContextPath() %>/RegisterServlet" method="post">
                    <input type="text" name="usernameRegister" placeholder="Username" required><br>

                    <input type="password" name="passwordRegister" placeholder="Password" required><br>

                    <input type="password" name="confirmPassword" placeholder="Confirm Password" required><br>

                    <!-- CAPTCHA Created-->
                    <img src="<%= request.getContextPath() %>/SimpleCaptchaServlet" >
                    
                    <input type="text" name="captcha" placeholder="Enter Captcha" required><br>
                    
                    <input class="button-form" type="submit" value="Register">  
                </form>

                <hr>

                <a href="login" class="button">Already have an Account</a>
            </div>
        </div>        
    </div>
</body>
</html>