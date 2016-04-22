package com.zgp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import java.lang.reflect.Field;

public class Util_System {


	/**
	 * 获取一个自定义风格的Dialog
	 * 
	 * @param activity
	 *            上下文对象
	 * @param style
	 *            风格
	 * @param customView
	 *            自定义view
	 * @return dialog
	 */
	public static Dialog getCustomeDialog(Activity activity, int style, View customView) {
		Dialog dialog = new Dialog(activity, style);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(customView);
		Window window = dialog.getWindow();
		LayoutParams lp = window.getAttributes();
		lp.width = LayoutParams.MATCH_PARENT;
		lp.height = LayoutParams.MATCH_PARENT;
		lp.x = 0;
		lp.y = 0;
		window.setAttributes(lp);
		return dialog;
	}

	public static Dialog getCustomeDialog(Activity activity, int style, int customView) {
		Dialog dialog = new Dialog(activity, style);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(customView);
		Window window = dialog.getWindow();
		LayoutParams lp = window.getAttributes();
		lp.width = LayoutParams.MATCH_PARENT;
		lp.height = LayoutParams.MATCH_PARENT;
		lp.x = 0;
		lp.y = 0;
		window.setAttributes(lp);
		return dialog;
	}

	/**
	 * 获取手机屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getDisplayHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 获取手机屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getDisplayWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 反射方法获取状态栏高度
	 * 
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int statusBarHeight = 20;
		try {
			Class<?> _class = Class.forName("com.android.internal.R$dimen");
			Object object = _class.newInstance();
			Field field = _class.getField("status_bar_height");
			int restult = Integer.parseInt(field.get(object).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(restult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Toast.makeText(getActivity(), "StatusBarHeight = " + statusBarHeight,
		// Toast.LENGTH_SHORT).show();
		return statusBarHeight;
	}

	public static float getDensity(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		return metrics.density;
	}
}
