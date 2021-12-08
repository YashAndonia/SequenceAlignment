mkdir bin
javac -d bin ./src/normal/*.java
cd bin

#base version, output.txt will be in ./bin
java normal.SequenceAlignment ../input1.txt

#memory efficient version
# java normal.SequenceAlignmentMemoryEfficient ../input1.txt
