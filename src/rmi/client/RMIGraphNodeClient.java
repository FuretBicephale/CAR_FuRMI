package rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import rmi.graph.RMIGraphNode;

public class RMIGraphNodeClient {

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		RMIGraphNode node;
		List<RMIGraphNode> path = null;
		String trace;
		
		node = (RMIGraphNode)Naming.lookup(args [0]);
		trace = node.propagate("42".getBytes(), path);
		
		System.out.println(trace);
	}
}
