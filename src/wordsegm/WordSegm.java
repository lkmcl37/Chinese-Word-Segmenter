package wordsegm;

import java.util.ArrayList;
import java.util.HashMap;

public class WordSegm {

	private HashMap<String, Integer> wordFreq = null;
	private HashMap<String, HashMap<String, Integer>> markovChain = null;
	private int maxWordSize = 6;
	int base = 33000;
	double weight = 0.9;
	int lastPlus = 55000;
	int engPlus = 100000;
	boolean debug = true;

	public WordSegm(HashMap<String, Integer> wordFreq,
			HashMap<String, HashMap<String, Integer>> markovChain) {
		super();
		this.wordFreq = wordFreq;
		this.markovChain = markovChain;
	}

	public ArrayList<String> segment(String str) {

		ArrayList<String> result = new ArrayList<String>();
		Pretreatment pretreatment = new Pretreatment();

		ArrayList<String> pretreatLsit = new ArrayList<String>();

		int localMaxWordSize = pretreatment.preprocess(str, maxWordSize,
				pretreatLsit);
		int resLocalMaxWordSize = localMaxWordSize;

		String predecessor = null;

		for (int index = 0; index < pretreatLsit.size();) {

			int ind = index;
			if (predecessor == null
					|| (predecessor != null && !wordFreq
							.containsKey(predecessor))) {
				ind = feedbackWordFreqSegment(index, localMaxWordSize,
						resLocalMaxWordSize, pretreatLsit);
			} else {
				HashMap<String, Integer> succeeds = markovChain
						.get(predecessor);
				boolean flag = false;
				for (int count = 0; count < localMaxWordSize; count++) {
					if (succeeds.containsKey(pretreatLsit.get(index + count))) {
						flag = true;
					}
				}
				if (flag) {
					ind = feedbackMarkovSegment(index, localMaxWordSize,
							resLocalMaxWordSize, succeeds, pretreatLsit);
				} else {
					ind = feedbackWordFreqSegment(index, localMaxWordSize,
							resLocalMaxWordSize, pretreatLsit);
				}
			}
			result.add(pretreatLsit.get(ind));
			predecessor = pretreatLsit.get(ind);
			int count = ind - index + 1;
			if (localMaxWordSize == resLocalMaxWordSize) {
				int i = 0;
				for (; i < count; i++) {
					index += resLocalMaxWordSize;
					if (index == pretreatLsit.size()
							- (resLocalMaxWordSize * (resLocalMaxWordSize - 1))
							/ 2) {
						localMaxWordSize--;
						i++;
						break;
					}
				}
				for (; i < count; i++) {
					index += localMaxWordSize;
					localMaxWordSize--;
				}
			} else {
				for (int i = 0; i < count; i++) {
					index += localMaxWordSize;
					localMaxWordSize--;
				}
			}
		}
		return result;
	}

	public int feedbackMarkovSegment(int index, int localMaxWordSize,
			int resLocalMaxWordSize, HashMap<String, Integer> succeeds,
			ArrayList<String> pretreatLsit) {

		double score = 0, maxScore = 1;
		int border = index;

		for (int count = 0; count < localMaxWordSize; count++) {
			if (succeeds.containsKey(pretreatLsit.get(index + count))) {
				score = succeeds.get(pretreatLsit.get(index + count))
						* Math.pow(base, count);
			} else {
				if (wordFreq.containsKey(pretreatLsit.get(index + count))) {
					score = wordFreq.get(pretreatLsit.get(index + count))
							/ Math.pow(weight, count);
				} else
					score = 1;
			}
			if (count == 0 && pretreatLsit.get(index).matches("[a-zA-Z]+")) {
				score = score + engPlus;
			}
			int tempIndex = index;
			int tempLocalMaxWordSize = localMaxWordSize;
			if (tempLocalMaxWordSize == resLocalMaxWordSize) {
				int i = 0;
				for (; i < count + 1; i++) {
					tempIndex += resLocalMaxWordSize;
					if (tempIndex == pretreatLsit.size()
							- (resLocalMaxWordSize * (resLocalMaxWordSize - 1))
							/ 2) {
						tempLocalMaxWordSize--;
						i++;
						break;
					}
				}
				for (; i < count + 1; i++) {
					tempIndex += tempLocalMaxWordSize;
					tempLocalMaxWordSize--;
				}
			} else {
				for (int i = 0; i < count + 1; i++) {
					tempIndex += tempLocalMaxWordSize;
					tempLocalMaxWordSize--;
				}
			}

			boolean exist = wordFreq.containsKey(pretreatLsit
					.get(index + count));
			if (exist && tempLocalMaxWordSize == 0) {
				score = score + lastPlus;
			}
			double localMax = 1;
			double temp = 0;
			if (exist) {
				HashMap<String, Integer> tempSucceeds = markovChain
						.get(pretreatLsit.get(index + count));
				for (int i = 0; i < tempLocalMaxWordSize; i++) {
					String word = pretreatLsit.get(tempIndex + i);
					if (tempSucceeds.containsKey(word)) {
						temp = tempSucceeds.get(word) * Math.pow(base, count);
					}
					if (temp > localMax) {
						localMax = temp;
					}
				}
			} else {
				for (int i = 0; i < tempLocalMaxWordSize; i++) {
					String word = pretreatLsit.get(tempIndex + i);
					if (wordFreq.containsKey(word)) {
						temp = wordFreq.get(word) / Math.pow(base, count);
						if (temp > localMax) {
							localMax = temp;
						}
					}
				}
			}
			score = score + localMax;
			if (score > maxScore) {
				maxScore = score;
				border = index + count;
			}
		}
		return border;
	}

	public int feedbackWordFreqSegment(int index, int localMaxWordSize,
			int resLocalMaxWordSize, ArrayList<String> pretreatLsit) {

		double score = 0, maxScore = 1;
		int border = index;

		for (int count = 0; count < localMaxWordSize; count++) {
			boolean exist = true;
			if (wordFreq.containsKey(pretreatLsit.get(index + count))) {
				if (count < 2) {
					score = wordFreq.get(pretreatLsit.get(index + count))
							* Math.pow(base, count + weight);
				} else
					score = wordFreq.get(pretreatLsit.get(index + count))
							* Math.pow(base, count);
			} else {
				score = 1;
				exist = false;
			}

			if (count == 0 && pretreatLsit.get(index).matches("[a-zA-Z]+")) {
				score = score + engPlus;
			}
			int tempIndex = index;
			int tempLocalMaxWordSize = localMaxWordSize;
			if (tempLocalMaxWordSize == resLocalMaxWordSize) {
				int i = 0;
				for (; i < count + 1; i++) {
					tempIndex += resLocalMaxWordSize;
					if (tempIndex == pretreatLsit.size()
							- (resLocalMaxWordSize * (resLocalMaxWordSize - 1))
							/ 2) {
						tempLocalMaxWordSize--;
						i++;
						break;
					}
				}
				for (; i < count + 1; i++) {
					tempIndex += tempLocalMaxWordSize;
					tempLocalMaxWordSize--;
				}
			} else {
				for (int i = 0; i < count + 1; i++) {
					tempIndex += tempLocalMaxWordSize;
					tempLocalMaxWordSize--;
				}
			}
			if (exist && tempLocalMaxWordSize == 0) {
				score = score + lastPlus;
			}
			double localMax = 1;
			double temp = 0;
			if (exist) {
				HashMap<String, Integer> tempSucceeds = markovChain
						.get(pretreatLsit.get(index + count));
				for (int i = 0; i < tempLocalMaxWordSize; i++) {
					String word = pretreatLsit.get(tempIndex + i);
					if (tempSucceeds.containsKey(word)) {
						temp = tempSucceeds.get(word) * Math.pow(base, count);
					}
					if (temp > localMax) {
						localMax = temp;
					}
				}
			} else {
				for (int i = 0; i < tempLocalMaxWordSize; i++) {
					String word = pretreatLsit.get(tempIndex + i);
					if (wordFreq.containsKey(word)) {
						temp = wordFreq.get(word) / Math.pow(base, count);
						if (temp > localMax) {
							localMax = temp;
						}
					}
				}
			}
			score = score + localMax;
			if (score > maxScore) {
				maxScore = score;
				border = index + count;
			}
		}
		return border;
	}

	public String ArraytoString(ArrayList<String> words) {

		String result = "";
		for (String s : words) {
			result += s + " | ";
		}
		return result;
	}
}