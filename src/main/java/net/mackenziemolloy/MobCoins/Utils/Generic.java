package net.mackenziemolloy.mobcoins.utils;

import java.text.DecimalFormat;

public class Generic {

	public static String roundedNumber(Double numberToRound) {

		if (numberToRound < 1000)
			return "" + numberToRound;
		int exp = (int) (Math.log(numberToRound) / Math.log(1000));
		DecimalFormat format = new DecimalFormat("0.#");
		String value = format.format(numberToRound / Math.pow(1000, exp));

		return String.format("%s%c", value, "kMBTPE".charAt(exp - 1));

	}

}
