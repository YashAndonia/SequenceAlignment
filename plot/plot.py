
import numpy as np
import matplotlib.pyplot as plt
import math
import matplotlib.ticker as ticker
x = [4096,16384,65536,262144,524288,1048576,2097152,4194304,8388608,16777216,33554432,67108864,134217728,268435456,536870912]
t1=[]
m1=[]
t2=[]
m2=[]

logx=[math.log((i),2) for i in x]
linex=[i/1000.0 for i in x]
for i in range(len(x)):
    f1=open(r"F:\Code\JavaFiles\EclipseProject\SequenceAlignment\bin\output_"+str(x[i])+".txt")
    f2=open(r"F:\Code\JavaFiles\EclipseProject\SequenceAlignment\bin\efficient_output_"+str(x[i])+".txt")
    f1.readline()
    f1.readline()
    f2.readline()
    f2.readline()

    t1.append(float(f1.readline()))
    m1.append(float(f1.readline()))
    t2.append(float(f2.readline()))
    m2.append(float(f2.readline()))
    f1.close()
    f2.close()

plt.xticks(rotation=-30) 
plt.xlabel("size: m*n/1000")
# plt.ylabel("time: s")
plt.ylabel("memory: KB")
plt.plot(linex,m1,marker='o',mec='red',mfc='red',label='base version')
plt.plot(linex,m2,marker='*',mec='green',mfc='green',label='memory efficient version')
plt.legend()
plt.show()
