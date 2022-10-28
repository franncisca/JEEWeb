package javaBeans;

import java.sql.SQLException;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;

public class TestTraitement {
	public static void main(String[] args) throws SQLException, TraitementException {
				
		String dbURL = "jdbc:mysql://sqletud.u-pem.fr:3306/yan.li_db";
		String user = "yan.li";
		String password = "Mysql568*";
		BOperations bo = new BOperations(dbURL, user, password);

		
		bo.ouvrirConnexion();
		
		bo.setNoCompte("0001");
		bo.setOp("+");
		bo.setValeur("100");
		bo.traiter();
		
		
		System.out.println("ancien solde: "+ bo.getAncienSolde());
		System.out.println("nouveau solde: " + bo.getNouveauSolde());
		System.out.println("operation: "+bo.getOp());
		System.out.println("valeur: " + bo.getValeur());
		
		
		bo.fermerConnexion();
	
	}

}
