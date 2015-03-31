#!/bin/sh

cd bin
rmiregistry &
sleep 0.1

rmiregistry_pid="$!"

cd ..

xterm -e "java -classpath bin rmi.server.RMIGraphNodeServer node1" &
sleep 0.1

xterm -e "java -classpath bin rmi.server.RMIGraphNodeServer node2 node1" &
sleep 0.1

xterm -e "java -classpath bin rmi.server.RMIGraphNodeServer node3 node2 node1" &
sleep 0.1

xterm -e "java -classpath bin rmi.server.RMIGraphNodeServer node4 node2" &
sleep 0.1

xterm -e "java -classpath bin rmi.server.RMIGraphNodeServer node5 node1" &
sleep 0.1

xterm -e "java -classpath bin rmi.server.RMIGraphNodeServer node6" &
sleep 0.1

echo -n "Press any key to continue... "
read var_end

kill $rmiregistry_pid
