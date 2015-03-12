package rmi.tree;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the RMITreeNode interface. Has one father and an array of children.
 * @author cachera
 */
public class RMITreeNodeImpl implements RMITreeNode {
	
	private RMITreeNodeImpl father;
	private List<RMITreeNodeImpl> children;
	
	/**
	 * Initialize a RMITreeNodeImpl with the father father and no child. If the RMITreeNodeImpl is a root, father can be set to null.
	 * @param father The father of the RMITreeNodeImpl. If it's null, the RMITreeNodeImpl is a root.
	 * @throws RemoteException
	 */
	public RMITreeNodeImpl(RMITreeNodeImpl father) throws RemoteException {
		this.father = father;
		this.children = new ArrayList<RMITreeNodeImpl>();
	}
	
	/**
	 * Set the father of the RMITreeNodeImpl. If it's set to null, the RMITreeNodeImpl becomes a root.
	 * @param father The father of the RMITreeNodeImpl. If it's null, the RMITreeNodeImpl is a root.
	 */
	public void setFather(RMITreeNodeImpl father) {
		this.father = father;
	}
	
	public void addChild(RMITreeNodeImpl child) {
		this.children.add(child);
	}
	
	public void removeChild(int index) {
		this.children.remove(index);
	}
	
	public void removeChild(RMITreeNodeImpl child) {
		this.children.remove(child);
	}
	
	/**
	 * Remove every child of the RMITreeNode so it becomes a leaf.
	 */
	public void clearChildren() {
		this.children.clear();
	}
	
	public RMITreeNodeImpl getFather() {
		return this.father;
	}
	
	public List<RMITreeNodeImpl> getChildren() {
		return this.children;
	}
	
	public RMITreeNodeImpl getChild(int index) {
		return this.children.get(index);
	}

	/* (non-Javadoc)
	 * @see rmi.tree.RMITreeNode#receiveDataFromFather()
	 */
	@Override
	public void propagate(Byte[] data) throws RemoteException {
		sendDataToChildren(data);
	}

	/* (non-Javadoc)
	 * @see rmi.tree.RMITreeNode#sendDataToChildren(java.lang.Byte[])
	 */
	@Override
	public void sendDataToChildren(Byte[] data) throws RemoteException {
		for(int i = 0; i < children.size(); i++) {
			children.get(i).propagate(data);
		}
	}

}
