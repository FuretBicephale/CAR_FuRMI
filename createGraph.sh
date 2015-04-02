#!/bin/sh

xterm -e "java -jar RMIGraphNodeServer.jar node1" &
sleep 1

xterm -e "java -jar RMIGraphNodeServer.jar node2 node1" &
sleep 1

xterm -e "java -jar RMIGraphNodeServer.jar node3 node2 node1" &
sleep 1

xterm -e "java -jar RMIGraphNodeServer.jar node4 node2" &
sleep 1

xterm -e "java -jar RMIGraphNodeServer.jar node5 node1" &
sleep 1

xterm -e "java -jar RMIGraphNodeServer.jar node6" &
sleep 1