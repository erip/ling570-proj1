#!/usr/bin/env python3

from sys import argv

def generate_tokens(filename):
    tokens = []
    with open(filename, 'r') as f:
        for line in f:
            for tok in line.split():
                tokens.append(tok)
    return tokens

if __name__ == '__main__':
    file = argv[1]
    tokens = generate_tokens(file)
    print("{0} has {1} tokens".format(file, len(tokens)))
