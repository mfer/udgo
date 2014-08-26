all:

%.o: %.cc
	g++ -c -O2 -Wall -Wextra -pedantic $<

library-objects = \

library-headers = \

library: $(library-objects)

$(library-objects): $(library-headers)

program = server 
program-objects = server.o

$(program-objects) : $(library-headers)

$(program) : $(program-objects) $(library-objects)
	g++ $^ -o $@

clean :
	rm -f $(library-objects) $(program-objects) $(program)

all : library $(program)
