## 使用说明

下载最新发布的GenCode压缩包，解压缩后，点击GenCode.exe即可运行，无需依赖Java环境。



[![pSx4f0I.md.png](https://s1.ax1x.com/2023/02/23/pSx4f0I.md.png)](https://imgse.com/i/pSx4f0I)
[![pSx479S.md.png](https://s1.ax1x.com/2023/02/23/pSx479S.md.png)](https://imgse.com/i/pSx479S)
[![pSx4bcQ.md.png](https://s1.ax1x.com/2023/02/23/pSx4bcQ.md.png)](https://imgse.com/i/pSx4bcQ)

### 模板文件说明

- 对象类型和集合类型《类型》分别填Object和List；
- 子类名默认类型去除s和List，可以通过《子类名》指定；
- 《内部类》指定是否创建与该类中；
- 《排序》目前仅用于作用上下级关系，比如<1.1>是<1>的属性；
- 《lombok》是否支持lombok；
- 排序时要从1开始；
- 排序主要要从小到大，可以跳数，但排序不能乱；
- 如果是内部类，该类的属性都需要标注；
- 支持多类生产，写在多个sheet中即可；
## 界面说明

- 文件路径是数据文件路径，生成的代码在该路径下；

### 待做

- 字段支持链式方式；
- 增加日志打印

## 版本说明

```txt
v1.0.0 初始版本
v1.0.1 修复排序和子包名问题
```

