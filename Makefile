compile:
	./gradlew run

run:
	./gradlew shadowjar
	java -jar build/libs/compiler-all.jar programs/program2.imp output

vm: run
	vm/maszyna-wirtualna-cln output

test:
	./gradlew test


shadowjar:
	./gradlew shadowjar