import random

_f = None


def init_log():
    global _f
    _f = open("results/{}-result.txt".format(random.randint(1, 999999)), "w+")


def log(msg):
    global _f
    _f.write(msg)
    _f.write("\n")
    _f.flush()
