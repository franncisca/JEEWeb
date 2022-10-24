package javaBeans;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;

public class TestConsultation {
	public static void main(String[] args) {
		String dbURL = "jdbc:mysql://sqletud.u-pem.fr:3306/yan.li_db";
		String user = "yan.li";
		String password = "Mysql568**";
		
		BOperations bo = new BOperations(dbURL, user, password);

		try {
			bo.ouvrirConnexion();
		} catch (TraitementException e) {
			System.out.println(MessagesDErreurs.getMessageDerreur(e.getNumber()));
		}
		bo.setNoCompte("0001");
		try {
			bo.consulter();
		} catch (TraitementException e) {
			System.out.println(MessagesDErreurs.getMessageDerreur(e.getNumber()));
		}
		
		
		System.out.println(bo.getNoDeCompte());
		System.out.println(bo.getNom());
		System.out.println(bo.getPrenom());
		System.out.println(bo.getSolde());
		
		try {
			bo.fermerConnexion();
		} catch (TraitementException e) {
			System.out.println(MessagesDErreurs.getMessageDerreur(e.getNumber()));
		}
		
	}

}
