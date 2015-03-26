package client;

import graph.RMIGraphNode;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * A client which send data to the node of your choice of remotable objects which will be propagate to its leaves 
 * @author cachera brabant
 */
public class RMIGraphNodeClient {

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		RMIGraphNode node;
		List<RMIGraphNode> path = new ArrayList<RMIGraphNode>();
		String trace;
		
		node = (RMIGraphNode)Naming.lookup(args [0]);
		trace = node.propagate("42".getBytes(), path);
		
		System.out.println(trace);
	}
}
