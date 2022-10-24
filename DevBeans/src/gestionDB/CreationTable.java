package gestionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreationTable {
	
	public static void main(String[] args) throws ClassNotFoundException {
		//Trigger a dynamic loading of the driver into the DriverManager.
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		try {
			
			//Establishes a connection to the database server.
			String DBurl = "jdbc:mysql://sqletud.u-pem.fr:3306/yan.li_db";
			Connection dbConnect = DriverManager.getConnection(DBurl, "yan.li", "Mysql568**");
			Statement statement = dbConnect.createStatement();
			
			String sqlCreateTableCompte = "CREATE TABLE IF NOT EXISTS COMPTE("
					+ "NOCOMPTE CHAR(4) NOT NULL,"
					+ "NOM VARCHAR(20),"
					+ "PRENOM VARCHAR(20),"
					+ "SOLDE DECIMAL(10,2) NOT NULL,"
					+ "PRIMARY KEY (`NOCOMPTE`))";
			
			
			String sqlCreateTableOperation = "CREATE TABLE IF NOT EXISTS OPERATION("
					+ "NOCOMPTE CHAR(4) NOT NULL,"
					+ "DATE DATE NOT NULL,"
					+ "HEURE TIME NOT NULL,"
					+ "OP CHAR(1) NOT NULL,"
					+ "VALEUR DECIMAL(10,2) NOT NULL)";
			
			statement.executeUpdate(sqlCreateTableCompte);
			
			statement.executeUpdate(sqlCreateTableOperation);
			
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.out.println(ex);
		}
		
	}

}
