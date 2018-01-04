# wechatJump
微信跳一跳

配合bluestacks安卓模拟器使用：http://www.bluestacks.cn

思考:
    1:截取游戏图片,使用像素点构建数组。
    2:分析数组找到旗子,定位棋子中心点。
    3:扫描数组找到第一个不是背景色,并且Y坐标不等于旗子的点,定义为块顶点(四边形为点,圆形为线的中心点)
    4:根据块顶点向下扫描,定位中心点。
    5:计算棋子中心点到块中心点的坐标系距离,距离*系数等于蓄力时间。