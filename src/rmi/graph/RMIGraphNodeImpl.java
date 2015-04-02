package rmi.graph;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the RMIGraphNode interface. Has a list of neighbors, a trace of the propogation and a name to make it easier to understand.
 * @author cachera brabant
 */
public class RMIGraphNodeImpl extends UnicastRemoteObject implements RMIGraphNode{

	private List<RMIGraphNode> neighbors;
	
	/**
	 * Only used for the trace. Make it easier to understand.
	 */
	private String name;
	
	/**
	 * Contains the entire trace since the beginning of the propogation.
	 */
	private String trace;

	/**
	 * Initialize a RMIGraphNodeImpl with his neighbors. RMIGraphNodeImpl can be set to null.
	 * @param name A name to make the trace easier to understand
	 * @param neighbors The neighbors of the RMIGraphNodeImpl. If it's null, the RMIGraphNode doesn't have neighbors.
	 * @throws RemoteException
	 */
	public RMIGraphNodeImpl(String name, List<RMIGraphNode> neighbors) throws RemoteException {
		this.name = name;
		this.trace = "";
		if(neighbors != null) {
			this.neighbors = neighbors;			
		} else {
			this.neighbors = new ArrayList<RMIGraphNode>();
		}
	}

	@Override
	public void addNeighbor(RMIGraphNode neighbor) throws RemoteException {
		this.neighbors.add(neighbor);
	}
	
	@Override
	public void addNeighbors(List<RMIGraphNode> neighbors) throws RemoteException {
		this.neighbors.addAll(neighbors);
	}

	@Override
	public void removeNeighbor(int index) throws RemoteException {
		this.neighbors.remove(index);
	}

	@Override
	public void removeNeighbor(RMIGraphNode neighbor) throws RemoteException {
		this.neighbors.remove(neighbor);
	}

	@Override
	public void clearNeighbors() throws RemoteException {
		this.neighbors.clear();
	}

	@Override
	public List<RMIGraphNode> getNeighbors() throws RemoteException {
		return this.neighbors;
	}

	@Override
	public RMIGraphNode getNeighbor(int index) throws RemoteException {
		return this.neighbors.get(index);
	}

	@Override
	public String getName() throws RemoteException {
		return this.name;
	}

	@Override
	public String getTrace() throws RemoteException {
		return this.trace;
	}
	
	@Override
	public String propagate(byte[] data, List<RMIGraphNode> path) throws RemoteException {
		
		// Add this to path in order to avoid infinite loop
		path.add(this);
		
		this.trace = name + " is propagating...\n";
		trace += sendDataToNeighbors(data, path);
		
		return trace;
	
	}

	@Override
	public String sendDataToNeighbors(byte[] data, final List<RMIGraphNode> path) throws RemoteException {

		boolean stop = true;
		
		this.trace = "";

		for(int i = 0; i < this.neighbors.size(); i++) {
			final int finalIndice = i;
			final byte[] finalData = data;

			// If the neighbors have already received the data, ignore it
			if(path.contains(this.neighbors.get(i))) {
				continue;
			} else {
				stop = false;
			}
			
			Thread thread = new Thread() {				
				public void run() {
					try {
						trace += name + " sending to child " + neighbors.get(finalIndice).getName() + "\n";
						trace += neighbors.get(finalIndice).propagate(finalData, path);
					} catch (RemoteException e) {
						System.out.println("Remote exception when sending data to neighbor " + finalIndice + " of " + name);
					}						
				}	

			};

			thread.run();
		}
		
		// If every neighbors have received the data, we end the path
		if(stop) {
			this.trace = "Data = " + new String(data) + "\n";
			return trace;
		}

		return trace;
	}

}
