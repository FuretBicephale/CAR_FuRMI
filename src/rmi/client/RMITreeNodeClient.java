package rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmi.tree.RMITreeNode;

/**
 * A client which send data to the root of a tree of remotable objects which will be propagate to its leaves 
 * @author cachera
 */
public class RMITreeNodeClient {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMITreeNode node;
		String trace;
		
		node = (RMITreeNode)Naming.lookup("root");
		trace = node.propagate("42".getBytes());
		
		System.out.println(trace);
	
	}
	
}