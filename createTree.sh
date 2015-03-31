#!/bin/sh

cd bin
rmiregistry &
sleep 0.1

rmiregistry_pid="$!"

cd ..

# Root
xterm -e "java -classpath bin rmi.server.RMITreeNodeServer root" &
sleep 0.1

# 1er etage
xterm -e "java -classpath bin rmi.server.RMITreeNodeServer node1 root" &
sleep 0.1

xterm -e "java -classpath bin rmi.server.RMITreeNodeServer node2 root" &
sleep 0.1

# 2eme etage
xterm -e "java -classpath bin rmi.server.RMITreeNodeServer leaf1 node1" & 
sleep 0.1

xterm -e "java -classpath bin rmi.server.RMITreeNodeServer leaf2 node1" &
sleep 0.1

xterm -e "java -classpath bin rmi.server.RMITreeNodeServer leaf3 node2" &
sleep 0.1

echo -n "Press any key to continue... "
read var_end

kill $rmiregistry_pid
