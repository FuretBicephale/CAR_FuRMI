package rmi.graph;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMIGraphNodeImpl extends UnicastRemoteObject implements RMIGraphNode{

	private List<RMIGraphNode> neighbors;
	private String name;
	private String trace;

	protected RMIGraphNodeImpl(String name, List<RMIGraphNode> neighbors) throws RemoteException {
		this.name = name;
		this.trace = "";
		this.neighbors = neighbors;
	}

	@Override
	public void addNeighbor(RMIGraphNode neighbor) throws RemoteException {
		this.neighbors.add(neighbor);
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
	public String propagate(byte[] data) throws RemoteException {
		this.trace = name + " is propagating...\n";
		trace += sendDataToNeighbors(data);
		return trace;
	}

	@Override
	public String sendDataToNeighbors(byte[] data) throws RemoteException {
		this.trace = "";

		if(this.neighbors.size() == 0) {
			this.trace = "Data = " + new String(data) + "\n";
			return trace;
		}

		for(int i = 0; i < this.neighbors.size(); i++) {
			final int finalIndice = i;
			final byte[] finalData = data;

			Thread thread = new Thread() {				
				public void run() {
					try {
						trace += "Sending to child " + neighbors.get(finalIndice).getName() + "\n";
						trace += neighbors.get(finalIndice).propagate(finalData);
					} catch (RemoteException e) {
						System.out.println("Remote exception when sending data to neighbor " + finalIndice + " of " + name);
					}						
				}	

			};

			thread.run();
		}

		return trace;
	}

}
