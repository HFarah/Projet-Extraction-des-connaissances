
package projetextractionconnaissance2016;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.abs;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.annolab.tt4j.ExecutableResolver;
import org.annolab.tt4j.PlatformDetector;
import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;
import static projetextractionconnaissance2016.VueProjet.file;
import static projetextractionconnaissance2016.VueProjet.id_doc;

public class IndexClass {
    
    private Connection con;
    public boolean actionIndexe = true;
    
    public void indexCentral(){
        
        VueProjet.jLabel_TotalFiles.setText("Nombre total de fichiers : " + file.length);

        Timer Tcontrol = new Timer();
        Timer Tcontrol4 = new Timer();
        Timer Tcontrol5 = new Timer();
        Timer Tcontrol6 = new Timer();
        Timer Tcontrol7 = new Timer();
        Timer Tcontrol8 = new Timer();
        Timer Tcontrol9 = new Timer();

        RemindTask rt1 = new RemindTask(0, (file.length / 10));
        RemindTask rt2 = new RemindTask((file.length / 10) + 1, (file.length / 5));

        RemindTask rt3 = new RemindTask((file.length / 5) + 1, 2 + (file.length / 5));

        RemindTask rt4 = new RemindTask(2 + (file.length / 5) + 1, 4 + (file.length / 5));
        RemindTask rt5 = new RemindTask(4 + (file.length / 5) + 1, 3 * (file.length / 5));
        RemindTask rt6 = new RemindTask(3 * (file.length / 5) + 1, 3 * (file.length / 5) + 2);
        RemindTask rt7 = new RemindTask(3 * (file.length / 5) + 3, file.length - 1);

        Tcontrol.schedule(rt1, 0, 1);
        Tcontrol4.schedule(rt2, 0, 1);
        Tcontrol5.schedule(rt3, 0, 1);
        Tcontrol6.schedule(rt4, 0, 1);
        Tcontrol7.schedule(rt5, 0, 1);
        Tcontrol8.schedule(rt6, 0, 1);
        Tcontrol9.schedule(rt7, 0, 1);

        VueProjet.jProgressBar_Pretraitement.setMaximum(file.length);
        VueProjet.jProgressBar_Pretraitement.setString("Traitement en cours...");
        VueProjet.jProgressBar_Pretraitement.setStringPainted(true);
        VueProjet.jLabel_Pretraitement.setText("Documents Prétraités :  " + k);
        VueProjet.jLabel_DocPretrRestante.setText("Documents restants : " + (file.length - k));
        VueProjet.jBouton_OuvrirDocPretraitement.setEnabled(false);
        VueProjet.jBouton_lancerPretraitement.setEnabled(false);   
       
    }
    
    public void indexStopWordsElimination() throws ClassNotFoundException, SQLException{
        
        
        con = ConnexionDB.getConnection();
        
        Statement requete = con.createStatement();
        
        Statement requete1 = con.createStatement();
        
        //Vider la table 
        requete.executeUpdate("DELETE FROM \"indexmotstopwords\""); 
        
        //Replissage de la table
        requete.executeUpdate("INSERT INTO \"indexmotstopwords\" (\"MOT_IN\",\"TF\",\"ID_DOC\",\"TF_IDF\",\"CLASSE\",\"TOKEN\") "
                          + " SELECT \"MOT_IN\", \"TF\", \"ID_DOC\", \"TF_IDF\", \"CLASSE\", \"TOKEN\" FROM \"indexmotdistinct\" "
                          + " WHERE \"MOT_IN\" NOT IN (SELECT DISTINCT(\"MOTIND\") FROM \"motIndecidable\")");
              
        /*ResultSet rs = requete.executeQuery("SELECT DISTINCT(\"MOT_IN\") as mot FROM \"indexmotstopwords\"");
        
        while (rs.next()) {
            
            String mot = rs.getString("mot").trim();
            
            if(StopWordClass.isStopWord(mot)){
                              
                requete1.executeUpdate("DELETE FROM \"indexmotstopwords\" WHERE \"MOT_IN\"='"+mot+"'"); 
             
            }
            
        }
        
        rs.close();*/
    }
    
    public void indexStopWordsEliminationSeuilMot() throws ClassNotFoundException, SQLException, IOException{

             Connection conn=ConnexionDB.getConnection();
            
             Statement requete = conn.createStatement();
             
             Statement requete2=conn.createStatement();
             
             requete.executeUpdate("DELETE FROM \"indexmotstopwordsseuil\"");
                          
             ResultSet rs=requete.executeQuery("SELECT \"ID_DOC\" FROM \"document\"");
             
             while (rs.next()) 
             {
                requete2.executeUpdate("INSERT INTO \"indexmotstopwordsseuil\" (\"MOT_IN\",\"TF\",\"ID_DOC\",\"TF_IDF\",\"CLASSE\",\"TOKEN\") "
                        + "            SELECT \"MOT_IN\", \"TF\", \"ID_DOC\", \"TF_IDF\", \"CLASSE\", \"TOKEN\" "
                        + "            FROM \"indexmot\" "
                        + "            WHERE \"ID_DOC\"='"+rs.getString("ID_DOC")+"'"
                        + "            AND \"MOT_IN\" NOT IN (SELECT \"MOTIND\" FROM \"motIndecidable\") "
                        + "            ORDER BY \"TF_IDF\" DESC LIMIT 5");
             }
            
             rs.close();
    }
    
    /*public void indexStopWordsEliminationSeuilMot() throws ClassNotFoundException, SQLException, IOException{
              
             String pathToSWN = "./fichiersArff/SentiWordNet.txt";
             
             SentiWordNet sentiwordnet = new SentiWordNet(pathToSWN);

             Connection conn=ConnexionDB.getConnection();
            
             Statement requete = conn.createStatement();
             
             Statement requete2=conn.createStatement();
             
             
            //Vider la table 
             requete.executeUpdate( "TRUNCATE TABLE temporairearff2" ); 
        
            //Replissage de la table
             requete.executeUpdate("INSERT INTO \"temporairearff2\" (\"MOT_IN\",\"TF\",\"ID_DOC\",\"TF_IDF\",\"CLASSE\",\"TOKEN\") SELECT \"MOT_IN\", \"TF\", \"ID_DOC\", \"TF_IDF\", \"CLASSE\", \"TOKEN\" FROM \"indexmot\"");
            
            //Traitement sémantique
            ResultSet rs = requete2.executeQuery("SELECT DISTINCT(\"MOT_IN\") as mot FROM \"temporairearff2\"");
            
            double poid;
                            
            while (rs.next()) {

                String mot = rs.getString("mot");

                if(sentiwordnet.extract(mot)!=null)
                {
                    poid = sentiwordnet.extract(mot);

                      if((poid<0.25) && (poid>=0))
                      {      
                            requete.executeUpdate("DELETE FROM \"temporairearff2\" WHERE \"MOT_IN\"='"+mot+"'"); 
                      }

                }else
                {
                    requete.executeUpdate("DELETE FROM \"temporairearff2\" WHERE \"MOT_IN\"='"+mot+"'"); 
                }
            }
           
            
             requete.executeUpdate("DELETE FROM \"indexmotstopwordsseuil\"");
             
             rs=requete.executeQuery("SELECT DISTINCT \"ID_DOC\" FROM \"temporairearff2\"");
             
             while (rs.next()) 
             {
                requete2.executeUpdate("INSERT INTO \"indexmotstopwordsseuil\" (\"MOT_IN\",\"TF\",\"ID_DOC\",\"TF_IDF\",\"CLASSE\",\"TOKEN\") "
                        + "            SELECT \"MOT_IN\", \"TF\", \"ID_DOC\", \"TF_IDF\", \"CLASSE\", \"TOKEN\" "
                        + "            FROM \"temporairearff2\" "
                        + "            WHERE \"ID_DOC\"='"+rs.getString("ID_DOC")+"'"
                        + "            AND \"MOT_IN\" NOT IN (SELECT \"MOTIND\" FROM \"motIndecidable\") "
                        + "            ORDER BY \"TF_IDF\" LIMIT 10");
             }
   
    }*/

    public void indexFiltreMorphSyntax() throws SQLException, ClassNotFoundException{
        
        con = ConnexionDB.getConnection();
        
        Statement requete = con.createStatement();
        Statement requete2 = con.createStatement();

        //'NNS','NN'
        //'RB','RBS','RBR'
        //'JJ','JJR','JJS'
        //'VV','VVZ','VVP','VVW','VVD','VVG'
        
        ArrayList<String> gramaticalRole = new ArrayList<>(Arrays.asList("NNS","NN","RB","RBS","RBR","JJ","JJR","JJS"));
        
        ArrayList<String> motTraite = new ArrayList<>();
        
        //Vider la table
        requete.executeUpdate("DELETE FROM \"indexmotmorphosyntax\"");
        
        ResultSet rs = requete.executeQuery("SELECT DISTINCT \"MOT_IN\" as mot, \"TOKEN\" as token FROM \"indexmot\"");

        while (rs.next()) {
            
            if(gramaticalRole.contains(rs.getString("token")))
            {
                if(!motTraite.contains(rs.getString("mot")))
                {
                    requete2.executeUpdate("INSERT INTO \"indexmotmorphosyntax\" (\"MOT_IN\",\"TF\",\"TF_IDF\",\"ID_DOC\",\"CLASSE\") "
                        + "           SELECT \"MOT_IN\", \"TF\", \"TF_IDF\", \"ID_DOC\", \"CLASSE\" FROM \"indexmot\" "
                        + "           WHERE \"TOKEN\"='"+rs.getString("token")+"' "
                        + "           AND \"MOT_IN\"='"+rs.getString("mot")+"' "
                        + "           ORDER BY \"TF_IDF\" DESC"
                        + "           LIMIT 1");
                    
                    motTraite.add(rs.getString("mot"));
                }
                
            }
            
        }
        
        rs.close();
    }
 
    public void indexSemantique() throws ClassNotFoundException, SQLException, IOException{
       
        con = ConnexionDB.getConnection();
        Statement requete = con.createStatement();
        Statement requete2 = con.createStatement();

        String pathToSWN = "./fichiersArff/SentiWordNet.txt";
        
        SentiWordNet sentiwordnet = new SentiWordNet(pathToSWN);

        //Vider la table
        requete.executeUpdate("DELETE FROM \"indexmotsemantique\"");
        
        ResultSet rs = requete.executeQuery("SELECT DISTINCT(\"MOT_IN\") as mot FROM \"indexmot\"");

        while (rs.next()) {
            
            String mot = rs.getString("mot");
             
            double poid;
            
            if(sentiwordnet.extract(mot)!=null)
            {
                poid = sentiwordnet.extract(mot);
                  
                  if(!((poid<0.25) && (poid>=0)))
                  {      
                        
                        //Remplissage de la table
                        requete2.executeUpdate("INSERT INTO \"indexmotsemantique\" (\"MOT_IN\",\"TF\",\"ID_DOC\",\"TF_IDF\",\"CLASSE\",\"TOKEN\") "
                                            + "SELECT  \"MOT_IN\",\"TF\",\"ID_DOC\",\"TF_IDF\",\"CLASSE\",\"TOKEN\" "
                                            + "FROM \"indexmot\" "
                                            + "WHERE \"MOT_IN\"='"+mot+"' "
                                            + "ORDER BY \"TF_IDF\" "
                                            + "LIMIT 1");
                  }
                  
            }

        }
        
        rs.close();
    }
    
    public void indexFiltreCombine() throws ClassNotFoundException, SQLException{
    
        con = ConnexionDB.getConnection();
        
        Statement requete = con.createStatement();
        
        //Vider la table
        requete.executeUpdate("DELETE FROM \"indexmotcombine\"");
        
        //Commencer à construire
        Statement requete2 = con.createStatement();
        
        ResultSet rs=requete2.executeQuery("SELECT \"ID_DOC\" FROM \"document\"");
             
        while (rs.next()) 
        {

            requete.executeUpdate("INSERT INTO \"indexmotcombine\" (\"MOT_IN\",\"TF\",\"ID_DOC\",\"TF_IDF\",\"CLASSE\") "
                            + "            SELECT \"MOT_IN\", \"TF\", \"ID_DOC\", \"TF_IDF\", \"CLASSE\" "
                            + "            FROM \"indexmot\" "
                            + "            WHERE \"ID_DOC\"='"+rs.getString("ID_DOC")+"'"
                            + "            AND \"MOT_IN\" NOT IN (SELECT \"MOTIND\" FROM \"motIndecidable\") "
                            + "            AND \"TOKEN\" IN ('NNS','NN','JJ','JJR','JJS','RB','RBS','RBR') "
                            + "            ORDER BY \"TF_IDF\" LIMIT 6");
                
        }
        
        rs.close();
    }

    ArrayList<String> stopwords = new ArrayList<>(Arrays.asList("an","nor", "or", "yet","musical","j","same","result","turn","viewing","ten","god","role","strong","string","run","kid","home","get","direct","call","while","another","American","Ann","butt","but","amid","us","when","what","own","out","day","will","say","also","dr","clear","woman","want","end","try","those","non","up","even","let","go","can","th","long","child","sub","to","one","two","too","for","much","be","im","so","oh","you","lot","see","fan","hi","boy","a","b","c","d","e","f","g","h","i","g","k","l","m","n","o","p","q","r","s","u","w","y","t","the","width", "your", "that", "these", "there","their","welcome","who","which","wish","Indian","adam","yours","part","mini","young","robert","title","toto","stop","segal","public","papa","book","zero","okay","without","port","shoe","page","copy","nadir","thats","themselves","five","some","think","show","commentary","sit","wife","blue","thomas","plus","period","whole","date","easy","next","dollar","scene","face","hour","very","regarded","student","school","European","director","would","where","whose","life","point","Arab","it","its","come","he","we","she","sister","company","they","them", "this", "first", "last", "second", "the", "about", "above", "after", "tea","Marcus","than","again","edies","guitar","halo","work","girl","want","video","viewer","start","stage", "against", "all","back","tell","edie","section","self","edith","woman","am","africa","albert","aged","post","bicker","friend","watch","english","time","jeremy","idea","austen","people","four","actor","member","open","toni","send","oscar","emma","mother", "an", "and","choice","old","year","word","room","cube","watch","miami","any", "are", "s", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "could", "t", "did", "b", "do", "does", "h", "doing", "don't", "down", "during", "each", "few", "for", "from", "further", "had", "hadn't", "has", "hasn't", "have", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", "its", "itself", "let's", "me", "more", "most", "mustn't", "my", "myself", "no", "nor","just","film","ever", "just","movie","than","class", "where","of","with","such","main","website","comment" ,"off", "on", "once", "only", "or", "other", "ought", "our", "ours"
    ));
    
    private int k = 0;
    
    class RemindTask extends TimerTask {

        public int d, f;

        public RemindTask(int debut, int fin) {
            d = debut;
            f = fin;
            System.out.println("timer =" + this.getClass().getSimpleName() + " d=" + d + " f=" + f);
            
        }
        
        @Override
        public void run() {

            try {
                
                FileReader monFichier = null;
                BufferedReader In = null;
                
                String line = null;
                
                Vector<String> vectorMot = new Vector<>();
                
                
                try {
                    monFichier = new FileReader(file[d].getAbsolutePath());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(IndexClass.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                In = new BufferedReader(monFichier);
                
                
                
                try {
                    while ((line = In.readLine()) != null) {
                        
                        StringTokenizer st = new StringTokenizer(line, "[ ï»¿,–;’:?!$^¨'~%#&[]-_?„><=*$°{}.)\"•+(/+0123456789 ]");
                        
                        while (st.hasMoreTokens()) {
                            
                            String mot1 = st.nextToken().toLowerCase().trim();
                            
                             if (!stopwords.contains(mot1)) {
                                    
                                    vectorMot.add(mot1);
                                
                              }
                            
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(IndexClass.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                  
                con = ConnexionDB.getConnection();
               
                Statement requete = con.createStatement();
                
                requete.executeUpdate("INSERT INTO \"document\" (\"NOM\",\"classe\",\"CHEMIN\") VALUES ('" + file[d].getName() + "','" + file[d].getParentFile().getName() + "','" + file[d].getAbsolutePath() + "')");
                
                ResultSet rs = requete.executeQuery("SELECT \"ID_DOC\" FROM \"document\" WHERE \"NOM\"='" + file[d].getName() + "'");
               
                while (rs.next()) {
                    id_doc[d] = rs.getInt("id_doc");
                }
                
                
                TreeTaggerWrapper tt = new TreeTaggerWrapper<>();
                
                ExecutableResolver er = new ExecutableResolver() {
                    @Override
                    public void setPlatformDetector(PlatformDetector pd) {
                        // throw new UnsupportedOperationException("Not supported yet.");
                    }
                    
                    @Override
                    public void destroy() {
//                       throw new UnsupportedOperationException("Not supported yet.");
                    }
                    
                    @Override
                    public String getExecutable() throws IOException {
                        //throw new UnsupportedOperationException("Not supported yet.");
                        String url = "/TreeTagger/bin/tree-tagger.exe";
                        return url;
                    }
                };
                
                //st2 = new StringTokenizer(words, " ");
                tt.setModel("/TreeTagger/model/english.par");
                
                tt.setExecutableProvider(er);
                
                String[] argum = {"-sgml", "-token", "-lemma"};
               
                tt.setArguments(argum);
                
                tt.setHandler(new TokenHandler<String>() {
                    
                    @Override
                    public void token(String o, String string, String string1) {
                        
                        // System.out.println(o + "\t" + string + "\t" + string1);
                        
                        if (!string1.equals("<unknown>")) {
                            
                            try {
                                con = ConnexionDB.getConnection();
                                
                                Statement requete22 = con.createStatement();
                                
                                requete22.executeUpdate("INSERT INTO \"termes\" (\"MOT\",\"CLASSE\",\"POS\",\"LEMME\",\"TF\",\"ID_DOC\") VALUES ('" + o + "','" + file[d].getParentFile().getName() + "','" + string + "','" + string1 + "',1," + id_doc[d] + ")");
                            
                            } catch (SQLException | ClassNotFoundException ex) {
                                Logger.getLogger(IndexClass.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            
                        }
                        
                    }
                });
                
                
                try {
                    tt.process(vectorMot);
                } catch (IOException | TreeTaggerException ex) {
                    Logger.getLogger(IndexClass.class.getName()).log(Level.SEVERE, null, ex);
                }
                vectorMot.clear();
                
                tt.destroy();
                
                int nbr = 0;
                int count = 0;

                con=ConnexionDB.getConnection();

                Statement requete2 = con.createStatement();

                requete2.executeUpdate("INSERT INTO \"indexmot\" (\"MOT_IN\",\"TF\",\"ID_DOC\",\"CLASSE\",\"TOKEN\")SELECT \"LEMME\", sum(\"TF\") as TF, \"ID_DOC\", \"classe\", \"POS\" FROM termes WHERE \"ID_DOC\"=" + id_doc[d] + " GROUP BY \"LEMME\"");

                VueProjet.jProgressBar_Pretraitement.setValue(k + 1);
                VueProjet.jLabel_Pretraitement.setText("Documents Prétraités :  " + (k + 1));
                VueProjet.jLabel_DocPretrRestante.setText("Documents restants :" + (file.length - (k + 1)));
                k++;
                
                requete2.executeUpdate("DELETE FROM \"termes\" where \"ID_DOC\"=" + id_doc[d]);
                
                if (d == f) {
                    
                    if (file.length == k) {
                        
                        k = 0;
                        actionIndexe = true;
                        VueProjet.jBouton_lancerPretraitement.setEnabled(false);
                        VueProjet.jProgressBar_Pretraitement.setValue(0);
                        VueProjet.jProgressBar_Pretraitement.setStringPainted(false);
                        VueProjet.jLabel_Pretraitement.setText("");
                        VueProjet.jLabel_DocPretrRestante.setText("");
                        VueProjet.jLabel_TotalFiles.setText("");
                        
                        VueProjet.jTextArea_PathFilesPretraitement.setText("");
                        VueProjet.jBouton_OuvrirDocPretraitement.setEnabled(true);
                        
                    }
                    System.out.println("timer =" + this.getClass().getSimpleName() + " d=" + d + " f=" + f + " terminée");
                    this.cancel();
                    
                }
                
                d++;
            } catch (SQLException | IOException | ClassNotFoundException ex) {
                Logger.getLogger(IndexClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
    
    } 
    
}
