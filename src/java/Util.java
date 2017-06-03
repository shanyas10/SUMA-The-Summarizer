
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sanya
 */
public class Util {
    
    String fileAsString;
    Preprocessor p=new Preprocessor();
    String[] preProcess=null;
    //Learner l=new Learner();
    public Util()
    {
    }
    public void readFile(String fileName) 
    { 
        try {
                //System.out.println("hi");
		BufferedReader buf = new BufferedReader (new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
                String line = buf.readLine(); 
                StringBuilder sb = new StringBuilder(); 
                while(line != null)
                {      
                    sb.append(line).append("\n"); 
                    line = buf.readLine(); 
                } 
                fileAsString = sb.toString(); 
                //System.out.println(fileAsString);
                
	} 
        catch (IOException e) 
        {
            System.out.println(e);
	}
        
    }
    
    public void setFileString(String text)
    {
       fileAsString=text; 
    }
    public  String[] preProcess()
    {    
        try 
        {
            preProcess=p.seperator(fileAsString);
        }
        catch (IOException ex) 
        {         
        }
        return preProcess;
    }
    public int totwords()
    {
        int totwords;
        String[] preProcess = null;
        try
        {
            preProcess=p.tokenize(fileAsString);
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
        totwords=preProcess.length;
        return totwords;
    }

    public int words(String get) {
        int word=0;
        try {
            word=p.tokenize(get).length;
            //System.out.println(word);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return word;
    }
    public int icomma(String sent)
    {   
        int icoma = 0;
        for( int i=0; i<sent.length(); i++ ) 
        {
            if( (sent.charAt(i) == '\'')||(sent.charAt(i)=='\"') ) 
            {
                //System.out.println("po");
                icoma++;
            } 
            else
            {
                System.out.println(sent.charAt(i));
            }
        }
        return icoma;
    
    }  
    public ArrayList<List<String>> keyPreProcess()
    {
        try 
        {
            preProcess=p.seperator(fileAsString);
        } 
        catch (IOException ex) {
            
        }
        List<String> sentList= Arrays.asList(preProcess);
        //System.out.println(sentList.size());
        List<String> wordList;
        String[] words=null;
        ArrayList<List<String>> ListOList = new ArrayList<List<String>>();
        for(int i=0;i<sentList.size();i++)
        {
            words=sentList.get(i).split(" ");
            wordList=Arrays.asList(words);
            ListOList.add(wordList);  
        }
        
        ListOList=p.stopWordProcess(ListOList);
        //System.out.println(ListOList.get(0).size());
        ListOList=p.stem(ListOList);
        return ListOList;     
    }
    public String generateKey()
    { 
        ArrayList<List<String>> ListOList = new ArrayList<List<String>>();
        Map<String, Integer> keyword;
        ListOList=keyPreProcess();
        keyword=p.keyword(ListOList,fileAsString);
        Set<Entry<String, Integer>> set = keyword.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            @Override
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        Map.Entry<String,Integer> entry=list.iterator().next();
        String key= entry.getKey();
        return key;    
    }
}