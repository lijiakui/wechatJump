# wechatJump
微信跳一跳 全自动辅助-JAVA
--
使用管理员权限运行编译器<br>
配合bluestacks安卓模拟器使用：http://www.bluestacks.cn<br>
运行时，请勿遮挡游戏窗口。<br>
调整蓄力系数jump的值，达到最优。<br>
### 思考:
* 截取游戏图片,使用像素点构建数组。
* 分析数组找到旗子,定位棋子中心点。
* 扫描数组找到第一个不是背景色,并且Y坐标不等于旗子的点,定义为块顶点(四边形为点,圆形为线的中心点)
* 根据块顶点向下扫描,定位中心点。
* 计算棋子中心点到块中心点的坐标系距离,距离x系数等于蓄力时间。
