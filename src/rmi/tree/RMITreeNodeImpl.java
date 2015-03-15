package rmi.tree;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the RMITreeNode interface. Has one father and an array of children.
 * @author cachera
 */
public class RMITreeNodeImpl extends UnicastRemoteObject implements RMITreeNode {
	
	private RMITreeNode father;
	private List<RMITreeNode> children;
	
	/**
	 * Initialize a RMITreeNodeImpl with the father father and no child. If the RMITreeNodeImpl is a root, father can be set to null.
	 * @param father The father of the RMITreeNodeImpl. If it's null, the RMITreeNodeImpl is a root.
	 * @throws RemoteException
	 */
	public RMITreeNodeImpl(RMITreeNode father) throws RemoteException {
		this.father = father;
		this.children = new ArrayList<RMITreeNode>();
	}
	
	/**
	 * Set the father of the RMITreeNodeImpl. If it's set to null, the RMITreeNodeImpl becomes a root.
	 * @param father The father of the RMITreeNodeImpl. If it's null, the RMITreeNodeImpl is a root.
	 */
	public void setFather(RMITreeNodeImpl father) {
		this.father = father;
	}

	@Override
	public void addChild(RMITreeNode child) {
		this.children.add(child);
	}

	@Override
	public void removeChild(int index) {
		this.children.remove(index);
	}

	@Override
	public void removeChild(RMITreeNode child) {
		this.children.remove(child);
	}
	
	@Override
	public void clearChildren() {
		this.children.clear();
	}
	
	public RMITreeNode getFather() {
		return this.father;
	}

	@Override
	public List<RMITreeNode> getChildren() {
		return this.children;
	}

	@Override
	public RMITreeNode getChild(int index) {
		return this.children.get(index);
	}

	@Override
	public void propagate(byte[] data) throws RemoteException {
		System.out.println("Propagate...");
		sendDataToChildren(data);
	}

	@Override
	public void sendDataToChildren(byte[] data) throws RemoteException {
		
		if(this.children.size() == 0) {
			System.out.println(data);
			return;
		}
		
		for(int i = 0; i < this.children.size(); i++) {
			System.out.println("Send to child " + i + "...");
			this.children.get(i).propagate(data);
		}
	}

}
