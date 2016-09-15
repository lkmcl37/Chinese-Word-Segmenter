# Chinese-Word-Segmenter

A word segmenter written in Java, requiring no dictionary in advance. It identifies word boundaries by estimating and comparing the score of each candidate segments (possible segmentation solutions).
The scoring method is based on the measurement of word length, frequency and occurrence frequencies (trigram).


# Usage
To segment raw texts, please use *Run.java*

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
