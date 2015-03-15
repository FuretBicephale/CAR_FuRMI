package rmi.tree;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * An interface of a remotable object which is able to receive data from its father(s) and send it to its child(ren).
 * @author cachera
 */
public interface RMITreeNode extends Remote {

	public void addChild(RMITreeNode child) throws RemoteException;
	
	public void removeChild(int index) throws RemoteException;
	
	public void removeChild(RMITreeNode child) throws RemoteException;
	
	/**
	 * Remove every child of the RMITreeNode so it becomes a leaf.
	 */
	public void clearChildren() throws RemoteException;
	
	public List<RMITreeNode> getChildren() throws RemoteException;
	
	public RMITreeNode getChild(int index) throws RemoteException;
	
	/**
	 * Give data as an array of byte to the RMITreeNode in order to propagate to the leaves of the tree
	 * @param data The data to propagate to the leaves of the tree
	 * @throws RemoteException
	 */
	public void propagate(byte[] data) throws RemoteException;
	
	/**
	 * Send data as an array of byte to every children of the RMITreeNode.
	 * @param data An array of byte containing the data to send to the children.
	 * @throws RemoteException
	 */
	public void sendDataToChildren(byte[] data) throws RemoteException;

}
