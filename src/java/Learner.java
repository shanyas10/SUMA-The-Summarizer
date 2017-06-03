
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sanya
 */
public class Learner {
    
    
    
    String fileName;
    String fileAsString;
    Util u=new Util();
    Preprocessor p=new Preprocessor();
    String[] sum ;
    List<String> sumArray;
    Database d=new Database();
    public Learner(int x)
    {
        fileName="E:/Summary/data/sum/"+x+".txt";
        u.readFile(fileName);
        sum=u.preProcess();
        sumArray=Arrays.asList(sum);
    }        
    public void keyLearner(String sent,String key)
    {
        String words[] = null;
        double numkey=0,wordSent,keyVal;
        try 
        {
            words=p.tokenize(sent);
        } 
        catch (IOException ex) 
        {
            System.out.println(ex);
        }
        for (String word : words) {
            if (word.equals(key)) {
                numkey++;
            }
        }
        wordSent=u.words(sent);
        keyVal=numkey/wordSent;
        keyVal=Math.floor(keyVal*100)/100;
        //System.out.println(keyVal);
        d.set(keyVal,"Key");
    }
    public void numLearner(String sent)
    {
        String words[] = null;
        double numkey=0,wordSent,keyVal;
        try 
        {
            words=p.tokenize(sent);
        } 
        catch (IOException ex) 
        {
            System.out.println(ex);
        }
        for (String word : words) {
            if (word.matches(".*\\d+.*"))
            {
                //System.out.println(word);
                numkey++;
            }
        }
        wordSent=u.words(sent);
        keyVal=numkey/wordSent;
        keyVal=Math.floor(keyVal*100)/100;
        d.set(keyVal,"Number");
    }
    public void nameLearner(String sent)
    {
        String words[] = null;
        double numName=0,wordSent,val;
        numName=p.findName(sent);
        wordSent=u.words(sent);
        val=numName/wordSent;
        val=Math.floor(val*100)/100;
        //System.out.println(val);
        d.set(val,"Name");
    }
    
    public void positionLearner(double pos, double size)
    {
        //System.out.println(id);
        double position;
        position=((size-pos)/size)*10;
        position= Math.floor(position*100)/100;
        //System.out.println(position);
        d.set(position,"Position"); 
    }
    public void lengthLearner(String sent, double totword)
    {
        double length, wordSent;
        wordSent=u.words(sent);
        length=(wordSent/totword)*100;
        //System.out.println(length);
        length= Math.floor(length*100)/100;
        d.set(length,"Length");
    }
    
    public void icommaLearner(String sent)
    {
        double icomma=0;
        try
        {
            //System.out.println(sent);
            icomma=u.icomma(sent);
            //System.out.println(icomma);
        }
        catch(Exception e)
        {
        }
    }
    
    public void classifier(String sent)
    {           
        if(sumArray.contains(sent))
            d.set("Yes");
        else
            d.set("No");
    }

    public void storeWeight() {
        d.store();
    }

    void names(String sent, int totwords) {
        //To change body of generated methods, choose Tools | Templates.
    }


}
