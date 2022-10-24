package gestionErreurs;

@SuppressWarnings("serial")
public class TraitementException extends Exception{
	private String number;
	
	public TraitementException(String number) {
		this.number = number;
	}
	
	public String getNumber() {
		return number;
	}

}
