/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easyconsoleinputoutput;

/**
 * @author Evans Chen
 */
public class EasyConsoleInputOutput
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException
    {
        Program program;
        program = new Program();
        
        program.init();
        
        while(true)
        {
            program.periodic();
            Thread.sleep(20);
        }
    }
}
