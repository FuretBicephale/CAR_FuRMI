package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import rmi.graph.RMIGraphNode;


public class RMIGraphNodeTest {

	@Test
	public void sendDataTest() {
		// Envoi d'une donnee
		// Verifier que tous les noeuds du graphes ont bien recu la donnee et que cette donnee est correcte
		// Verifie aussi que les noeuds non connectes a la source n'ont pas recu la donnee
		String data = "Data = 42\n";
		String propagate = "is propagating...\n";
		List<RMIGraphNode> path = new ArrayList<RMIGraphNode>();
		try {
			RMIGraphNode node = (RMIGraphNode)Naming.lookup("node2");
			String trace = node.propagate("42".getBytes(), path);
			System.out.println(trace);
			assertTrue(trace.contains("node2 " + propagate));
			assertTrue(trace.contains("node1 " + propagate));
			assertTrue(trace.contains("node3 " + propagate));
			assertTrue(trace.contains("node4 " + propagate));
			assertTrue(trace.contains("node4 " + propagate));
			assertTrue(trace.contains(data));
			assertFalse(trace.contains("node6 " + propagate));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void getNeighborsTest() {
		// Verifier que les voisins d'un noeud sont bien les voisins attendus
		try {
			RMIGraphNode node = (RMIGraphNode)Naming.lookup("node2");
			List<RMIGraphNode> neighbors = node.getNeighbors();
			assertEquals(3, neighbors.size());
			assertTrue(neighbors.contains((RMIGraphNode)Naming.lookup("node1")));
			assertTrue(neighbors.contains((RMIGraphNode)Naming.lookup("node3")));
			assertTrue(neighbors.contains((RMIGraphNode)Naming.lookup("node4")));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
}
