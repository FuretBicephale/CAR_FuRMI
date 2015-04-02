package rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import rmi.tree.RMITreeNode;
import rmi.tree.RMITreeNodeImpl;

/**
 * A server which creates a remotable tree node object which receives data from its father and sends it to its children.
 * @author cachera brabant
 */
public class RMITreeNodeServer {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMITreeNode node;

		try {
			LocateRegistry.getRegistry(1099);			
		} catch(RemoteException e) {
			LocateRegistry.createRegistry(1099);			
		}
		
		if(args.length == 0 || args.length > 2) {
			System.err.println("Usage error : RMITreeNodeServer nodeName (fatherName)");
			System.exit(1);
		}
		
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
