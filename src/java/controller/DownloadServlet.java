/*
    This servlet would allow the user to download their records
    in the XLS Format. The download would only be available if there
    are atleast two records. Otherwise, the download will not work.
    
    The format of the Excel File would be similar to what is seen
    on the records page. EXCEPT, it will only contain completed records.

    It will contain ATTEMPTS, START_STREAK, END_STREAK, and IS_HOURS.
*/
package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

@WebServlet(name = "DownloadServlet", urlPatterns = {"/DownloadServlet"})
public class DownloadServlet extends HttpServlet {
    
    static Connection con;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        con = DBConnector.getConnection();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
            // This is the MIME type for XLS file.
            response.setContentType("application/vnd.ms-excel");
           
            HttpSession session = request.getSession();
            int userIndex = Integer.parseInt(session.getAttribute("userIndex").toString());
            
            // Checks whether the record count has values.
            int recordCount = getRecordCount(userIndex);
            
            // If there are no values go to error page.
            if (recordCount == 0) throw new NullPointerException();
            
            // Place the values here.
            final String[] titles = {"Attempt #", "Start of Streak", "End of Streak", "Time", "Time Value"};
            // Gets the values from the database.
            ResultSet records = getDatabaseRecords(userIndex);
            
            try (Workbook wb = new HSSFWorkbook())
            {
                // Creates the sheet of the workbook.
                Sheet sheet = wb.createSheet("NoFap Records");
                
                // Creating the header row.
                Row headerRow = sheet.createRow(0);
                
                for (int i = 0; i < titles.length; i++) 
                {
                    Cell headerCell = headerRow.createCell(i);
                    headerCell.setCellValue(titles[i]);
                    sheet.autoSizeColumn(i);
                }
                
                // Freeze the first row
                sheet.createFreezePane(0, 1);
                
                // Inserts the values of the records to the sheet.
                Row row;
                Cell cell;
                
                // Gets the a new row.
                int rownum = sheet.getLastRowNum() + 1;
                
                // Insertion Loop.
                for (int i = 0; i < recordCount; i++, rownum++)
                {
                    row = sheet.createRow(rownum);
                    
                    for (int j = 0; j < titles.length; j++)
                    {
                        cell = row.createCell(j);
                        
                        switch(j)
                        {
                            case 1:
                            {
                                   
                            };
                            case 2:;
                            case 3:;
                            case 4:;
                            case 5:;
                        }
                    }
                }
            }
            catch (FileNotFoundException fnfe)
            {
                fnfe.printStackTrace();
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
            
    }
    
    private static ResultSet getDatabaseRecords(int userIndex) throws SQLException
    {
        String query =  "SELECT ATTEMPT, START_STREAK, END_STREAK, DAYS, IS_HOURS\n" +
                        "FROM RECORDS\n" +
                        "WHERE USER_ID = " + userIndex + "AND END_STREAK IS NOT NULL\n" +
                        "ORDER BY ATTEMPT";
        
        try (PreparedStatement pstmtRecords = con.prepareStatement(query))
        {
            ResultSet records = pstmtRecords.executeQuery();
            
            return records;
        }
    }
    
        private static int getRecordCount(int userIndex) throws SQLException
    {
        String query =  "SELECT COUNT(*)\n" +
                        "FROM RECORDS\n" +
                        "WHERE USER_ID = " + userIndex + " AND END_STREAK IS NOT NULL";
        
        try (PreparedStatement pstmtRecords = con.prepareStatement(query))
        {
            ResultSet rs = pstmtRecords.executeQuery();
            
            while (rs.next())
            {
                return rs.getInt(1);
            }
        }
        
        return 0;
    }
    
    private static Map<String, CellStyle> createStyles(Workbook wb)
    {
        Map<String, CellStyle> styles = new HashMap<>();
        
        // Style for the headers.
        CellStyle style;
        Font headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);  
        headerFont.setFontName("Times New Roman");
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);
        
        // Style for the normal cells.
    
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        styles.put("normal", style);
        
        return styles;
    }
        
    private static CellStyle createBorderedStyle(Workbook wb)
    {
        BorderStyle thin = BorderStyle.THIN;
        short black = IndexedColors.BLACK.getIndex();

        CellStyle style = wb.createCellStyle();
        style.setBorderRight(thin);
        style.setRightBorderColor(black);
        style.setBorderBottom(thin);
        style.setBottomBorderColor(black);
        style.setBorderLeft(thin);
        style.setLeftBorderColor(black);
        style.setBorderTop(thin);
        style.setTopBorderColor(black);
        return style;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
