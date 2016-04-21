/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetextractionconnaissance2016;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
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
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
public class ClassifierBayes {
    /*
    *       Index central 
    */
    private  String chemin, chemin1;
   
    public ClassifierBayes(String chemin, String chemin1){
        this.chemin=chemin;
        this.chemin1 = chemin1;
    }
    
    public void BayesCentral(){
        Tcontrol = new Timer();
        RemindTask rt1 = new RemindTask();
        Tcontrol.schedule(rt1, 0, 1);
        VueProjet.jButtonBayesOuvrirDoc.setEnabled(false);
        VueProjet.jButton_LancerBayes.setEnabled(false);
    }
  
    ArrayList<String> stopwords = new ArrayList<>(Arrays.asList("an","nor", "or", "yet","musical","j","same","result","turn","viewing","ten","god","role","strong","string","run","kid","home","get","direct","call","while","another","American","Ann","butt","but","amid","us","when","what","own","out","day","will","say","also","dr","clear","woman","want","end","try","those","non","up","even","let","go","can","th","long","child","sub","to","one","two","too","for","much","be","im","so","oh","you","lot","see","fan","hi","boy","a","b","c","d","e","f","g","h","i","g","k","l","m","n","o","p","q","r","s","u","w","y","t","the","width", "your", "that", "these", "there","their","welcome","who","which","wish","Indian","adam","yours","part","mini","young","robert","title","toto","stop","segal","public","papa","book","zero","okay","without","port","shoe","page","copy","nadir","thats","themselves","five","some","think","show","commentary","sit","wife","blue","thomas","plus","period","whole","date","easy","next","dollar","scene","face","hour","very","regarded","student","school","European","director","would","where","whose","life","point","Arab","it","its","come","he","we","she","sister","company","they","them", "this", "first", "last", "second", "the", "about", "above", "after", "tea","Marcus","than","again","edies","guitar","halo","work","girl","want","video","viewer","start","stage", "against", "all","back","tell","edie","section","self","edith","woman","am","africa","albert","aged","post","bicker","friend","watch","english","time","jeremy","idea","austen","people","four","actor","member","open","toni","send","oscar","emma","mother", "an", "and","choice","old","year","word","room","cube","watch","miami","any", "are", "s", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "could", "t", "did", "b", "do", "does", "h", "doing", "don't", "down", "during", "each", "few", "for", "from", "further", "had", "hadn't", "has", "hasn't", "have", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", "its", "itself", "let's", "me", "more", "most", "mustn't", "my", "myself", "no", "nor","just","film","ever", "just","movie","than","class", "where","of","with","such","main","website","comment" ,"off", "on", "once", "only", "or", "other", "ought", "our", "ours"
    ));    
        private Timer Tcontrol;

        private int k = 0;

        private ResultSet rs ;
        private static Connection con;


    class RemindTask extends TimerTask {

            @Override
            public void run() {

                try {

                    FileReader monFichier = null;
                    BufferedReader In = null;
                    String line = null;


                    Vector<String> vectorMot = new Vector<String>();


                    try {
                        monFichier = new FileReader(file[0].getAbsolutePath());
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    con = ConnexionDB.getConnection();

                    Statement requete = con.createStatement();

                    requete.executeUpdate("INSERT INTO \"documenttest\" (\"NOM\",\"CLASSE\",\"CHEMIN\") VALUES ('" + file[0].getName() + "','?','" + file[0].getAbsolutePath() + "')");
                    ResultSet rs = requete.executeQuery("SELECT \"ID_DOC\" FROM \"documenttest\" WHERE \"NOM\"='" + file[0].getName() + "'");

                    while (rs.next()) {
                        id_doc[0] = rs.getInt("id_doc");
                    }

                    rs.close();
                    TreeTaggerWrapper tt = new TreeTaggerWrapper<String>();
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


                    tt.setModel("/TreeTagger/model/english.par");
                    tt.setExecutableProvider(er);

                    String[] argum = {"-sgml", "-token", "-lemma"};
                    tt.setArguments(argum);

                    tt.setHandler(new TokenHandler<String>() {
                        @Override
                        public void token(String o, String string, String string1) {

                            if (!string1.equals("<unknown>")) {

                                try {

                                    Statement requete = con.createStatement();
                                    requete.executeUpdate("INSERT INTO \"termes\" (\"MOT\",\"CLASSE\",\"POS\",\"LEMME\",\"TF\",\"ID_DOC\") VALUES ('" + o + "','?','" + string + "','" + o + "',1," + id_doc[0] + ")");

                                } catch (SQLException ex) {
                                    Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }


                        }
                    });


                    tt.process(vectorMot);
                    vectorMot.clear();
                    tt.destroy();

                    Statement requete2 = con.createStatement();

                    requete2.executeUpdate("INSERT INTO \"mot\" (\"MOT_IN\",\"TF\",\"ID_DOC\",\"CLASSE\",\"TOKEN\")SELECT \"LEMME\", sum(\"TF\") as TF, \"ID_DOC\",\"CLASSE\",\"POS\" FROM \"termes\" WHERE \"ID_DOC\"=" + id_doc[0] + " GROUP BY \"LEMME\"");

                    k++;

                    requete2.executeUpdate("DELETE FROM \"termes\" where \"ID_DOC\"=" + id_doc[0]);


                    // AlgoBayesGIMCIDF();


                    k = 0;
                    VueProjet.jTextArea_bayesVar.setText("");
                    VueProjet.jButtonBayesOuvrirDoc.setEnabled(true);
                    VueProjet.jButton_LancerBayes.setEnabled(true);

                    this.cancel();




                } catch (ClassNotFoundException | SQLException | IOException | TreeTaggerException ex) {
                    Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
                }




            }
        }   
   
    /*
    *      Index : elimination des stop words 
    */
    
    public void algoBayesIndexStopWords(){
        Tcontrol = new Timer();
        RemindTask1 rt1 = new RemindTask1();
        Tcontrol.schedule(rt1, 0, 1);
        VueProjet.jButtonBayesOuvrirDoc.setEnabled(false);
        VueProjet.jButton_LancerBayes.setEnabled(false);
    }

    class RemindTask1 extends TimerTask {

            @Override
            public void run() {

                try {

                    FileReader monFichier = null;
                    BufferedReader In = null;
                    String line = null;


                    Vector<String> vectorMot = new Vector<String>();


                    try {
                        monFichier = new FileReader(file[0].getAbsolutePath());
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    con = ConnexionDB.getConnection();

                    Statement requete = con.createStatement();

                    requete.executeUpdate("INSERT INTO \"documenttest\" (\"NOM\",\"CLASSE\",\"CHEMIN\") VALUES ('" + file[0].getName() + "','?','" + file[0].getAbsolutePath() + "')");
                    ResultSet rs = requete.executeQuery("SELECT \"ID_DOC\" FROM \"documenttest\" WHERE \"NOM\"='" + file[0].getName() + "'");

                    while (rs.next()) {
                        id_doc[0] = rs.getInt("id_doc");
                    }

                    rs.close();
                    TreeTaggerWrapper tt = new TreeTaggerWrapper<String>();
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

                    tt.setModel("/TreeTagger/model/english.par");
                    tt.setExecutableProvider(er);

                    String[] argum = {"-sgml", "-token", "-lemma"};
                    tt.setArguments(argum);

                    tt.setHandler(new TokenHandler<String>() {
                        @Override
                        public void token(String o, String string, String string1) {

                            if (!string1.equals("<unknown>")) {

                                try {

                                    Statement requete = con.createStatement();
                                    requete.executeUpdate("INSERT INTO \"termes\" (\"MOT\",\"CLASSE\",\"POS\",\"LEMME\",\"TF\",\"ID_DOC\") VALUES ('" + o + "','?','" + string + "','" + o + "',1," + id_doc[0] + ")");

                                } catch (SQLException ex) {
                                    Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }


                        }
                    });


                    tt.process(vectorMot);
                    vectorMot.clear();
                    tt.destroy();

                    Statement requete2 = con.createStatement();

                    requete2.executeUpdate("INSERT INTO \"mot\" (\"MOT_IN\",\"TF\",\"ID_DOC\",\"CLASSE\",\"TOKEN\")SELECT \"LEMME\", sum(\"TF\") as TF, \"ID_DOC\",\"CLASSE\",\"POS\" FROM \"termes\" WHERE \"ID_DOC\"=" + id_doc[0] + " GROUP BY LEMME");

                    k++;

                    requete2.executeUpdate("DELETE FROM \"termes\" where \"ID_DOC\"=" + id_doc[0]);


                    // AlgoBayesGIMCIDF();


                    k = 0;
                    VueProjet.jTextArea_bayesVar.setText("");
                    VueProjet.jButtonBayesOuvrirDoc.setEnabled(true);
                    VueProjet.jButton_LancerBayes.setEnabled(true);

                    this.cancel();




                } catch (ClassNotFoundException | SQLException | IOException | TreeTaggerException ex) {
                    Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
                }




            }
        }   

   
    /****************** Morphosyntaxique ************************************/
    
    public void algoBayesIndexMorphoSyntaxique(){
        Tcontrol = new Timer();
        RemindTask2 rt1 = new RemindTask2();
        Tcontrol.schedule(rt1, 0, 1);
        VueProjet.jButtonBayesOuvrirDoc.setEnabled(false);
        VueProjet.jButton_LancerBayes.setEnabled(false);
    }

    class RemindTask2 extends TimerTask {

            @Override
            public void run() {

                try {

                    FileReader monFichier = null;
                    BufferedReader In = null;
                    String line = null;


                    Vector<String> vectorMot = new Vector<String>();


                    try {
                        monFichier = new FileReader(file[0].getAbsolutePath());
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    con = ConnexionDB.getConnection();

                    Statement requete = con.createStatement();

                    requete.executeUpdate("INSERT INTO \"documenttest\" (\"NOM\",\"CLASSE\",\"CHEMIN\") VALUES ('" + file[0].getName() + "','?','" + file[0].getAbsolutePath() + "')");
                    ResultSet rs = requete.executeQuery("SELECT \"ID_DOC\" FROM \"documenttest\" WHERE \"NOM\"='" + file[0].getName() + "'");

                    while (rs.next()) {
                        id_doc[0] = rs.getInt("id_doc");
                    }

                    rs.close();
                    TreeTaggerWrapper tt = new TreeTaggerWrapper<String>();
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


                    tt.setModel("/TreeTagger/model/english.par");
                    tt.setExecutableProvider(er);

                    String[] argum = {"-sgml", "-token", "-lemma"};
                    tt.setArguments(argum);

                    tt.setHandler(new TokenHandler<String>() {
                        @Override
                        public void token(String o, String string, String string1) {

                            if (!string1.equals("<unknown>")) {

                                try {

                                    Statement requete = con.createStatement();
                                    requete.executeUpdate("INSERT INTO \"termes\" (\"MOT\",\"CLASSE\",\"POS\",\"LEMME\",\"TF\",\"ID_DOC\") VALUES ('" + o + "','?','" + string + "','" + o + "',1," + id_doc[0] + ")");

                                } catch (SQLException ex) {
                                    Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }


                        }
                    });


                    tt.process(vectorMot);
                    vectorMot.clear();
                    tt.destroy();

                    Statement requete2 = con.createStatement();

                    requete2.executeUpdate("INSERT INTO \"mot\" (\"MOT_IN\",\"TF\",\"ID_DOC\",\"CLASSE\",\"TOKEN\")SELECT \"LEMME\", sum(\"TF\") as TF, \"ID_DOC\",\"CLASSE\",\"POS\" FROM \"termes\" WHERE \"POS\" IN ('NNS','NN','JJ','JJR','JJS') AND \"ID_DOC\"=" + id_doc[0] + " GROUP BY \"LEMME\"");

                    k++;

                    requete2.executeUpdate("DELETE FROM \"termes\" where \"ID_DOC\"=" + id_doc[0]);


                    // AlgoBayesGIMCIDF();


                    k = 0;
                    VueProjet.jTextArea_bayesVar.setText("");
                    VueProjet.jButtonBayesOuvrirDoc.setEnabled(true);
                    VueProjet.jButton_LancerBayes.setEnabled(true);

                    this.cancel();




                } catch (ClassNotFoundException | SQLException | IOException | TreeTaggerException ex) {
                    Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
                }




            }
        }   

   
    
  /**
    * Bayes alogo implementation
    * @throws java.io.FileNotFoundException 
     
  */


   public void AlgoBayes() throws FileNotFoundException, IOException, Exception {

        try {
           // String chemin = "fichiersArff/arffIndexCentral.arff";
           // String chemin1 = "Domestique/arffIndexCentralTest.arff";
         
            
           
            BufferedReader breader = null;
            
            breader = new BufferedReader(new FileReader(chemin));
            Instances train = new Instances(breader);
            train.setClassIndex(train.numAttributes() - 1);
            breader.close();
            NaiveBayes nb = new NaiveBayes();
            nb.buildClassifier(train);
            weka.classifiers.Evaluation evl=new weka.classifiers.Evaluation(train);
         
            evl.crossValidateModel(nb, train, 5, new Random(1));
          
            ResultSet rs;
            int tab[][] = new int[2][train.numAttributes() - 1];
            List<Integer> v = new ArrayList<>();
            List<String> v1 = new ArrayList<>();
            List<Integer> v3 = new ArrayList<>();
            List<Integer> v4 = new ArrayList<>();
            Set<String> c = new HashSet<>();
            
            con = ConnexionDB.getConnection();
            Statement requete = con.createStatement();
            Statement requete1 = con.createStatement();
            
            rs = requete.executeQuery("SELECT \"ID_DOC\" as nbr FROM mot GROUP BY \"ID_DOC\"");

            while (rs.next()) {
             
                v.add(rs.getInt("nbr"));
            }
            for (int cc = 0; cc < v.size(); cc++) {
                System.out.println(v.get(cc));
            }

            int li;
            li = -1;

            FileWriter fichierOut = new FileWriter("C:/Users/aigle/Documents/NetBeansProjects/MemoireClassification/Domestique/arffIndexCentralTest.arff");

            BufferedWriter tmp = new BufferedWriter(fichierOut);
            PrintWriter out = new PrintWriter(tmp);
            if (li == -1) {
                try {
                    tmp.write("@relation indexation");
                    tmp.newLine();
                    tmp.newLine();

                    for (int jj = 0; jj < train.numAttributes(); jj++) {
                        tmp.write(train.attribute(jj).toString());
                        tmp.newLine();
                    }
                    tmp.newLine();
                    tmp.newLine();
                    tmp.newLine();
                    tmp.write("@data");
                    tmp.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            li = 1;
            if (li == 1) {
                for (int i = 0; i < v.size(); i++) {
                    
                    rs = requete1.executeQuery("SELECT \"MOT_IN\" as mt, \"TF\" FROM \"mot\" WHERE \"ID_DOC\"='" + v.get(i) + "' ");

                    while (rs.next()) {
                       
                        v1.add(rs.getString("mt").trim());
                        v4.add(rs.getInt("TF"));
                    }

                    requete.executeUpdate( "TRUNCATE TABLE mot" );


                    String s="";


                    for (int h = 0; h < v1.size(); h++) {

                        for (int jj = 0; jj < train.numAttributes() - 1; jj++) {
                            if (v1.get(h).equals(train.attribute(jj).name())) {
                               
                             v3.add(v4.get(h));
                            } else {
                                v3.add(0);
                            }

                        }
                        for (int ii = 0; ii < v3.size(); ii++) {
                            if (v3.get(ii) != 0) {
                                tab[0][ii] = v3.get(ii);
                                tab[1][ii] = ii;

                            }

                        }
                        v3.clear();
                    }
                    v1.clear();
                    v4.clear();
                    for (int u = 0; u < train.numAttributes() - 1; u++) {
                        s += tab[0][u] + ",";
                    }
                    s += "?";
                   

                    try {

                        tmp.write(s);
                        tmp.newLine();
                    } catch (IOException ex) {
                        Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }
                
                tmp.close();
                v.clear();
          }
            Date d=new Date();
            SimpleDateFormat f=new SimpleDateFormat("dd-MMMM-yyyy", Locale.FRANCE); 
	
            BufferedReader breader1 = null;
            breader1 = new BufferedReader(new FileReader(chemin1));
            Instances test = new Instances(breader1);
            test.setClassIndex(test.numAttributes() - 1);
            System.out.println("" + test.numInstances());
            for (int i = 0; i < test.numInstances(); i++) {
                double clsLabel = nb.classifyInstance(test.instance(i));
                
                requete.executeUpdate("INSERT INTO \"documentclasse\" (\"NOM\",\"CHEMIN\",\"CLASSE\",\"CLASSIFIER\") VALUES ('" + file[0].getName() + "','" + file[0].getAbsolutePath() + "','"+test.classAttribute().value((int) clsLabel)+"','Bayes')");

                    test.instance(i).setClassValue(clsLabel);
                   
                    VueProjet.jTextArea_ClasseBayes.setText(test.classAttribute().value((int) clsLabel));
             }
       
        } catch (SQLException ex) {
            Logger.getLogger(ClassifierBayes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }   
    
}    
    
    
    

