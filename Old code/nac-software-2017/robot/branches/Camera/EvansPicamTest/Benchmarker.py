import time
class Benchmarker:
    # benchmarking
    benchmarks = {}
    old = 0.0
    start = 0.0
    
    def __init__(self):
        self.benchmarks = {}
        self.old = 0.0
        self.start = 0.0
    
    # float representation, no scientific notation
    def fltstr(self, x):
        return '{0:f}'.format(x)
    
    def init(self):
        self.start = time.clock()
        self.old = self.start
    def add(self, name):
        self.benchmarks[name] = time.clock() - self.old
        self.old = time.clock()
    def str(self):
        s = "TIMESTAMPS:" + "\n"
        for k, v in self.benchmarks.iteritems():
            s += str(k) + ": " + self.fltstr(v) + "\n"
        return s
    def finish(self):
        self.benchmarks["Total"] = time.clock() - self.start