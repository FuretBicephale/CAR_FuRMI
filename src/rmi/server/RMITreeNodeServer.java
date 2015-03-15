package rmi.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import rmi.tree.RMITreeNode;
import rmi.tree.RMITreeNodeImpl;

/**
 * A server which creates a remotable tree node object which receives data from its father and sends it to its children.
 * @author cachera
 */
public class RMITreeNodeServer {

	public static void main(String[] args) throws MalformedURLException, RemoteException, AlreadyBoundException {
		
		RMITreeNode node = new RMITreeNodeImpl(null);
		Naming.bind("root", node);
		
	}
	
}
