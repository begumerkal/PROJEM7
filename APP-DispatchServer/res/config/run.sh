LIB_PATH=.
for i in ../lib/*.jar; do
LIB_PATH=${LIB_PATH}:$i
done

for i in ../serverLib/*.jar; do
LIB_PATH=${LIB_PATH}:$i
done


<<<<<<< HEAD
$JAVA_HOME/bin/java -server -Xmx512m -XX:MaxDirectMemorySize=128M -verbose:gc -Djava.awt.headless=true -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.EPollSelectorProvider -classpath .:$CLASSPATH:$LIB_PATH:dddDispatchServer.jar com.app.dispatch.DisServer >> stdout.log 2>&1 &
=======
$JAVA_HOME/bin/java -server -Xmx512m -XX:MaxDirectMemorySize=128M -verbose:gc -Djava.awt.headless=true -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.EPollSelectorProvider -classpath .:$CLASSPATH:$LIB_PATH:dddDispatchServer.jar com.app.DisServer.Main >> stdout.log 2>&1 &
>>>>>>> dd58a2b0f872ce8c8de6889acaba4efd61a11f47

echo $! > ddddispatchserver.pid
