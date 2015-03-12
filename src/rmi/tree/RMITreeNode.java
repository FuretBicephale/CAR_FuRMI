package rmi.tree;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An interface of a remotable object which is able to receive data from its father(s) and send it to its child(ren).
 * @author cachera
 */
public interface RMITreeNode extends Remote {
	
	/**
	 * Give data as an array of Byte to the RMITreeNode in order to propagate it to its children
	 * @param data
	 * @throws RemoteException
	 */
	public void propagate(Byte[] data) throws RemoteException;
	
	/**
	 * Send data as an array of Bytes to every children of the RMITreeNode.
	 * @param data An array of Bytes containing the data to send to the children.
	 * @throws RemoteException
	 */
	public void sendDataToChildren(Byte[] data) throws RemoteException;

}
