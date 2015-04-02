# Conception d'application réparties - Transfert de données entre objets RMI
#### Julien BRABANT - Nicolas CACHERA
###### 19/03/2015

#### Introduction

Cette application est une application permettant de transferer des données entre plusieurs objets RMI. Ces objets sont organisés sous forme d'arbre ou de graphe et sont tous dans des machines virtuelles différentes voire des machines physiques différentes. Un client peut alors envoyer une donnée, dans l'objet de son choix, qui sera ensuite envoyée vers tous les fils, directs ou indirects, de l'objet, pour un arbre, et tous les voisins, directs ou indirects, pour un graphe.

#### Architecture

Cette application comprends quatres packages différents

Les packages rmi.client et rmi.server, contiennent chacun une classe, contenant un main, pour chaque type d'organisation d'objet (arbre et graphe).

Les mains du package rmi.client agissent comme des clients qui envoient une donnée dans un des objets RMI puis affichent la trace complete de la propagation résultante de cet envoi.

Les mains du package rmi.server agissent comme des serveurs et vont créer un objet RMI, soit un noeud, et le lier à un identifiant qui est le nom du noeud afin qu'il puisse être récupéré par une autre machine virtuelle. Ces mains s'occuperont aussi de lier les noeuds en fonction de leurs liens de parenté pour les arbres, voisinages pour les graphes.

Les packages graph et tree contiennent les classes permettant de définir le comportement d'un objet RMI considéré comme un noeud d'un graphe (graph) ou d'un arbre (tree).

Try/catch :
* Catch(RemoteException) dans RMITreeNodeImpl.sendDataToChildren() et RMIGraphNodeImpl.sendDataToChildren() qui se déclenche si l'objet RMI essai d'envoyer la donnée vers un fils qui n'existe pas. Ce Catch est necessaire du fait qu'il se situe dans la méthode run() d'un Thread qui ne peut donc pas utiliser Throw.

```
try { ... } catch (RemoteException e) {
	System.out.println("Remote exception when sending data to child/neighbor " + finalIndice + " of " + name);
}
```

* Catch(RemoteException) dans RMITreeNodeServer.main() et RMIGraphNodeServer.main() qui se déclenche si l'on essaye d'accéder à un registry non existant. Dans ce cas, on le crée.
```
try { ... } catch(RemoteException e) {
	LocateRegistry.createRegistry(1099);			
}
```

Throw :
* RemoteException pour toutes les fonctions de RMITreeNode et RMIGraphNode et les constructeurs de RMITreeNodeImpl et RMIGraphNodeImpl. Ces Throw sont necessaires pour que ces objets soient RMI.
* MalformedURLException, RemoteException, NotBoundException pour les mains des classes des packages server et client. MalformedURLException est lancée lorsque le nom fourni dans rebind ou lookup n'est pas dans le bon format, RemoteException est l'exception lancée par les objets RMI, et NotBoundException est lancée si lookup essaie d'acceder à un objet inexistant.

#### Code Samples

Propagation du message data à partir d'un noeud. La variable path contient tous les noeuds ayant déjà reçu le message sur le chemin courant pour éviter les boucles infinies. 

```
@Override
public String propagate(byte[] data, List<RMIGraphNode> path) throws RemoteException {
	
	// Add this to path in order to avoid infinite loop
	path.add(this);
	
	this.trace = name + " is propagating...\n";
	trace += sendDataToNeighbors(data, path);
	
	return trace;
	
}
```

Envoi du message data à tous les voisins d'un noeud. Si un voisin est dans la list path, rien ne lui est envoyé. Si le noeud n'a pas de voisin ou si tous ses voisins on déjà reçu le message, le noeud affiche le message.

```
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
		
		[...] // Thread
	
	}
	
	// If every neighbors have received the data, we end the path
	if(stop) {
		this.trace = "Data = " + new String(data) + "\n";
		return trace;
	}

	return trace;
}
```

Thread envoyant le message data au voisin finalIndice d'un noeud.

```
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
```

Client de notre application pour les graphes. Envoi la donnée "42" à un noeud choisi par l'utilisateur et affiche la trace complète.

```
public class RMIGraphNodeClient {

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		RMIGraphNode node;
		List<RMIGraphNode> path = new ArrayList<RMIGraphNode>();
		String trace;
		
		if(args.length != 1) {
			System.err.println("Usage error : RMITreeNodeServer rootName");
			System.exit(1);
		}
		
		node = (RMIGraphNode)Naming.lookup(args [0]);
		trace = node.propagate("42".getBytes(), path);
		
		System.out.println(trace);
	}
}
```

Serveur de notre application pour les graphes. Crée un noeud de graphe avec un nom et des voisins définis par l'utilisateur

```
public class RMIGraphNodeServer {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

		RMIGraphNode node;

		try {
			LocateRegistry.getRegistry(1099);			
		} catch(RemoteException e) {
			LocateRegistry.createRegistry(1099);			
		}

		if(args.length == 0) {
			System.err.println("Usage error : RMITreeNodeServer nodeName (neighborsName)*");
			System.exit(1);
		}
		
		if(args.length == 1) {
			node = new RMIGraphNodeImpl(args[0], null);
		} else {
			node = new RMIGraphNodeImpl(args[0], null);
			for(int i = 1; i < args.length; i++) {
				RMIGraphNode neighbor = (RMIGraphNode)Naming.lookup(args[i]);
				node.addNeighbor(neighbor);
				neighbor.addNeighbor(node);				
			}
		}

		Naming.rebind(args[0], node);

	}

}
```

#### Utilisation

Script pour générer une structure d'arbre : createTree.sh.

Script pour générer une structure de graphe : createGraph.sh.

Ces deux scripts sont nécessaire pour que l'application fonctionne, ainsi que les tests. Si ils ne sont pas lancés, il n'y aura pas d'objets RMI existants.

Client pour les arbres : RMITreeNodeClient (rootName)  
Par défaut, propage une donnée à partir de la racine de l'arbre, si précisé, propage une donnée à partir du noeud rootName.

Client pour les graphes : RMIGraphNodeClient rootName  
Propage une donnée dans le graphe à partir du noeud rootName.

Test pour les arbres : RMITreeNodeTest.  
Test pour les graphes : RMIGraphNodeTest.
