package server;

import graph.RMIGraphNode;
import graph.RMIGraphNodeImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * A server which creates a remotable graph node object which receives data from its neighbors and sends it to its neighbors.
 * @author cachera brabant
 */
public class RMIGraphNodeServer {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

		RMIGraphNode node;

		if(args.length == 1) {
			node = new RMIGraphNodeImpl(args[0], null);
		} else {
			node = new RMIGraphNodeImpl(args[0], null);
			for(int i = 1; i < args.length; i++) {
				RMIGraphNode neighbor = (RMIGraphNode)Naming.lookup(args[i]);
				node.addNeighbor(neighbor);
				neighbor.addNeighbor(node);				
			}
		}

		Naming.rebind(args[0], node);

	}

}
