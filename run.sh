mvn war:war
~/Tools/apache-tomcat-9.0.44/bin/shutdown.sh
~/Tools/apache-tomcat-9.0.44/bin/startup.sh
cp target/ads.war ~/Tools/apache-tomcat-9.0.44/webapps