#!/bin/sh

# Root
xterm -e "java -jar RMITreeNodeServer.jar root" &
sleep 1

# 1er etage
xterm -e "java -jar RMITreeNodeServer.jar node1 root" &
sleep 1

xterm -e "java -jar RMITreeNodeServer.jar node2 root" &
sleep 1

# 2eme etage
xterm -e "java -jar RMITreeNodeServer.jar leaf1 node1" & 
sleep 1

xterm -e "java -jar RMITreeNodeServer.jar leaf2 node1" &
sleep 1

xterm -e "java -jar RMITreeNodeServer.jar leaf3 node2" &
sleep 1