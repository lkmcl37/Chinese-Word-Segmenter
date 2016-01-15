# Chinese-Word-Segmenter

A word segmenter written in pure Java. It is designed based on the assumption of Bigram model, requiring no lexicon in advance. To avoid redundant calculations of probabilities between words, it utilizes a new word scoring method based on word length, frequency and occurrence frequency statistics. In addition, to enhance the reliability of word scoring, a feedback mechanism is introduced. 

It is developed for fun.
Modules for part-of-speech tagging and unknown word recognition are still under development.

# Training Data Files
Corpus file:

standard-text.txt: People's Daily of the year 1998, which contains roughly 15,000,000 words that have been manually segmented.

Serialized files (containing data trained on the corpus and ready to be used):

1. wordFreq.temp: word frequency statistics, stored as HashMap \<word, frequency>
2. markovChain.temp: occurrence frequency statistics, stored as HashMap \<word, HashMap \<successor, occurrence frequency>>

# Word Scoring and Feedback Mechanism
This segmenter identifies word boundaries by estimating and comparing the score values of all possible word alternatives (possible segmentation solutions).

During segmentation process, the score of a word alternative Wi is calculated as follows:

             score = C(Wi, Wi-1)*[(base)^length]

Where C(Wi, Wi-1) is the co-occurrence frequency of Wi and its precursor Wi-1, length is the word length of Wi, base is a experiment value measured by trial and error analysis.

Moreover, considering the possibility that Wi and Wi-1 may not appear adjacently in training data, the score of Wi can be calculated as follows in case C (Wi, Wi-1) does not exist:

            score = Cwi/(base)^length

Where Cwi and length are the frequency and word length of Wi respectively.

After word scoring, the score value of Wi will be adjusted by adding a feedback value.
The measurement of feedback value is as same as word scoring, but with Wi-1 replaced by Wi and Wi replaced by Wi+1.

Finally, the word alternative with the highest score will be accepted and marked as the segmentation result.

#Performance
The text used for experiment is from the People's Daily 1998, which contains 253 3709 lines, with a total of 2837 4490 Chinese characters. The experiment environment is shown as follows:

     Experiment platform：   Eclipse 
     Operation platform：    Windows 7 32 operation system 
     CPU：                   Intel(R) Core(TM)2 Duo CPU T5670@1.80GHZ 
     RAM：                   2.00 GB 

 The experimental results are illustrated as follows:	
 
     Accurate word count:      15060241  
     Total word count:         15943761
     Accuracy rate:            0.9445852205135288
     Time:                     38686
     Efficiency:               746 characters / millisecond
     Total running time :      48002

For further experiments on the ACL Sighan data sets, please see the attached files.
