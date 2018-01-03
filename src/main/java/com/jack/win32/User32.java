package com.jack.win32;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public interface User32 extends StdCallLibrary {

	User32 INSTANCE = (User32) Native.loadLibrary("User32",User32.class);
	
	int PostMessageA(int a, int b, int c, int d);
	
	int FindWindowA(String a, String b);
	
	int GetWindowRect(int hwnd, Rect r);
	
}
