package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import tree.RMITreeNode;

/**
 * A client which send data to the root of a tree or the node of your choice (if specified) of remotable objects which will be propagate to its leaves 
 * @author cachera brabant
 */
public class RMITreeNodeClient {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMITreeNode node;
		String trace;
		
		if(args.length == 1) {
			node = (RMITreeNode)Naming.lookup(args[0]);
			
		} else {
			node = (RMITreeNode)Naming.lookup("root");			
		}
		trace = node.propagate("42".getBytes());
		
		System.out.println(trace);
	
	}
	
}
