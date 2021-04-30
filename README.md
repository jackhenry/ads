# ADS Project

### Running

1. Clone the repository and cd into project
```
git clone https://github.com/jackhenry/ads.git
cd ads
``` 

2. Use the `create_ads_tables.sql` script to create the necessary tables in the postgres database

3. Setup tomcat with jdbc and postgresql drivers

4. Install the maven dependencies
```
mvn install
```

5. Build the WAR
```
mvn war:war
```

6. Copy the WAR from the target directory into your tomcat webapps directory
```
cp target/ads.war path/to/tomcat/webapps/
```

7. Done