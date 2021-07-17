<%@page import="model.Streak"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=, initial-scale=1.0">
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
     <link rel="icon" href="icon_path" type="image/icon type">
    <title>Index Page for Web Application</title>
</head>
<body>
    <%
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // New HTTP
        response.setHeader("Pragma", "no-cache"); // Older HTTP
        response.setHeader("Expires", "0"); // Proxy Servers
       
        if(session.getAttribute("user") == null)
        {
            session.setAttribute("records", null);
            response.sendRedirect("login");
        }
        
        boolean recordEmpty = Streak.isRecordEmpty();
        String currentStreak = "0 Hours";
        String startTimeStreak = "HH:MM:SS";
        String currentAttempt = "No Attempts";
        String bestAttempt = "No Best Attempt";
        
        if (!recordEmpty) 
        {
            String timeValue = "Days";
            if (!Streak.isHour()) timeValue = "Hours";
            currentStreak = Streak.getCurrentStreak() + " " + timeValue;
            startTimeStreak = Streak.getStartTimeStreak();
            currentAttempt = Streak.getCurrentAttempt() + "";
            bestAttempt = Streak.getBestAttempt();
        }   
    %>
    <div id="page-wrapper">

        <!-- Header -->
        <header>
            <p></p>
            <p><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></p>
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
                <a href="<%= request.getContextPath() %>/RecordServlet">
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
            
            <div class="main-information">
                <h1 id="streak"><%out.print(currentStreak);%></h1>
                <section id="time"><%out.print(startTimeStreak);%></section>
                <section id="streak-attempt">Streak # <%out.print(currentAttempt);%></section>
                <section id="streak-best"><%out.print(bestAttempt);%></section>
            </div>
            
            <% if (recordEmpty) { %>
                <a href="<%= request.getContextPath() %>/NewStreakServlet" class="button-start-end">Start Streak</a>
            <% } else { %>
                <a href="<%= request.getContextPath() %>/EndStreakServlet" class="button-start-end">End Streak</a>
            <% } %>
            
        </main>
    </div>

</body>
</html>