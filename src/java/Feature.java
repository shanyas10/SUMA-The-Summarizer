
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
public class Feature {
    
    Util u=new Util();
    Preprocessor p=new Preprocessor();
    public Feature()
    {   
    }
    public double keyLearner(String sent,String key)
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
        return keyVal;
        //System.out.println("keyVal"+keyVal);
    }
    public double numLearner(String sent)
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
                numkey++;
            }
        }
        wordSent=u.words(sent);
        keyVal=numkey/wordSent;
        keyVal=Math.floor(keyVal*100)/100;
        return keyVal;
    }
    public double nameLearner(String sent)
    {
        String words[] = null;
        double numName=0,wordSent,val;
        numName=p.findName(sent);
        wordSent=u.words(sent);
        val=numName/wordSent;
        val=Math.floor(val*100)/100;
        return val;
    }
    
    public double positionLearner(double pos, double size)
    {
        //System.out.println(id);
        double position;
        position=((size-pos)/size)*10;
        position= Math.floor(position*100)/100;
        return position;
    }
    public double lengthLearner(String sent, double totword)
    {
        double length, wordSent;
        wordSent=u.words(sent);
        length=(wordSent/totword)*100;
        length= Math.floor(length*100)/100;
        return length;
    } 
    
}
