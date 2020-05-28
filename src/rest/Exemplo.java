package rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


@Path("/cesar")
public class Exemplo {
	
	private ArrayList<String> alphabetList = new ArrayList<String>() {
		{
			add("a");
			add("b");
			add("c");
			add("d");
			add("e");
			add("f");
			add("g");
			add("h");
			add("i");
			add("j");
			add("k");
			add("l");
			add("m");
			add("n");
			add("o");
			add("p");
			add("q");
			add("r");
			add("s");
			add("t");
			add("u");
			add("v");
			add("w");
			add("x");
			add("y");
			add("z");
		}
	};
	
	
	private HashMap<String, Integer> alphabetMap = new HashMap<String, Integer>() {
		{
			put("a", 0);
			put("b", 1);
			put("c", 2);
			put("d", 3);
			put("e", 4);
			put("f", 5);
			put("g", 6);
			put("h", 7);
			put("i", 8);
			put("j", 9);
			put("k", 10);
			put("l", 11);
			put("m", 12);
			put("n", 13);
			put("o", 14);
			put("p", 15);
			put("q", 16);
			put("r", 17);
			put("s", 18);
			put("t", 19);
			put("u", 20);
			put("v", 21);
			put("w", 22);
			put("x", 23);
			put("y", 24);
			put("z", 25);
		}
	}; 
		
	public ArrayList<String> getAlphabetList() {
		return this.alphabetList;
	}
	
	public HashMap<String, Integer> getAlphabetMap() {
		return this.alphabetMap;
	}
	
	private boolean verifySpecialChar(char specialChar) {
		String specialString = String.valueOf(specialChar);
		Pattern regex = Pattern.compile("[^A-Za-z0-9]");
		Matcher matcher = regex.matcher(specialString);
		boolean match = matcher.matches();
		return !match;
	}
	
	private String cipherChar(char charToCipher, int cipherKey) {
		String strToCipher = String.valueOf(charToCipher);
		int strValue = this.getAlphabetMap().get(strToCipher);
		int strCipherIndex = (strValue + cipherKey) % 26;
		return this.getAlphabetList().get(strCipherIndex);
		
	}
	
	private String decipherChar(char charToCipher, int cipherKey) {
		String strToCipher = String.valueOf(charToCipher);
		int strValue = this.getAlphabetMap().get(strToCipher);
		int strCipherIndex = (((strValue - cipherKey) % 26) + 26) % 26;
		return this.getAlphabetList().get(strCipherIndex);
	}
	
	public String cipher(String text, int cipherKey) {
		
		String cipheredText = "";
		
		for(int i = 0; i < text.length(); i++) {
			char charNormal = text.charAt(i);
			if (!Character.isWhitespace(charNormal)) {
				boolean specialChar = !verifySpecialChar(charNormal) == false;
				if (specialChar) {
					if (Character.isUpperCase(charNormal)) {
						String cipheredChar = cipherChar(Character.toLowerCase(charNormal), cipherKey);
						cipheredText += Character.toUpperCase(cipheredChar.charAt(0));	
					} else 	{
						String cipheredChar = cipherChar(charNormal, cipherKey);
						cipheredText += cipheredChar;
					}
				} else {
					String specialString = String.valueOf(charNormal);
					cipheredText += specialString;
				}
				
			} else {
				
				cipheredText += " ";
				
			}
		}
		return cipheredText;
	}
	
	public String decipher(String text, int cipherKey) {
		
		String decipheredText = "";
		
		for(int i = 0; i < text.length(); i++) {
			char charNormal = text.charAt(i);
			if (!Character.isWhitespace(charNormal)) {
				boolean specialChar = !verifySpecialChar(charNormal) == false;
				if (specialChar) {
					if (Character.isUpperCase(charNormal)) {
						String cipheredChar = decipherChar(Character.toLowerCase(charNormal), cipherKey);
						decipheredText += Character.toUpperCase(cipheredChar.charAt(0));	
					} else 	{
						String cipheredChar = decipherChar(charNormal, cipherKey);
						decipheredText += cipheredChar;
					}
				
				} else {
					String specialString = String.valueOf(charNormal);
					decipheredText += specialString;
				}
				
			} else {
				decipheredText += " ";	
			}
			
		}
		return decipheredText;
	}
	
	@GET
	@Produces("text/plain")
	public String cesar(@QueryParam("word") String palavra, @QueryParam("key") int chave, @QueryParam("cipher") boolean cphr) {
		if (cphr) {
			return "criptografado:" + this.cipher(palavra, chave);
		} else {
			return "descriptografado:" + this.decipher(palavra, chave);
		}
	}
}
