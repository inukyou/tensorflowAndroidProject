# tensorflowAndroidProject
本程序是一个基于tensorflow的手绘图形识别软件
，目前本程序只能识别：鱼、花、圆圈、猫四种图形

六、技术实现方案
1.使用tensorflow构建lenet5网络并进行训练，以官方的mnist项目作为参考，且最后将训练的模型和参数保存在pb文件中。

2.使用了PaletteView开源项目实现绘图板的功能。

3.android端使用tensorflow读取pb文件，并对输入的图像进行识别。

4.Android使用opencv将获得的图像灰度化并缩小成为28*28的大小。

5.训练所用的图像数据在谷歌的Quick draw项目上下载。


训练模型和代码https://github.com/inukyou/tensorflow_DrawTrain

![](https://github.com/inukyou/tensorflowAndroidProject/blob/master/Screenrecorder.gif)  

八、外部参考
1.https://blog.csdn.net/happyorg/article/details/78274066  （lenet5详解）
2.http://www.tensorfly.cn/tfdoc/tutorials/overview.html （mnist详解）
3.https://blog.csdn.net/michael_yt/article/details/74737489 （tensorflow模型持久化pb文件保存）
4.https://quickdraw.withgoogle.com/data （谷歌quick draw项目）
5.https://blog.csdn.net/michael_yt/article/details/72955793 （mnist在android端上的移植）
