compile:
	chmod +x gradlew
	./gradlew run

run:
	chmod +x gradlew
	./gradlew shadowjar
	java -jar build/libs/compiler-all.jar programs/program2.imp output

vm: run
	chmod +x vm/maszyna-wirtualna-cln
	vm/maszyna-wirtualna-cln output

test:
	chmod +x gradlew
	./gradlew test


shadowjar:
	chmod +x gradlew
	./gradlew shadowjar

# you need these to rebuild everything
install_dependencies: install_vm
remove_dependencies: remove_vm remove_bison remove_flex remove_cln
	sudo apt autoremove -y

remove_cln:
	-cd cln-1.3.6/; sudo make uninstall
	-sudo rm -r cln-1.3.6

remove_vm:
	cd vm/ && make cleanall

remove_bison:
	sudo apt purge bison -y

remove_flex:
	sudo apt purge flex -y

install_vm: install_bison install_flex install_cln
	cd vm/ && make all

install_cln:
	# download an archive from https://www.ginac.de/CLN/cln-1.3.6.tar.bz2
	# unpack it to JFFTCompiler/cln-1.3.6
	cd cln-1.3.6/ && sudo ./configure && sudo make && sudo make check && sudo make install


install_bison:
	sudo apt install bison -y

install_flex:
	sudo apt install flex -y
