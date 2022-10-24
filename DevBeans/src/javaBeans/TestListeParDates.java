package javaBeans;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;

public class TestListeParDates {

	public static void main(String[] args) {
		
		
		String dbURL = "jdbc:mysql://sqletud.u-pem.fr:3306/yan.li_db";
		String user = "yan.li";
		String password = "Mysql568**";
		BOperations bo = new BOperations(dbURL, user, password);

		try {
			bo.ouvrirConnexion();
		} catch (TraitementException e) {
			// TODO Auto-generated catch block
			System.out.println(MessagesDErreurs.getMessageDerreur(e.getNumber()));
		}
		bo.setNoCompte("0001");
		bo.setDateInf("2022-10-24");
		bo.setDateSup("2022-10-25");
		try {
			bo.listerParDate();
		} catch (TraitementException e) {
			// TODO Auto-generated catch block
			System.out.println(MessagesDErreurs.getMessageDerreur(e.getNumber()));
		}
		
		var operations = bo.getOperationsParDate();
		operations.forEach(op -> {
			for(int i=0; i <5; i++) {
				System.out.print(op[i]+" ");
			}
			System.out.println();
		});		
		
		try {
			bo.fermerConnexion();
		} catch (TraitementException e) {
			// TODO Auto-generated catch block
			System.out.println(MessagesDErreurs.getMessageDerreur(e.getNumber()));
		}
	}
}
