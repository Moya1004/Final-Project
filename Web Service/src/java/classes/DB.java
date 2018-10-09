package classes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.rmi.runtime.Log;

/**
 *
 * @author Moya
 */
/**
 *
 * @author Moya
 */public class DB {
    private Connection conn;
    private Statement statement;




    public DB() {
        //System.out.println(java.lang.System.getProperty("java.library.path"));

        final String LOG = "DEBUG";
        String ip = "192.168.43.180";
        String port = "49170";
        String classs = "net.sourceforge.jtds.jdbc.Driver";
        String db = "Chat";
        String un = "sa";
        String password = "Lovely6508";
        conn = null;
        String ConnURL = null;
        try {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://localhost:49170;databaseName=FinalYearProjectDB;integratedSecurity=true;";
            conn = DriverManager.getConnection(ConnURL);
            this.statement = conn.createStatement();
        } catch (Exception e) {
            String Message = e.getMessage();
            System.out.println(Message);
        }
    }

    public boolean insertLog()
    {
        String sql = String.format("Insert Into Logs(LOGDATE,IpAddress) Values(GETDATE(),'localhost')");
        try {
            boolean result = statement.execute(sql);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean insertFile(String fileName , String fileExtension , int fileSize)
    {
        String sql = String.format("Insert Into Files(Name,Extension,Size) Values('%s','%s',%d)",fileName,fileExtension,fileSize);
        try {
            boolean result = statement.execute(sql);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean insertResponse(String requestTime , String responseInJson , int res, long responseTime)
    {
        String sql = String.format("Insert Into Responses(RequestTime,ResponseInJSON,Result,ResponseTimeInSecond) Values('%s','%s',%d,%d)",requestTime,responseInJson,res,responseTime);
        try {
            boolean result = statement.execute(sql);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    

}
