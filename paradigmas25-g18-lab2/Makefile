JAR=lib/json-20240303.jar
OUT_DIR=out

all: compile

compile:
	find . -name "*.java" > sources.txt
	mkdir -p $(OUT_DIR)
	javac -cp $(JAR) -d $(OUT_DIR) @sources.txt

run:
	java -cp $(OUT_DIR):$(JAR) FeedReaderMain

run-ne:
	java -cp $(OUT_DIR):$(JAR) FeedReaderMain -ne

clean:
	rm -rf $(OUT_DIR) sources.txt