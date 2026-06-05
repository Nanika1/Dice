all: compile run

compile: $(wildcard *.java)
	javac *.java

run: $(wildcard *.class)
	java Main

clean:
	rm -f *.class
