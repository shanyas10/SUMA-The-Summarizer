/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sanya
 */
public class FileRead {
    
    Trainer t= new Trainer();
    
    void read()
    {
        for(int i=1;i<=20;i++)
        {
            t.train(i);
            System.out.println(i);
            
        }
    }
    
}
