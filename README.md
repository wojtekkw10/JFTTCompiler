# Compiler
Simple compiler for JFTT class at the Wrocław University of Science and Technology

## Dependencies
By default the file 'input' is used as input for the compiler. 
'output' is the output file of the compiler and the input file for the VM.
### openjdk 11.0.5-ea 2019-10-15
```shell script
sudo apt install openjdk-11-jdk
```
### cln 1.3.5
```shell script
cd cln-directory
./configure
sudo make install
```

## To build and run the compiler, and then the vm
```shell script
make vm
```

## To build and run jar file
```shell script
make run
```
