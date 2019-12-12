compile:
	./gradlew run

run:
	./gradlew shadowjar
	java -jar build/libs/compiler-all.jar input output

vm: run
	vm/maszyna-wirtualna output

test:
	./gradlew test

