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
* Catch(RemoteException) dans RMITreeNodeImpl.sendDataToChildren() et RMIGraphNodeImpl.sendDataToChildren() qui se déclenche si l'objet RMI essai d'envoyer la donnée vers un fils qui n'exsiste pas. Ce Catch est necessaire du fait qu'il se situe dans la méthode run() d'un Thread qui ne peut donc pas utiliser Throw.

```
try { ... } catch (RemoteException e) {
	System.out.println("Remote exception when sending data to child/neighbor " + finalIndice + " of " + name);
}
```


Throw :
* - RemoteException pour toutes les fonctions de RMITreeNode et RMIGraphNode et les constructeurs de RMITreeNodeImpl et RMIGraphNodeImpl. Ces Throw sont necessaires pour que ces objets soient RMI.
* - MalformedURLException, RemoteException, NotBoundException pour les mains des classes des packages server et client. MalformedURLException est lancée lorsque le nom fourni dans rebind ou lookup n'est pas dans le bon format, RemoteException est l'exception lancée par les objets RMI, et NotBoundException est lancée si lookup essaie d'acceder à un objet inexistant.

#### Code Samples

#### Utilisation

Script pour générer une structure d'arbre : createTree.sh.
Script pour générer une structure de graphe : createGraph.sh.

Ces deux scripts sont nécessaire pour que l'application fonctionne, ainsi que les tests. Si ils ne sont pas lancés, il n'y aura pas d'objets RMI existants.

Client pour les arbres : RMITreeNodeClient (rootName)
Par défaut, propage une donnée à partir de la racine de l'arbre, si précisé, propage une donnée à partir du noeud rootName.

Client pour les graphes : RMIGraphNodeClient rootName
Propage une donnée dans le graphe à partir du noeud rootName.
