SRC_DIR := src
BUILD_DIR := build

JFX := -p lib/javafx-sdk-21.0.3/lib --add-modules ALL-MODULE-PATH
LIBS := $(JFX) -p lib --add-modules Server

J := java
JOPT := $(LIBS) -cp $(BUILD_DIR)
JC := javac
JCOPT := $(LIBS) -cp $(SRC_DIR) -d $(BUILD_DIR)

src := $(shell find $(SRC_DIR) -type f -name *.java)
.class := $(patsubst $(SRC_DIR)/%,$(BUILD_DIR)/%,$(patsubst %.java,%.class,$(src)))

P1 := de.fh.stud.p1.MyAgent_P1

MAIN := $(P1)
ARGS =

.PHONY: all build clean

all: $(.class)

$(.class): $(src)
	$(JC) $(JCOPT) $(src)

clean:
	$(RM) -rf $(.class)

clobber:
	$(RM) -rf $(BUILD_DIR)/*

server:
	$J $(JFX) -jar lib/Server.jar

run: $(.class)
	$J $(JOPT) $(MAIN) $(ARGS)
