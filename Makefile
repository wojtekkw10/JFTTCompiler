compile:
	./gradlew run

jar:
	./gradlew shadowjar
	java -jar build/libs/compiler-all.jar input output

run: jar
	vm/maszyna-wirtualna output

