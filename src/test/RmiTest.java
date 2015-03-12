package test;

import org.junit.Before;
import org.junit.Test;

public class RmiTest {

	@Before
	public void setUp() {
		// Initialisation des objets et mise en place des liens de l'arbre
	}
	
	@Test
	public void sendDataTest() {
		// Envoi d'une donnee
		// Verifier que tous les noeuds de l'arbre ont bien recu la donnee et que cette donnee est correcte
	}
	
	@Test
	public void getFatherTest() {
		// Verifier que le pere d'un noeud est bien le pere attendu
	}
	
	@Test
	public void getRootFatherTest() {
		// Verifie que le racine n'a pas de pere
	}
	
	@Test
	public void getSonsTest() {
		// Verifier que les fils d'un noeud sont bien les fils attendu
	}
	
	@Test
	public void getLeafSonsTest() {
		// Verifie que les feuilles n'ont pas de fils
	}
	
	
}
