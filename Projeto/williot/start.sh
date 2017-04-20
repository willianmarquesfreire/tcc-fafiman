#! /bin/sh

#! /bin/sh
gnome-terminal -e "java -jar eureka/target/EurekaServer-0.1-SNAPSHOT.jar" --title="Eureka"
sleep 10
gnome-terminal -e "java -jar configuration/target/ConfigurationServer-0.1-SNAPSHOT.jar" --title="Config"
sleep 10
gnome-terminal -e "java -jar servertime/target/ServerTime-0.1-SNAPSHOT.jar" --title="ServerTime"
sleep 10
gnome-terminal -e "java -jar periodo/target/PeriodoServer-0.1-SNAPSHOT.jar" --title="Periodo"
sleep 10
gnome-terminal -e "java -jar entrada/target/EntradaServer-0.1-SNAPSHOT.jar" --title="Entrada"



http://www.baeldung.com/spring-cloud-netflix-hystrix

Ports:
EurekaServer: 8000
ConfigurationServer: 8001
RibbonServer: 8003
HystrixServer: 8004
HystrixDashboard: 8005
TurbineServer: 8006

ZuulServer: 9000
FeignClient: 9001
IotServer: 9002
