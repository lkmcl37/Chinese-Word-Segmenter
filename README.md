# Chinese-Word-Segmenter

A word segmenter written in Java, requiring no dictionary in advance. It identifies word boundaries by estimating and comparing the score of each candidate segments (possible segmentation solutions).
The scoring method is based on the measurement of word length, frequency and occurrence frequencies.


# Usage
To segment raw texts, please use *Run.java*


#Word Scoring

During segmenting process, the score of a candidate word Wi is calculated as follows:

         score = C(Wi, Wi-1)*[(base)^length]
         
Where C(Wi, Wi-1) is the co-occurrence frequency of Wi and its precursor Wi-1, length is the word length of Wi, base is a constant.

Moreover, if C(Wi, Wi-1) does not exist in training data, that the score of Wi will be estimated as:

        score = freq/(base)^length
        
Where freq and length are the frequency and word length of Wi respectively.

The score of Wi will be further adjusted by a feedback value after word scoring. The measurement of feedback value is as same as word scoring, but with Wi-1 replaced by Wi and Wi replaced by Wi+1. 

Finally, the word candidate with the highest score will be accepted as the segmentation result.

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
