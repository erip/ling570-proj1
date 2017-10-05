#!/usr/bin/env python3

from sys import argv

def get_nonempty_lines(filename):
    lines = []
    with open(filename, 'r') as f:
        for line in f:
            if line.strip() != "":
                lines.append(line)
    return lines

if __name__ == '__main__':
    file = argv[1]
    lines = get_nonempty_lines(file)
    print("{0} has {1} non-empty lines".format(file, len(lines)))
