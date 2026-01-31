JAR=lib/json-20240303.jar
OUT_DIR=out
# Comment this line to run with default java
#JAVA_PATH=/opt/homebrew/opt/openjdk@11/bin/

JAVAC=javac
JAVA=java
LIB_DIR=lib
# Directory where spark is installed
SPARK_FOLDER=/home/tomi/spark
# Extra libraries to include, replace with your own .jars
CLASSPATH=$(OUT_DIR):$(LIB_DIR)/json-20240303.jar:$(SPARK_FOLDER)/jars/*

SOURCES=$(shell find src -name "*.java")

all: build

build:
	$(JAVA_PATH)$(JAVAC) -cp $(CLASSPATH) -d out $(SOURCES)

run:
	$(JAVA_PATH)$(JAVA) -cp $(CLASSPATH) FeedReaderMain
	
run-ne:
	$(JAVA_PATH)$(JAVA) -cp $(CLASSPATH) FeedReaderMain -ne

clean:
	rm -rf $(OUT_DIR)
