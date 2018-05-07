import tensorflow as tf
hello = tf.constant('Hello, TensorFlow!')
sess = tf.Session()
print(sess.run(hello))


##-------------
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt

#表示直接在浏览器中显示matplotlib图表
%matplotlib inline

a = tf.random_normal([2,20]) #定义2x20的随机数矩阵
sess = tf.Session()  #启动一个tensorflow会话
out = sess.run(a)    # 用在sess会话里执行a，结果放out里
x, y = out

plt.scatter(x, y)    #用pyplot创建一系列散列点，坐标为x和y
plt.show()