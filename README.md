# Conception d'application réparties - Transfert de données entre objets RMI
#### Julien BRABANT - Nicolas CACHERA
###### 19/03/2015

#### Introduction

Cette application est une application permettant de transferer des données entre plusieurs objets RMI. Ces objets sont organisés sous forme d'arbre ou de graphes et sont tous dans des machines virtuelles différentes. Un client peut alors envoyer une donnée, dans l'objet de son choix, qui sera ensuite envoyer vers tous les fils, directs ou indirects, de l'objet, pour un arbre, et tous les voisins, directs ou indirects, pour un graphe.

#### Architecture

Cette application comprends 4 packages : rmi.client, rmi.server, rmi.graph et rmi.tree

Les packages rmi.client et rmi.server contiennent chacun une classe, contenant un main, pour chaque type d'organisation d'objet (arbre et graphe).

Les mains du package rmi.client agissent comme des clients qui envoient une donnée dans un des objets RMI puis affichent la trace complete de la propagation résultante de cet envoi.

Les main du package rmi.server agissent comme des serveurs et vont créer un objet RMI, soit un noeud, et le lier à un identifiant qui est le nom du noeud afin qu'il puisse être récupéré par une autre machine virtuelle.

Les package rmi.graph et rmi.server contiennent les classes permettant de définir le comportement d'un objet RMI considéré comme un noeud d'un graphe (rmi.graph) ou d'un arbre (rmi.tree).

Try/catch :
* Catch(RemoteException) dans RMITreeNodeImpl.sendDataToChildren() qui se déclenche si l'objet RMI essai d'envoyer la donnée vers un fils qui n'exsiste pas. Ce Catch est necessaire du fait qu'il se situe dans la méthode run() d'un Thread qui ne peut donc pas utiliser Throw.
'''
try { ... } catch (RemoteException e) {
	System.out.println("Remote exception when sending data to child " + finalIndice + " of " + name);
}'''


Throw :
* - RemoteException pour toutes les fonctions de RMITreeNode et RMIGraphNode et les constructeurs de RMITreeNodeImpl et RMIGraphNodeImpl. Ces Throw sont necessaires pour que ces objets soient RMI.

#### Code Samples

#### Utilisation