
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sanya
 */

public class Preprocessor 
{
    List<String> stopwords;
    private static ArrayList<String> sentences = new ArrayList<String>();
    private static ArrayList<String> tokens = new ArrayList<String>();
    Map<String, Integer> wordCount = new HashMap<>();
    
    
    public Preprocessor() 
    {
	stopwords = readStopwords();
    }
    
    public String[] seperator(String passage)throws InvalidFormatException,
		IOException 
    {
        InputStream is = new FileInputStream("E:/Summary/data/models/en-sent.bin");
	SentenceModel model = new SentenceModel(is);
	SentenceDetectorME sdetector = new SentenceDetectorME(model);
        String sent[]=sdetector.sentDetect(passage);
       
        is.close();
        return sent;
        
    }
    public String[] tokenize(String passage) throws InvalidFormatException, IOException 
    {
	InputStream is = new FileInputStream("E:/Summary/data/models/en-token.bin");
	TokenizerModel model = new TokenizerModel(is);
 	Tokenizer tokenizer = new TokenizerME(model);
 	String token[] = tokenizer.tokenize(passage);
        is.close();
        return token;
    }
    
    public int findName(String sent)  
    {
	InputStream is;
        int name = 0;
        try 
        {
            is = new FileInputStream("E:/Summary/data/models/en-ner-person.bin");
            TokenNameFinderModel model = new TokenNameFinderModel(is);
            is.close();
 
            NameFinderME nameFinder = new NameFinderME(model); 
            String []sentence = tokenize(sent); 
            Span nameSpans[] = nameFinder.find(sentence); 
            name=nameSpans.length;
           
                //System.out.println(sent);
        } 
        catch (Exception ex) 
        {
            System.out.println(ex);
        }
        return name;
    }
    private List<String> readStopwords() 
    {
        String stopword = null;
	List<String> stopwords = new ArrayList<>();

	String fileName;
        fileName = "E:/Summary/data/models/stoplist.txt";
		
	try {
		FileReader fr=  new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fr);
		
		while ((stopword = bufferedReader.readLine()) != null) {
                       // System.out.println(stopword);
			stopwords.add(stopword);
		}
	} catch (IOException e) {
	}
		 //To change body of generated methods, choose Tools | Templates.
        return stopwords;
    }
    public ArrayList<List<String>> removeStopwords(ArrayList<List<String>> passage) 
    {	
	ArrayList<List<String>> processed = new ArrayList<>();
		
	for (int i=0; i<passage.size(); i++) 
        {
            List<String> oldSentence = passage.get(i);
            List<String> newSentence = new ArrayList<>();
			
            for (int j=0; j<oldSentence.size(); j++) 
            {
		if (!stopwords.contains(oldSentence.get(j))) 
                {
                    newSentence.add(oldSentence.get(j));
		}
            }		
            processed.add(newSentence);
	}
        
	return processed;
    }
    private boolean isPunct(final char c) 
    {
        final int val = (int)c;
        return val >= 33 && val <= 47
        || val >= 58 && val <= 64
        || val >= 91 && val <= 96
        || val >= 123 && val <= 126;
    }
    public ArrayList<List<String>> removePunctuation(ArrayList<List<String>> document) 
    {
			
	// Each sentence is a list of Strings and
	// the document is a list of sentences.
	ArrayList<List<String>> processed = new ArrayList<>();
	char a=' ';	
	for (List<String> sentence: document) 
        {
            List<String> newTokens = new ArrayList<String>();
				
            // If a string has letters or numbers, it is not just
    	    // punctuation, so add it to the list.
            for (String word : sentence) 
            {
		for(int l=0;l<word.length();l++)
                {
                    if(isPunct(word.charAt(l)))
                    {
                        word.replace(word.charAt(l),'0');
                                  
                    }
                }
                newTokens.add(word);
            }
            processed.add(newTokens);
        }		
	return processed;
    }
    
    public ArrayList<List<String>> makeLowercase(ArrayList<List<String>> document) {
		
	ArrayList<List<String>> processed = new ArrayList<>();
		
	for (int i=0; i<document.size(); i++) {
            List<String> oldSentence = document.get(i);
            List<String> newSentence = new ArrayList<>();
			
            for (int j=0; j<oldSentence.size(); j++) {
		String word = oldSentence.get(j).toLowerCase();
		newSentence.add(word);
            }
			
	    processed.add(newSentence);
	}
		
	return processed;
    }

    public ArrayList<List<String>> stopWordProcess(ArrayList<List<String>> document) 
    {
	return removeStopwords(removePunctuation(makeLowercase(document)));
    }
    
    public ArrayList<List<String>> stem(ArrayList<List<String>> passage)
    {
        ArrayList<List<String>> processed = new ArrayList<>();
        PorterStemmer ps=new PorterStemmer();
        for (int i=0; i<passage.size(); i++) 
        {
            List<String> oldSentence = passage.get(i);
            List<String> newSentence = new ArrayList<>();
            for (int j=0; j<oldSentence.size(); j++) 
            {
                String prestem=oldSentence.get(j);
		String word = ps.stem(prestem);
		newSentence.add(word);
            }		
            processed.add(newSentence);
	}
		
	return processed;
    } 
    public Map<String, Integer> keyword (ArrayList<List<String>> passage,String file)
    {
        //System.out.println(passage.size());
        for(int i=0;i<passage.size();i++)
        {
            List<String> oldSentence = passage.get(i);
            //System.out.println(oldSentence.size());
            for (int j=0; j<oldSentence.size(); j++) 
            {
                String word=oldSentence.get(j);
                
                if (wordCount.containsKey(word)) 
                {
        // Map already contains the word key. Just increment it's count by 1
                    wordCount.put(word, wordCount.get(word) + 1);
                } 
                else 
                {
        // Map doesn't have mapping for word. Add one with count = 1
                    wordCount.put(word, 1);
                }
            }
            
        }
        return wordCount;
        
    }   
}