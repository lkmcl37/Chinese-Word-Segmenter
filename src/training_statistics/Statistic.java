package training_statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Statistic {

	private String encode = "utf-8";
	private HashMap<String, Integer> wordFreq;
	private HashMap<String, HashMap<String, Integer>> markovChain;

	public Statistic() {
		super();
		wordFreq = new HashMap<String, Integer>();
		markovChain = new HashMap<String, HashMap<String, Integer>>();
	}

	public void statWordFreqAndMarkovChain(File file) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), encode));
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				String[] lineArray = line.split(" ");
				String predecessor = null;

				for (String word : lineArray) {
					if (wordFreq.containsKey(word)) {
						wordFreq.put(word, wordFreq.get(word) + 1);
					} else {
						wordFreq.put(word, 1);
					}
					if (!markovChain.containsKey(word)) {
						markovChain.put(word, new HashMap<String, Integer>());
					}
					if (predecessor != null) {
						HashMap<String, Integer> succeeds = markovChain
								.get(predecessor);
						if (succeeds.containsKey(word)) {
							succeeds.put(word, succeeds.get(word) + 1);
						} else {
							succeeds.put(word, 1);
						}
					}
					predecessor = word;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void WordFreqAndMarkovChainSerialize(File file) throws IOException {

		statWordFreqAndMarkovChain(file);
		adjustFreq();
		try {
			Kryo kryo1 = new Kryo();
			Output output1 = new Output(new FileOutputStream("AdjustFreq.temp"));
			kryo1.writeObject(output1, wordFreq);
			output1.close();

			Kryo kryo2 = new Kryo();
			Output output2 = new Output(
					new FileOutputStream("markovChain.temp"));
			kryo2.writeObject(output2, markovChain);
			output2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (KryoException e) {

		}
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> getWordFreq(File file) {

		HashMap<String, Integer> wordFreq = null;
		Kryo kryo = new Kryo();
		Input input;
		try {
			input = new Input(new FileInputStream(file));
			wordFreq = kryo.readObject(input, HashMap.class);
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (KryoException e) {

		}
		return wordFreq;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, HashMap<String, Integer>> getMarkovChain(File file) {

		HashMap<String, HashMap<String, Integer>> markovChain = null;
		try {
			Kryo kryo = new Kryo();
			Input input = new Input(new FileInputStream(file));
			markovChain = kryo.readObject(input, HashMap.class);
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (KryoException e) {

		}
		return markovChain;
	}
}
