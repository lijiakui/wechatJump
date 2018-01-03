package com.jack.jump;

import com.jack.win32.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Jack on 2018/1/1.
 */
public class Jump {
    private static final File file = new File("D:\\temp\\");
    /**
     * 定位旗子的颜色值
     */
    private static final int DEFULT_HALMA_COLOR = 3554124;
    private static final int DEFULT_WHITE = 16119285;
    /**
     * 起跳系数
     */
    private static final double jump =2.9;
    /**
     * 横向边界偏移量
     */
    private static final int BODER_X = 7;
    /**
     * 纵向边界偏移量
     */
    private static final int BODER_Y = 358;

    public static int goJump(){
        int hwnd = GameWindow.getHwnd("BlueStacks App Player");
        if(hwnd <= 0){
            return 0;
        }

        try {
            File f = File.createTempFile("tiao",".bmp",file);
            BufferedImage bufferedImage = GameWindow.getImage(hwnd);
            if(bufferedImage == null){
                return 0;
            }
            ImageIO.write(bufferedImage,"bmp",new FileOutputStream(f));
            int[][] GRB = getImageGRB(bufferedImage);
            Robot robot = new Robot();
            Point p1 = getHalma(GRB);
            if(p1 == null){
                return 0;
            }
            System.out.println("棋子中心点  X:"+p1.getX()+"   Y:"+p1.getY());
            Point p2 = getBlockCenter(GRB,p1);
            if(p2 == null){
                return 0;
            }
            System.out.println("方块中心点  X:"+p2.getX()+"   Y:"+p2.getY());
            robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
            double range = p2.getDistance(p1);
            System.out.println("预估跳跃距离："+range);
            int ms = (int)(range*jump);
            System.out.println("蓄力时间："+ms+"毫秒"+"  起跳系数："+jump);
            if(ms != 0){
                robot.delay(ms);
                robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
            }
            System.out.println("----------------------------------------------------------------");
            return ms;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取方块中心点
     * @param GRB 棋盘
     * @param halma 旗子
     * @return
     * @throws AWTException
     */
    public static Point getBlockCenter(int[][] GRB, Point halma) throws AWTException {
        //获取顶点
        Point vertex = vertexCenter(GRB,halma);
        if(vertex == null){
            return null;
        }
        //向下偏移 5个像素点 取面颜色 因为顶点颜色不固定
        int faceColor = GRB[vertex.getY()+5][vertex.getX()];
        for(int y = vertex.getY()+6 ; y<GRB.length; y++){
            //颜色偏移100 防止色差
            int temp = GRB[y][vertex.getX()];
            if(Math.abs(temp-faceColor)<100){
                //此处为色块颜色，保留if判断用于修正BUG
            }else{
                //查询白色块
                Point whitey = findBaise(y,vertex.getX(),GRB);
                if(whitey != null){
                    return whitey;
                }else{
                    vertex.setY((vertex.getY()+(y-vertex.getY())/2));
                    //移动到中心点
                    return vertex;
                }
            }
        }
        return null;
    }

    /**
     * 找到棋子中心点
     * @param GRB
     * @return
     */
    public static Point getHalma(int[][] GRB){
        try {
            for(int i=BODER_Y;i<GRB.length;i++){
                for(int j=BODER_X;j<GRB[i].length-BODER_X;j++){
                    if(Math.abs(GRB[i][j] - DEFULT_HALMA_COLOR)<100){
                        return new Point(j+10,i+90);
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取顶点的中心点
     * 因为弧形中心点为线
     * @param GRB 棋盘
     * @param halma 棋子中心
     * @return 顶点
     */
    public static Point vertexCenter(int[][] GRB,Point halma){
        try {
            if(halma == null){
                return null;
            }
            //背景色
            int background = 0;
            for(int i=BODER_Y;i<GRB.length;i++){
                for(int j=BODER_X;j<GRB[i].length-BODER_X;j++){
                    if(j == BODER_X){
                        background = GRB[i][j];
                        continue;
                    }
                    //如果当前颜色与背景色差值100以内，则当前像素为背景色
                    if(Math.abs(GRB[i][j]-background)<100){
                        //此处保留，排查BUG使用
                    }else{//当前像素为其他色块
                        //当前查询到的X轴和人物的X轴在一垂直方向，跳过此次循环继续查询
                        if(Math.abs(j - halma.getX())<30){
                            continue;
                        }
                        int length = 0;
                        //查找与背景色不同的颜色
                        while (Math.abs(GRB[i][j+length]-background)>100){
                            length ++;
                        }
                        Point vertex = new Point(j+(length/2+length%2),i);
                        Robot robot = new Robot();
                        robot.mouseMove(vertex.getX(),vertex.getY());
                        return vertex;
                    }
                }
            }
            return null;
        } catch (AWTException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 白色点一共为9个像素点 Y+3 则是 中心点
     * @param y
     * @param x
     * @param GRB
     * @return
     */
    public static Point findBaise(int y,int x,int[][] GRB){
        for(int i=y;i<y+50;i++){
            if(GRB[i][x] == 16119285){
                return new Point(x,i+3);
            }
        }
        return null;
    }

    public static int[][] getImageGRB(BufferedImage bufferedImage){
        int width = bufferedImage.getWidth();
        int hight = bufferedImage.getHeight();
        int[][] result = new int[hight][width];
        for(int h = 0;h < hight; h++){
            for(int w = 0; w<width;w++){
                result[h][w] = bufferedImage.getRGB(w,h) & 0xFFFFFF;
            }
        }
        return result;
    }
    public static void main(String[] args) throws InterruptedException {
        boolean notEnd = true;
        while (notEnd){
            int ms = Jump.goJump();
            if(ms != 0){
                if(ms < 500){
                    ms = 500;
                }
                Thread.sleep(ms+1000);
            }else{
                notEnd = false;
            }
        }
    }
}
