package com.pinthecloud.moodly.util;

import java.util.Random;

public class RandomUtil {
	public static String getString() {
		return getString(getInt(25));
	}
	public static String getString(int num) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < num; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;
	}
	public static int getInt() {
		return getInt(100);
	}
	public static int getInt(int seed) {
		return new Random().nextInt(seed);
	}
	public static float getFloat() {
		return new Random().nextFloat();
	}
	public static boolean getBoolean() {
		if (getInt() > 50) return true;
		return false;
	}
	public static double getDouble() {
		return new Random().nextDouble();
	}
	public static String getDate() {
		return "2014."+getInt(13)+"."+getInt(31);
	}
}
