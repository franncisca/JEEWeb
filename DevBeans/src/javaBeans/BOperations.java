package javaBeans;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gestionErreurs.TraitementException;

public class BOperations {
	private String noDeCompte;
	private String nom;
	private String prenom;
	private BigDecimal solde;
	private BigDecimal ancienSolde;
	private BigDecimal nouveauSolde;
	private BigDecimal valeur;
	private String op;
	private ArrayList<String[]> operationParDates = new ArrayList<>();
	private String dateInf;
	private String dateSup;
	
	
	
	
	private String dbURL ;
    private String user;
    private String password;
	private Connection dbConnection ;

	
	public BOperations(String dbURL, String user, String password) {
		this.user = user;
		this.dbURL = dbURL;
		this.password = password;
	}
	
	public void setNoCompte(String noDeCompte) {
		this.noDeCompte = noDeCompte;
	}
	
	public void setValeur(String valeur) {
//		BigDecimal.valueOf(Double.valueOf(valeur))
		this.valeur = new BigDecimal(valeur);
	}
	
	public void setOp(String op) {
		this.op = op;
	}
	
	public void setDateInf(String dateInf) {
		this.dateInf = dateInf;
	}
	
	public void setDateSup(String dateSup) {
		this.dateSup = dateSup;
	}
	
	
	public String getNoDeCompte() {
		return this.noDeCompte.toString();
	}
	
	public String getNom() {
		return this.nom.toString();
	}
	
	public String getPrenom() {
		return this.prenom.toString();
	}
	
	public BigDecimal getSolde() {
		return this.solde;
	}

	public BigDecimal getAncienSolde() {
		return this.ancienSolde;
	}
	
	public BigDecimal getNouveauSolde() {
		return this.nouveauSolde;
	}
	
	public String getValeur() {
		return this.valeur.setScale(2).toString();
	}
	
	public String getOp() {
		return this.op;
	}
	
	public String getDateInf() {
		return this.dateInf;
	}
	
	public String getDateSuo() {
		return this.dateSup;
	}
	
	public ArrayList<String[]> getOperationsParDate(){
		return this.operationParDates;
	}
	
	public void ouvrirConnexion() throws TraitementException {
		try {
//			Trigger a dynamic loading of the driver into the DriverManager.
			System.out.println(this.dbURL + this.user + this.password);
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.dbConnection = DriverManager.getConnection(this.dbURL, this.user, this.password);
			
		}catch (SQLException e) {
			//			System.out.println(e);
			throw new TraitementException("3");
			
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			System.out.println(e);
			throw new TraitementException("21");

		}
	}
	
	public void fermerConnexion() throws TraitementException {
		try {
			this.dbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);		
			throw new TraitementException("22");
		}
	}
	
	public void consulter() throws TraitementException {	
		try {
			Statement statement = this.dbConnection.createStatement();
			
			String sqlInfoCompte = "SELECT * FROM COMPTE WHERE NOCOMPTE = '"+ noDeCompte +"' ";	
			ResultSet rs = statement.executeQuery(sqlInfoCompte);
			
			//Move the cursor forward to next row
			rs.next();
			noDeCompte = rs.getString("NOCOMPTE");
			nom = rs.getString("NOM");
			prenom = rs.getString("PRENOM");
			solde = rs.getBigDecimal("SOLDE");
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			throw new TraitementException("24");	
		}
	}
	
	public void traiter() throws TraitementException, SQLException {
		//Roll back
		try {
			Statement statement = dbConnection.createStatement();
			dbConnection.setAutoCommit(false);
			String sqlExtracSolde = "SELECT SOLDE FROM COMPTE WHERE NOCOMPTE = '"+ noDeCompte +"' ";
			ResultSet rs = statement.executeQuery(sqlExtracSolde);
						
			rs.next();
			solde = rs.getBigDecimal("SOLDE");			
			ancienSolde = solde;
			
			
			if(op.equals("-")) {
				var diff = solde.subtract(valeur);
				if(diff.compareTo(BigDecimal.ZERO) > 0) {
					nouveauSolde = diff;
				}
				else{
					System.out.println("The solde is not enough, the opreration is denied");
					nouveauSolde = ancienSolde;
					dbConnection.rollback();
					throw new TraitementException("24");
				}
			}else {
				nouveauSolde = solde.add(valeur);
			}

			String sqlUpdateSolde = "UPDATE COMPTE SET SOLDE = '"+ nouveauSolde +"' WHERE NOCOMPTE = '"+ noDeCompte +"' ";
			
//			System.out.println("sql: "+sqlUpdateSolde);
			
			String sqlUpdateOpreration = "INSERT INTO "
					+ "OPERATION (NOCOMPTE, DATE, HEURE, OP, VALEUR) "
					+ "VALUE ('"+ noDeCompte +"', CURRENT_DATE(), CURRENT_TIME(), '"+ op +"', '"+ valeur +"' ) ";
			
//			System.out.println("update operation:" + sqlUpdateOpreration);
			
			Statement statement2 = dbConnection.createStatement();
			statement2.executeUpdate(sqlUpdateSolde);
			
			Statement statement3 = dbConnection.createStatement();
			statement3.executeLargeUpdate(sqlUpdateOpreration);		
			
			dbConnection.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			dbConnection.rollback();
			throw new TraitementException("24");	
		}
	}
	
	@SuppressWarnings("null")
	public ArrayList<String[]> listerParDate() throws TraitementException{
		String sqlGetOperations = "SELECT * FROM OPERATION WHERE NOCOMPTE = '"+ noDeCompte +"' AND DATE >= '"+dateInf+"' AND DATE <= '"+dateSup+"'   ";
		try {
			Statement statement = dbConnection.createStatement();
			ResultSet rs = statement.executeQuery(sqlGetOperations);
			
			while(rs.next()) {
				String[] elements = new String[5]; 	
				
				elements[0] = rs.getString("NOCOMPTE");
				elements[1] = rs.getString("DATE");
				elements[2] = rs.getString("HEURE");
				elements[3] = rs.getString("OP");
				elements[4] = rs.getString("VALEUR");
				
//				for(int i = 0; i < 5; i ++) {
//					elements[i] = rs.getString("");
//				}
				operationParDates.add(elements);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			throw new TraitementException("24");	
		}
		return operationParDates;		
	}
	

}
