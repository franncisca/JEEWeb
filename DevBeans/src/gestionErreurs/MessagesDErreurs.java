package gestionErreurs;

import java.util.HashMap;

public class MessagesDErreurs {

	@SuppressWarnings("serial")
	private static HashMap<String, String> message = new HashMap<>() {{
		put("3",  "Problème pour accérder a ce compte client, vérifiez au'il est bien valide");
		put("21", "Problème d'accès à la base de données, veuillez le signaler à votre administrateur");
		put("22", "Proble après traitement. Le traitement a été effectué correctement mais il y a eu un problème à signaler à votre administrateur");
		put("24", "Operation refusée, débit demandé supérieur au crédit du compte");
	}};
	
	public static String getMessageDerreur(String number) {		
		return message.get(number);
	}
}
