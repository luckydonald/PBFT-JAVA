offline:
	mvn package -DskipTest=True -Dmaven.javadoc.skip=true -Dmaven.test.skip=true --offline

run:
	java -jar target/pbft-jar-with-dependencies.jar

it: offline run
