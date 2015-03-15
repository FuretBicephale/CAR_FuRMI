package rmi.tree;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An interface of a remotable object which is able to receive data from its father(s) and send it to its child(ren).
 * @author cachera
 */
public interface RMITreeNode extends Remote {
	
	/**
	 * Give data as Byte to the RMITreeNode in order to propagate to the leaves of the tree
	 * @param data The data to propagate to the leaves of the tree
	 * @throws RemoteException
	 */
	public void propagate(Byte data) throws RemoteException;
	
	/**
	 * Send data as Byte to every children of the RMITreeNode.
	 * @param data A Byte containing the data to send to the children.
	 * @throws RemoteException
	 */
	public void sendDataToChildren(Byte data) throws RemoteException;

}
