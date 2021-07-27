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
import java.io.FileOutputStream;
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
    
    Connection con;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        con = DBConnector.getConnection();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            // This is the MIME type for XLS file.
            response.setContentType("application/vnd.ms-excel");
           
            HttpSession session = request.getSession();
            int userIndex = Integer.parseInt(session.getAttribute("userIndex").toString());
            
            // These are the headers for the XLS file.
            final String[] titles = {"Attempt #", "Start of Streak", "End of Streak", "Time", "Time Value"};
            
            // Gets the values from the database.
            String query =  "SELECT ATTEMPT, START_STREAK, END_STREAK, DAYS, IS_HOURS\n" +
                "FROM RECORDS\n" +
                "WHERE USER_ID = " + userIndex + "AND END_STREAK IS NOT NULL\n" +
                "ORDER BY ATTEMPT";
            
            try (PreparedStatement pstmtRecords = con.prepareStatement(query)) 
            {
                ResultSet records = pstmtRecords.executeQuery();
                System.out.println("FINISHED creating the result set for the XLS file!");
                
                // If the records is empty.
                if (!records.next()) throw new SQLException();
                System.out.println("FINISHED checking if the records is empty.");
                
                try (Workbook wb = new HSSFWorkbook())
                {
                    response.setHeader("Content-disposition", "attachment; filename=NoFapRecords.xls");
                    System.out.println("INITIALIZED workbook and set the header.");
                    
                    // Creates the sheet of the workbook.
                    Sheet sheet = wb.createSheet("NoFap Records");
                    System.out.println("INITIALIZED sheet: NoFap Records.");

                    // Creates the styles for the cells;
                    Map<String, CellStyle> styles = createStyles(wb);
                    System.out.println("INITIALIZED cell styles.");
                    
                    // Creating the header row.
                    Row headerRow = sheet.createRow(0);

                    for (int i = 0; i < titles.length; i++) 
                    {
                        Cell headerCell = headerRow.createCell(i);
                        headerCell.setCellValue(titles[i]);
                        headerCell.setCellStyle(styles.get("header"));
                    }
                    System.out.println("FINISHED creating the header cells");

                    // Freeze the first row
                    sheet.createFreezePane(0, 1);
                    System.out.println("FINISHED Freezing the header row");

                    // Inserts the values of the records to the sheet.
                    Row row;
                    Cell cell;

                    // Gets the a new row.
                    int rownum = sheet.getLastRowNum() + 1;

                    // Insertion Loop. Do while loop is used since rs.next() was used at the beginning to determine if the record was empty.
                    do
                    {
                        row = sheet.createRow(rownum);
                        
                        for (int i = 0; i < titles.length; i++)
                        {
                            cell = row.createCell(i);

                            // Places the values under respective columns.
                            switch(i)
                            {
                                case 0:
                                {
                                    cell.setCellValue(records.getInt("ATTEMPT"));
                                    break;
                                }
                                case 1:
                                {
                                    cell.setCellValue(records.getString("START_STREAK"));
                                    break;
                                }
                                case 2:
                                {
                                    cell.setCellValue(records.getString("END_STREAK"));
                                    break;
                                }
                                case 3:
                                {
                                    cell.setCellValue(records.getInt("DAYS"));
                                    break;
                                }
                                case 4:
                                {
                                    cell.setCellValue(records.getBoolean("IS_HOURS"));
                                    break;
                                }
                            }
                            
                            cell.setCellStyle(styles.get("normal"));
                        }
                        rownum++;
                        
                    } while (records.next());
                            
                    System.out.println("FINISHED placing all the records of the database in their respective ROWS.");
                    
                    // Readjusts the columns.
                    for (int i = 0; i < titles.length; i++)
                    {
                        sheet.autoSizeColumn(i);
                    }
                    System.out.println("FINISHED adjusting Columns");
                    
                    // Creates the file.
                    wb.write(response.getOutputStream());
                    wb.close();
                    
                    System.out.println("FINISHED creating the workbook");
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
            catch (SQLException sqle) 
            {
                System.out.println("There is an SQLException error when I was creating the worksheet.");
                sqle.printStackTrace();
            }
    }
    
    private ResultSet getDatabaseRecords(int userIndex) throws SQLException
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
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
        
        return null;
    }
    
        private int getRecordCount(int userIndex) throws SQLException
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
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
        
        return 0;
    }
    
    private Map<String, CellStyle> createStyles(Workbook wb)
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
        
    private CellStyle createBorderedStyle(Workbook wb)
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
