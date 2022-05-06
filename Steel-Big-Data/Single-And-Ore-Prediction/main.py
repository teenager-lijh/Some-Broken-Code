import torch
import sklearn
import numpy as np
from tools.reader import CsvReader
import matplotlib.pyplot as plt
import csv

'''
只研究第一列的数据
'''

file_name = './data/data.csv'
reader = CsvReader(file_name)
all_data = reader.get_all_data()

del all_data[0]

feature = []
for line in all_data[0:5]:
    feature.append(float(line[0]))

train = []
for line in all_data[5:]:
    temp = []
    for item in line:
        temp.append(float(item))
    train.append(temp)

# 数据 train 和 feature

train = np.array(train)

X_train = train[:, :-1]
y_train = train[:, -1:]

print(X_train)
print(y_train)

# 研究数据
