package com.qtkt.ServiceClasses.HelperClasses;

import java.util.Random;

public class AccessoryService {

	public static int generateRandomNumber() {
		Random rand = new Random();
		int value = 10000000 + rand.nextInt(90000000);
		value = Integer.parseInt((value + "").substring(0, 8));
		return value;
	}
}
