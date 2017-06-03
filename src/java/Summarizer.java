
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Summarizer {
    
    Preprocessor p=new Preprocessor();
    Util u=new Util();
    double pos,length,name,keyw,num;
    String _class;
    PostClassifier pc=new PostClassifier();
    public Summarizer()
    {
        Classifier c=new Classifier();
    }
    
    public ArrayList<String> summarize(String text)
    {
        ArrayList<String> FinalSummary=new ArrayList<String>();
        try {
            String[] doc =p.seperator(text);
            int totwords;
            String words[]=p.tokenize(text);
            totwords=words.length;
            int size=doc.length;  
            u.setFileString(text);
            String key=u.generateKey();
            Feature f=new Feature();
            
            for(int i=0;i<size;i++)
            {
                String sent=doc[i];
                pos=f.positionLearner(i+1,size+1);
                length=f.lengthLearner(sent, totwords);
                name=f.nameLearner(sent);
                keyw=f.keyLearner(sent,key);
                num=f.numLearner(sent);
                _class=pc.finalClass(pos,length,name,keyw,num); 
                if(_class=="Yes")
                {
                    FinalSummary.add(sent);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return FinalSummary;
        
    }   
}
