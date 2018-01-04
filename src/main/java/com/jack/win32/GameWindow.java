package com.jack.win32;


import java.awt.*;
import java.awt.image.BufferedImage;


public class GameWindow {

	private static User32 user32 = User32.INSTANCE;

	/**
	 * 获取窗口句柄
	 * @param title
	 * @return
	 */
	public static int getHwnd(String title) {
		return user32.FindWindowA(null, title);
	}

	/**
	 * 获取窗口矩形
	 * @param hwnd
	 * @return
	 */
	public static Rect getRect(int hwnd) {
		Rect r = new Rect();
		user32.GetWindowRect(hwnd, r);
		return r;
	}

	/**
	 * 窗口截图
	 * @param r
	 * @return
	 */
	public static BufferedImage getImage(Rect r){
		Rectangle rg = new Rectangle(r.left, r.top, r.right-r.left, r.bottom-r.top);
		try {
			return new Robot().createScreenCapture(rg);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}
