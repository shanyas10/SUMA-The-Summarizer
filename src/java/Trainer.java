
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




public class Trainer {
    
    String fileName;
    String fileAsString;
    Util u=new Util();
    public Trainer()
    {
        
    }
    
    public void train(int x)
    {
        fileName="E:/Summary/data/docs/"+x+".txt";
        //System.out.println(fileName);
        u.readFile(fileName);
        String key=u.generateKey();
        String[] doc =u.preProcess();
        int totwords=u.totwords();
        int size=doc.length;
        Learner l=new Learner(x);
        //System.out.println("size is "+size);
        for(int i=0;i<size;i++)
        {
            String sent=doc[i];
            l.positionLearner(i+1,size+1);
            l.lengthLearner(sent, totwords);
            l.classifier(sent);
            l.names(sent,totwords);
            l.keyLearner(sent,key);
            l.numLearner(sent);
            l.nameLearner(sent);
            l.storeWeight();
            
        }                                                                                                     
    }   
}
