<%@page import="model.Streak,model.Records, java.util.ArrayList, java.util.Arrays"%>
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
    <%
        // Session Management Code Segment
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // New HTTP
        response.setHeader("Pragma", "no-cache"); // Older HTTP
        response.setHeader("Expires", "0"); // Proxy Servers
       
        System.out.println("This is the user from the records JSP:" + session.getAttribute("user"));
        
        if (session.getAttribute("user") != null)
        {
            if (session.getAttribute("records") == null)
            {
                response.sendRedirect("RecordServlet");
            }
        }
        else 
        {
            response.sendRedirect("login");
        }
        
        Records records = new Records();
        ArrayList<Integer> attempts = records.getAttempts();
        ArrayList<String> streakStart = records.getStreakStart();
        ArrayList<String> streakEnd = records.getStreakEnd();
        ArrayList<Integer> days = records.getDays();
        ArrayList<Boolean> isHours = records.getIsHours();       
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
                 <a href="<%= request.getContextPath() %>/StreakServlet">
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
                <a href="<%= request.getContextPath() %>/DownloadServlet">
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
                    <th scope="col">Attempt #</th>
                    <th scope="col">Start of Streak</th>
                    <th scope="col">End of Streak</th>
                    <th scope="col">Time (Days)</th>
                </tr>
                </thead>
                <tbody>
                <% for (int i = 0; i < attempts.size(); i++)
                { 
                    String streak = days.get(i) + "";
                    String endStreak = streakEnd.get(i);
                    
                    if (endStreak == null) 
                    {
                        endStreak = " ";
                        days.set(i, (int) Streak.getCurrentStreak());
                        isHours.set(i, Streak.isHour());
                        streak = days.get(i) + "";
                    }

                    if (isHours.get(i)) {
                        if (Integer.parseInt(streak) == 1) 
                        {
                            streak = days.get(i) + " Hour";
                        }
                        else
                        {
                            streak = days.get(i) + " Hours";
                        }
                    }
                %>
                                
                <tr>
                    <th scope="row"><%out.print(attempts.get(i));%></th>
                    <td><%out.print(streakStart.get(i)); %></td>
                    <td><%out.print(endStreak); %></td>
                    <td><%out.print(streak); %></td>
                </tr>
                <% } %>
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