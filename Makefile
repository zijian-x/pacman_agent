SRC_DIR := src
BUILD_DIR := build
DIR_GUARD = @mkdir -p "$(@D)"

JFX := -p lib/javafx-sdk-21.0.3/lib --add-modules ALL-MODULE-PATH
LIBS := $(JFX) -p lib --add-modules Server

J := java
JOPT := $(LIBS) -cp $(BUILD_DIR)
JC := javac
JCOPT := $(LIBS) -cp $(SRC_DIR) -d $(BUILD_DIR)

src := $(shell find $(SRC_DIR) -type f -name *.java)
.class := $(patsubst $(SRC_DIR)/%,$(BUILD_DIR)/%,$(patsubst %.java,%.class,$(src)))

P1 := de.fh.stud.p1.MyAgent_P1
P2 := de.fh.stud.p2.BaumTest
P3 := de.fh.stud.p3.MyAgent_P3

MAIN := $(P3)
ARGS =

.PHONY: all build clean

all: $(.class)

$(.class): $(src)
	$(DIR_GUARD)
	$(JC) $(JCOPT) $(src)

clean:
	$(RM) -rf $(.class)

clobber:
	$(RM) -rf $(BUILD_DIR)/*

server:
	@$J $(JFX) -jar lib/Server.jar > /dev/null 2>&1 & disown

run: $(.class)
	$J $(JOPT) $(MAIN) $(ARGS)
