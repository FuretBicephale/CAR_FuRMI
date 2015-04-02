package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.rmi.Naming;
import java.util.List;

import org.junit.Test;

import rmi.tree.RMITreeNode;

public class RMITreeNodeTest {
	
	@Test
	public void sendDataTest() {
		// Envoi d'une donnee
		// Verifier que tous les noeuds de l'arbre ont bien recu la donnee et que cette donnee est correcte
		String data = "Data = 42\n";
		String propagate = "is propagating...\n";
		try {
			RMITreeNode node = (RMITreeNode)Naming.lookup("root");
			String trace = node.propagate("42".getBytes());
			assertTrue(trace.contains("root " + propagate));
			assertTrue(trace.contains("node1 " + propagate));
			assertTrue(trace.contains("node2 " + propagate));
			assertTrue(trace.contains("leaf1 " + propagate));
			assertTrue(trace.contains("leaf2 " + propagate));
			assertTrue(trace.contains("leaf3 " + propagate));
			assertTrue(trace.contains(data));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void getFatherTest() {
		// Verifier que le pere d'un noeud est bien le pere attendu
		try {
			RMITreeNode node = (RMITreeNode)Naming.lookup("node1");
			assertEquals((RMITreeNode)Naming.lookup("root"), node.getFather());
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void getRootFatherTest() {
		// Verifie que le racine n'a pas de pere
		try {
			RMITreeNode node = (RMITreeNode)Naming.lookup("root");
			assertEquals(null, node.getFather());
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void getSonsTest() {
		// Verifier que les fils d'un noeud sont bien les fils attendu
		try {
			RMITreeNode node = (RMITreeNode)Naming.lookup("root");
			List<RMITreeNode> children = node.getChildren();
			assertEquals(2, children.size());
			assertTrue(children.contains((RMITreeNode)Naming.lookup("node1")));
			assertTrue(children.contains((RMITreeNode)Naming.lookup("node2")));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void getLeafSonsTest() {
		// Verifie qu'une feuille n'a pas de fils
		try {
			RMITreeNode node = (RMITreeNode)Naming.lookup("leaf1");
			assertEquals(0, node.getChildren().size());
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	
}
