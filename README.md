# 本程序使用依赖包包括了：

## Apache POI		

~~醒醒，跟某舰娘游戏没有关系poi~~，此API主要负责支持Java语言对office文件进行编辑修改。

依赖包下载地址：[https://mirrors.tuna.tsinghua.edu.cn/apache/poi/release/bin/poi-bin-3.17-20170915.zip](https://mirrors.tuna.tsinghua.edu.cn/apache/poi/release/bin/poi-bin-3.17-20170915.zip)。



## OpenCV		

主要负责对图像的阈值处理，减少水印所带来的误差。

所用版本为OpenCV3.4.0，下载：[https://astuteinternet.dl.sourceforge.net/project/opencvlibrary/opencv-win/3.4.0/opencv-3.4.0-vc14_vc15.exe](https://astuteinternet.dl.sourceforge.net/project/opencvlibrary/opencv-win/3.4.0/opencv-3.4.0-vc14_vc15.exe)。



## Tess4J		

Tess4J是Tesseract-OCR API在Java PC上的一个应用，具体使用时不用另外安装tesseract。

使用版本为3.4.6，下载地址：[https://jaist.dl.sourceforge.net/project/tess4j/tess4j/3.4.6/Tess4J-3.4.6-src.zip](https://jaist.dl.sourceforge.net/project/tess4j/tess4j/3.4.6/Tess4J-3.4.6-src.zip)。



另外因为是基于汉字的训练，因此需要另外下载简体中文的字库(默认的字库是英文)。

GitHub开源地址：[https://github.com/tesseract-ocr/tessdata](https://github.com/tesseract-ocr/tessdata)，选择`chi_sim.traineddata`下载即可。


> PS：
>
> 以上依赖包OpenCV与Tess4J需要配置环境变量，具体请自行查阅相关文档。

