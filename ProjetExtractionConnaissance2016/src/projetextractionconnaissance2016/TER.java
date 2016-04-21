/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetextractionconnaissance2016;

/**
 *
 * @author proprietaire
 */
public class TER {

    /**
     * @param args the command line arguments
     */

            
    public static void main(String[] args) 
    {
        
        try {
            
            String pathToSWN = "./fichiersArff/SentiWordNet.txt";
             
            SentiWordNet sentiwordnet = new SentiWordNet(pathToSWN);
            
            double poid;
            
            if(sentiwordnet.extract("love")!=null)
            {
                poid = sentiwordnet.extract("love");
                
                  /*if(poid>=0.75)
                            System.out.println("very positive");
                    else if(poid > 0.25 && poid<0.5)
                        System.out.println("positive");
                    else if(poid>=0.5)
                        System.out.println("positive");
                    else if(poid < 0 && poid>=-0.25)
                        System.out.println("négative");
                    else if(poid < -0.25 && poid>=-0.5)
                        System.out.println("négative");
                    else if(poid<=-0.75)
                        System.out.println("very négative");
                    else
                        System.out.println("Poid:"+poid+" :neutral");*/
                  
                  if((poid<0.25) && (poid>=0))
                  {
                        System.out.println("Poid:"+poid+" :neutral");
                  }
            }

        } catch (Exception e) {
        }
         
        
        
        
        
    }

}