## 内存马学习记录

先知的[这篇文章](https://xz.aliyun.com/t/11368)，准确来说这个马是冰蝎改过来的。因为如果是哥斯拉的木马，
反编译之后的`SimpleVer`会包含一些列操作，比如命令执行、数据库连接、文件查看编辑等。 但是在`SimpleVer`里面，
使用了`com.sun.jmx.remote.util.OrderClassLoaders`的`defineClass`加载传过来的字节码。这在[冰蝎](https://xz.aliyun.com/t/2744)
的原理最开始就讲了。这个马结合了哥斯拉服务端的实现，完成了payload传递和混淆的功能。按理来说，应该是可以通过Burp插件完成的，未测试。

