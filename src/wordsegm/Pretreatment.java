package wordsegm;

import java.util.ArrayList;

public class Pretreatment {

	public int preprocess(String str, int maxWordSize,
			ArrayList<String> pretreatLsit) {

		int localMaxWordSize = maxWordSize;
		char[] strChars = str.toCharArray();
		int maxLocalMaxWordSize = 0;
		StringBuilder temp = new StringBuilder("");

		for (int index = 0; index < strChars.length; index++) {
			temp.delete(0, temp.length());
			boolean flag = false;
			int res = 0;
			int tempMaxLocalMaxWordSize = 0;
			for (int count = 0; count < localMaxWordSize; count++) {
				if (index + count < strChars.length) {
					if (strChars[index + count] == ' ') {
						if (count == 0) {
							break;
						} else {
							localMaxWordSize++;
							continue;
						}
					}
					if (strChars[index + count] > 47
							&& strChars[index + count] < 58) {
						if (count == 0) {
							flag = true;
							while (strChars[index + count] > 47
									&& strChars[index + count] < 58) {
								temp.append(strChars[index + count]);
								localMaxWordSize++;
								count++;
								res++;
								if (index + count >= strChars.length) {
									break;
								}
							}
						} else {
							while (strChars[index + count] > 47
									&& strChars[index + count] < 58) {
								temp.append(strChars[index + count]);
								localMaxWordSize++;
								count++;
								if (index + count >= strChars.length) {
									break;
								}
							}
						}
						count--;
						localMaxWordSize--;
						pretreatLsit.add(temp.toString());
						tempMaxLocalMaxWordSize++;
						continue;

					} else if ((strChars[index + count] > 64 && strChars[index
							+ count] < 91)
							|| (strChars[index + count] > 96 && strChars[index
									+ count] < 123)) {
						if (count == 0) {
							flag = true;
							while ((strChars[index + count] > 64 && strChars[index
									+ count] < 91)
									|| (strChars[index + count] > 96 && strChars[index
											+ count] < 123)) {
								temp.append(strChars[index + count]);
								localMaxWordSize++;
								count++;
								res++;
								if (index + count >= strChars.length) {
									break;
								}
							}
						} else {
							while ((strChars[index + count] > 64 && strChars[index
									+ count] < 91)
									|| (strChars[index + count] > 96 && strChars[index
											+ count] < 123)) {
								temp.append(strChars[index + count]);
								localMaxWordSize++;
								count++;
								if (index + count >= strChars.length) {
									break;
								}
							}
						}
						count--;
						localMaxWordSize--;
						pretreatLsit.add(temp.toString());
						tempMaxLocalMaxWordSize++;
						continue;
					}
					//
					temp.append(strChars[index + count]);
					pretreatLsit.add(temp.toString());
					tempMaxLocalMaxWordSize++;
				}
			}
			if (tempMaxLocalMaxWordSize > maxLocalMaxWordSize) {
				maxLocalMaxWordSize = tempMaxLocalMaxWordSize;
			}
			if (flag) {
				index = index + res - 1;
				flag = false;
			}
			localMaxWordSize = maxWordSize;//
		}
		return maxLocalMaxWordSize;
	}
}
