package rmi.graph;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIGraphNode extends Remote {

	public void addNeighbor(RMIGraphNode neighbor) throws RemoteException;

	public void removeNeighbor(int index) throws RemoteException;

	public void removeNeighbor(RMIGraphNode neighbor) throws RemoteException;

	public void clearNeighbors() throws RemoteException;

	public List<RMIGraphNode> getNeighbors() throws RemoteException;

	public RMIGraphNode getNeighbor(int index) throws RemoteException;
	
	public String getName() throws RemoteException;
	
	public String getTrace() throws RemoteException;
	
	public String propagate(byte[] data) throws RemoteException;
	
	public String sendDataToNeighbors(byte[] data) throws RemoteException;
}
