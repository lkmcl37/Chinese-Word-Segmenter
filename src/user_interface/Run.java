package user_interface;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import training_statistics.Statistic;
import wordsegm.WordSegm;

public class Run {

	String str;

	public Run(String str) {
		this.str = str;
	}

	public String getresult() throws IOException, ClassNotFoundException {
		Statistic info = new Statistic();
		HashMap<String, Integer> wordFreq = info.getWordFreq(new File(
				".//data//wordFreq.temp"));
		HashMap<String, HashMap<String, Integer>> markovChain = info
				.getMarkovChain(new File(".//data//markovChain.temp"));
		WordSegm seg = new WordSegm(wordFreq, markovChain);
		String words = seg.ArraytoString(seg.segment(str));
		return words;
	}
}

