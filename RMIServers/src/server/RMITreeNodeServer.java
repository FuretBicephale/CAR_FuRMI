package server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import tree.RMITreeNode;
import tree.RMITreeNodeImpl;

/**
 * A server which creates a remotable tree node object which receives data from its father and sends it to its children.
 * @author cachera brabant
 */
public class RMITreeNodeServer {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMITreeNode node;
		
		if(args.length == 1) {
			node = new RMITreeNodeImpl(args[0], null);
		} else {
			RMITreeNode father = (RMITreeNode)Naming.lookup(args[1]);
			node = new RMITreeNodeImpl(args[0], father);
			father.addChild(node);
		}
		
		Naming.rebind(args[0], node);
		
	}
	
}
