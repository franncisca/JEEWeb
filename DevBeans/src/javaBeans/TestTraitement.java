package javaBeans;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;

public class TestTraitement {
	public static void main(String[] args) {
				
		String dbURL = "jdbc:mysql://sqletud.u-pem.fr:3306/yan.li_db";
		String user = "yan.li";
		String password = "Mysql568*";
		BOperations bo = new BOperations(dbURL, user, password);

		try {
			bo.ouvrirConnexion();
		} catch (TraitementException e) {
			System.out.println(MessagesDErreurs.getMessageDerreur(e.getNumber()));
		}
		bo.setNoCompte("0001");
		bo.setOp("-");
		bo.setValeur("100");
		try {
			bo.traiter();
		} catch (TraitementException e) {
			System.out.println(MessagesDErreurs.getMessageDerreur(e.getNumber()));
		}
		
		System.out.println("ancien solde: "+ bo.getAncienSolde());
		System.out.println("nouveau solde: " + bo.getNouveauSolde());
		System.out.println("operation: "+bo.getOp());
		System.out.println("valeur: " + bo.getValeur());
		
		try {
			bo.fermerConnexion();
		} catch (TraitementException e) {
			System.out.println(MessagesDErreurs.getMessageDerreur(e.getNumber()));
		}
	}

}
