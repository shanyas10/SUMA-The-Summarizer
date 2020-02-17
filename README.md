##SUMA- The Summarizer

This is an extractive sumarization tool that provides a short summary of the provided document by extracting the most 
important sentences of the document. Whether or not shouuld a sentence be added to the summary is decided by a Naive Bayes classifier which runs on the document considering certain statistical and inguistic features like position, lengt, presence of keyword etc.

###Data
Training data consists of documents and their manually curated summary. Both of them can be found [here](https://github.com/thechange/SUMA-The-Summarizer/tree/master/data)

###Requirements

Provided are the java files along with the training data set for the Extractive Summarization tool. 
OpenNLP library has been used for some purposes. The jar file for the same needs to be added to your build path.
The jar files required are located at "apache-opennlp-1.5.3-bin.zip" which can be downloaded here.(http://opennlp.apache.org/cgi-bin/download.cgi)
In addition, you will need to download some model files namely en-ner-person.bin,en-sent.bin, en-token.bin. 
These can be downloaded here(http://opennlp.sourceforge.net/models-1.5/).
