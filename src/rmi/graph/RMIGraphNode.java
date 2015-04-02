package rmi.graph;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * An interface of a remotable object which is able to receive data from its neighbors and send it to its neighbors.
 * @author cachera brabant
 */

public interface RMIGraphNode extends Remote {

	public void addNeighbor(RMIGraphNode neighbor) throws RemoteException;
	
	public void addNeighbors(List<RMIGraphNode> neighbors) throws RemoteException;

	public void removeNeighbor(int index) throws RemoteException;

	public void removeNeighbor(RMIGraphNode neighbor) throws RemoteException;

	/**
	 * Remove every neighbor of the RMIGraphNode.
	 */
	public void clearNeighbors() throws RemoteException;

	public List<RMIGraphNode> getNeighbors() throws RemoteException;

	public RMIGraphNode getNeighbor(int index) throws RemoteException;
	
	public String getName() throws RemoteException;
	
	/**
	 * Return a String containing the trace of the propogation since its beginning.
	 * @return The trace of the propogation.
	 * @throws RemoteException
	 */
	public String getTrace() throws RemoteException;
	
	/**
	 * Give data as an array of byte to the RMITreeNode in order to propagate to the leaves of the tree.
	 * @param data The data to propagate to the leaves of the tree.
	 * @return The trace of the entire propagation.
	 * @throws RemoteException
	 */
	public String propagate(byte[] data, List<RMIGraphNode> path) throws RemoteException;
	
	/**
	 * Send data as an array of byte to every children of the RMITreeNode.
	 * If the node hasn't any neighbors or if every neighbors are in path, put the message in the trace.
	 * @param data An array of byte containing the data to send to the children.
	 * @return The trace of the children receiving the data.
	 * @throws RemoteException
	 */
	public String sendDataToNeighbors(byte[] data, final List<RMIGraphNode> path) throws RemoteException;
}
