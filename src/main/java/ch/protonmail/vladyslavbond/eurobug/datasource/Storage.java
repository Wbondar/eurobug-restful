package ch.protonmail.vladyslavbond.eurobug.datasource;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public enum Storage
{
    DEFAULT ("org.postgresql.Driver", "localhost", "5432", "eurobug", "postgres", "postgres");
    
    private final String titleOfDriver;
    private final String host;
    private final String port;
    private final String database;
    private final String user;
    private final String password;
    
    private Storage (String titleOfDriver, String host, String port, String database, String user, String password)
    {
        this.titleOfDriver = titleOfDriver;
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }
    
    public final Connection getConnection ( )
    throws SQLException
    {
        try
        {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e)
        {
            throw new AssertionError ("Database driver is missing.", e);
        }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/eurobug", "postgres", "postgres");
    }
}
