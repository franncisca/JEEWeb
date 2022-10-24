package gestionErreurs;

import java.util.HashMap;

public class MessagesDErreurs {

	@SuppressWarnings("serial")
	private static HashMap<String, String> message = new HashMap<>() {{
		put("3", "Problem pour accerder a ce compte client");
		put("21", "Problem d'acces a la base de donnees");
		put("22", "Proble apres traitement");
		put("24", "Operation refusee");
	}};
	
	public static String getMessageDerreur(String number) {		
		return message.get(number);
	}
}
