offline:
	mvn package -DskipTest=True -Dmaven.javadoc.skip=true -Dmaven.test.skip=true

run: offline
	java -jar target/pbft-jar-with-dependencies.jar
