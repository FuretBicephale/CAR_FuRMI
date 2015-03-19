# Root
java rmi/server/RMITreeNodeServer root &

# 1er etage
java rmi/server/RMITreeNodeServer node1 root &
java rmi/server/RMITreeNodeServer node2 root &

# 2eme etage
java rmi/server/RMITreeNodeServer leaf1 node1 &
java rmi/server/RMITreeNodeServer leaf2 node1 &
java rmi/server/RMITreeNodeServer leaf3 node2 &