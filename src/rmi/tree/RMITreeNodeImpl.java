package rmi.tree;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the RMITreeNode interface. Has one father, an array of children, a trace of the propogation and a name to make it easier to understand.
 * @author cachera brabant
 */
public class RMITreeNodeImpl extends UnicastRemoteObject implements RMITreeNode {
	
	private RMITreeNode father;
	private List<RMITreeNode> children;
	
	/**
	 * Only used for the trace. Make it easier to understand.
	 */
	private String name;
	
	/**
	 * Contains the entire trace since the beginning of the propogation.
	 */
	private String trace;
	
	/**
	 * Initialize a RMITreeNodeImpl with the father father and no child. If the RMITreeNodeImpl is a root, father can be set to null.
	 * @param name A name to make the trace easier to understand
	 * @param father The father of the RMITreeNodeImpl. If it's null, the RMITreeNodeImpl is a root.
	 * @throws RemoteException
	 */
	public RMITreeNodeImpl(String name, RMITreeNode father) throws RemoteException {
		this.name = name;
		this.trace = "";
		this.father = father;
		this.children = new ArrayList<RMITreeNode>();
	}
	
	@Override
	public void setFather(RMITreeNode father) throws RemoteException {
		this.father = father;
	}

	@Override
	public void addChild(RMITreeNode child) throws RemoteException {
		this.children.add(child);
	}

	@Override
	public void removeChild(int index) throws RemoteException {
		this.children.remove(index);
	}

	@Override
	public void removeChild(RMITreeNode child) throws RemoteException {
		this.children.remove(child);
	}
	
	@Override
	public void clearChildren() throws RemoteException {
		this.children.clear();
	}
	
	@Override
	public RMITreeNode getFather() throws RemoteException {
		return this.father;
	}

	@Override
	public List<RMITreeNode> getChildren() throws RemoteException {
		return this.children;
	}

	@Override
	public RMITreeNode getChild(int index) throws RemoteException {
		return this.children.get(index);
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
		trace += sendDataToChildren(data);
		return trace;
	}

	@Override
	public String sendDataToChildren(byte[] data) throws RemoteException {
		
		this.trace = "";
		
		if(this.children.size() == 0) {
			this.trace = "Data = " + new String(data) + "\n";
			return trace;
		}
		
		for(int i = 0; i < this.children.size(); i++) {
			final int finalIndice = i;
			final byte[] finalData = data;
			
			Thread thread = new Thread() {				
				public void run() {
					try {
						trace += name + " sending to child " + children.get(finalIndice).getName() + "\n";
						trace += children.get(finalIndice).propagate(finalData);
					} catch (RemoteException e) {
						System.out.println("Remote exception when sending data to child " + finalIndice + " of " + name);
					}						
				}	
				
			};
					
			thread.run();
		}
		
		return trace;
	}

}
