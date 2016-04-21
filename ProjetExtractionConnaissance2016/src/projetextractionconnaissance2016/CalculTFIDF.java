
package projetextractionconnaissance2016;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class CalculTFIDF {
     
     private static Connection con;
     TableModel TM;
     
     
    /**
     * 
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static void calculRepresentationVectorielleTfIdf() throws SQLException, ClassNotFoundException{
      
            con = ConnexionDB.getConnection();
            List <String> v = new ArrayList<>();
            Set <String> c= new HashSet<>();
            ResultSet rs;
            Statement requete = con.createStatement();
            Statement requete1 = con.createStatement();
            
            rs = requete.executeQuery("SELECT \"MOT_IN\" FROM \"indexmot\"");
            
            while(rs.next()){
                
                c.add(rs.getString("MOT_IN").trim()); 
            }
                       
            Iterator kk=c.iterator();     
          
            while(kk.hasNext()){
                
                v.add(kk.next().toString());

            }
            c.clear();
            
            int N=0;
           
            rs = requete.executeQuery("SELECT count(\"ID_DOC\") AS nbre FROM \"document\"");
           
            while (rs.next()){
               
                N =rs.getInt("nbre");
            }
            for(int i=0;i<v.size();i++){
             
                rs = requete.executeQuery("SELECT  count(\"ID_DOC\") AS nbdoc FROM \"indexmot\" WHERE \"MOT_IN\"='"+v.get(i)+"'");
                
                int DF=0;
                
                while (rs.next()){
                    
                    DF = rs.getInt("nbdoc");
                
                }
            rs = requete.executeQuery("SELECT \"TF\" ,\"ID_DOC\" FROM \"indexmot\" WHERE \"MOT_IN\"='"+v.get(i)+"'");
            
            int doc=0;
            int TF=0;
             
            while(rs.next())
              {
                TF = rs.getInt("TF");
                doc=rs.getInt("ID_DOC");
              
                float IDF=0;
                float TFIDF=0;
              
                IDF =   (float)((Math.log((float)N/DF))/(Math.log(10)));
                
                TFIDF =(float) (TF*IDF);
               
                TFIDF=(float)((int)(TFIDF*10000))/10000;
               
                requete1.executeUpdate("UPDATE indexmot SET \"TF_IDF\" = '"+TFIDF+"' WHERE \"MOT_IN\"='"+v.get(i)+"'AND \"ID_DOC\"='"+doc+"'");
            }
                            
            }
            v.clear();
        
    }
    /**
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
    public  void fichierArffSeuilTFIDFIndexCentral() throws ClassNotFoundException, SQLException, IOException{
               
          int n= Integer.parseInt(VueProjet.seuilVarTFIDF.getText());
          
          con = ConnexionDB.getConnection();
          
          Statement requete = con.createStatement();
          
          /*ArrayList<String> v = new ArrayList<>();
          
          ResultSet rs= requete.executeQuery("SELECT DISTINCT \"MOT_IN\" FROM \"indexmot\"");
          
          while (rs.next()) {
            v.add(rs.getString("MOT_IN"));
          }
          
          for(String mot : v )
          {
          
            System.out.println(mot);
            
            requete.executeUpdate("INSERT INTO \"indexmotdistinct\" (\"MOT_IN\",\"CLASSE\",\"TF\",\"ID_DOC\",\"TF_IDF\",\"TOKEN\") "
                                + "SELECT \"MOT_IN\", \"CLASSE\", \"TF\", \"ID_DOC\", \"TF_IDF\", \"TOKEN\" "
                                + "FROM \"indexmot\" WHERE \"MOT_IN\"='"+mot+"' ORDER BY \"TF_IDF\" DESC LIMIT 1");         

          }*/
          
          requete.executeUpdate("TRUNCATE TABLE temporairearff");  
          
          requete.executeUpdate("INSERT INTO temporairearff (\"MOT_IN\",\"CLASSE\",\"TF\",\"ID_DOC\",\"TF_IDF\") SELECT \"MOT_IN\", \"CLASSE\", \"TF\", \"ID_DOC\", \"TF_IDF\" FROM \"indexmotdistinct\" ORDER BY \"TF_IDF\" DESC LIMIT "+n);         

          this.affichageTFIDFIndexCentral();
           
          this.arffIndexCentral();
            
          VueProjet.BouttonFichierArff.setEnabled(true);
          
    }
    /**
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
    public  void fichierArffSeuilTFIDFIndexStopWords() throws ClassNotFoundException, SQLException, IOException{
               
        int n= Integer.parseInt(VueProjet.seuilVarTFIDF.getText());
          
        con = ConnexionDB.getConnection();
        Statement requete = con.createStatement();
          
        requete.executeUpdate( "TRUNCATE TABLE temporairearff" );  
          
        requete.executeUpdate("INSERT INTO temporairearff (\"MOT_IN\",\"CLASSE\",\"TF\",\"ID_DOC\",\"TF_IDF\") SELECT \"MOT_IN\", \"CLASSE\", \"TF\", \"ID_DOC\", \"TF_IDF\" FROM \"indexmotstopwords\" ORDER BY \"TF_IDF\" DESC LIMIT "+n);         
            
        this.affichageTFIDFIndexStopWords();
           
        this.arffIndexStopWords();
            
        VueProjet.BouttonFichierArff.setEnabled(true);
          
    }
    
    /**
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
    public  void fichierArffSeuilTFIDFIndexMorphoSyntax() throws ClassNotFoundException, SQLException, IOException{
               
        int n= Integer.parseInt(VueProjet.seuilVarTFIDF.getText());
          
        con = ConnexionDB.getConnection();
        Statement requete = con.createStatement();
          
        requete.executeUpdate("TRUNCATE TABLE temporairearff" );  
          
        requete.executeUpdate("INSERT INTO temporairearff (\"MOT_IN\",\"CLASSE\",\"TF\",\"ID_DOC\",\"TF_IDF\") SELECT \"MOT_IN\", \"CLASSE\", \"TF\", \"ID_DOC\", \"TF_IDF\" FROM \"indexmotmorphosyntax\" ORDER BY \"TF_IDF\" DESC LIMIT "+n);         
            
        this.affichageTFIDFIndexMorphoSyntax();
           
        this.arffIndexMorphoSyntax();
            
        VueProjet.BouttonFichierArff.setEnabled(true);
          
    }
    /**
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
    public  void fichierArffSeuilTFIDFIndexCombine() throws ClassNotFoundException, SQLException, IOException{
               
        int n= Integer.parseInt(VueProjet.seuilVarTFIDF.getText());
          
        con = ConnexionDB.getConnection();
        
        Statement requete = con.createStatement();
          
        requete.executeUpdate( "TRUNCATE TABLE temporairearff" );  
          
        requete.executeUpdate("INSERT INTO temporairearff (\"MOT_IN\",\"CLASSE\",\"TF\",\"ID_DOC\",\"TF_IDF\") SELECT \"MOT_IN\", \"CLASSE\", \"TF\", \"ID_DOC\", \"TF_IDF\" FROM \"indexmotcombine\" ORDER BY \"TF_IDF\" DESC LIMIT "+n);         
            
        this.affichageTFIDFIndexCombine();
           
        this.arffIndexCombine();
            
        VueProjet.BouttonFichierArff.setEnabled(true);
          
    }
    
     public  void fichierArffSeuilTFIDFIndexStopWordsSeuilMot() throws ClassNotFoundException, SQLException, IOException{
               
        int n= Integer.parseInt(VueProjet.seuilVarTFIDF.getText());
          
        con = ConnexionDB.getConnection();
        
        Statement requete = con.createStatement();
          
        requete.executeUpdate( "TRUNCATE TABLE temporairearff" );  
          
        requete.executeUpdate("INSERT INTO temporairearff (\"MOT_IN\",\"CLASSE\",\"TF\",\"ID_DOC\",\"TF_IDF\") SELECT \"MOT_IN\", \"CLASSE\", \"TF\", \"ID_DOC\", \"TF_IDF\" FROM \"indexmotstopwordsseuil\" ORDER BY \"TF_IDF\" DESC LIMIT "+n);         
            
        this.affichageTFIDFIndexStopWordsSeuilMot();
           
        this.arffIndexStopWordsSeuilMot();
            
        VueProjet.BouttonFichierArff.setEnabled(true);
          
    }
    
    public  void fichierArffSeuilTFIDFIndexSemantique() throws ClassNotFoundException, SQLException, IOException{
               
        int n= Integer.parseInt(VueProjet.seuilVarTFIDF.getText());
          
        con = ConnexionDB.getConnection();
        
        Statement requete = con.createStatement();
          
        requete.executeUpdate( "TRUNCATE TABLE temporairearff" );  
          
        requete.executeUpdate("INSERT INTO temporairearff (\"MOT_IN\",\"CLASSE\",\"TF\",\"ID_DOC\",\"TF_IDF\") SELECT \"MOT_IN\", \"CLASSE\", \"TF\", \"ID_DOC\", \"TF_IDF\" FROM \"indexmotsemantique\" ORDER BY \"TF_IDF\" DESC LIMIT "+n);         

        this.affichageTFIDFIndexSemantique();
           
        this.arffIndexSemantique();
            
        VueProjet.BouttonFichierArff.setEnabled(true);
          
    }
    
    /**
     * @throws java.lang.ClassNotFoundException*
     * @throws java.sql.SQLException
     */
    
    public void affichageTFIDFIndexCentral() throws ClassNotFoundException, SQLException {
        
        con = ConnexionDB.getConnection();
        
        ResultSet rs;
       
        Vector<Integer> v = new Vector<Integer>();
        
        Statement requete = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
       
        //rs = requete.executeQuery("SELECT count(\"ID_DOC\") AS nbre, \"ID_DOC\" AS ID FROM \"temporairearff\" GROUP BY \"ID_DOC\"");
        rs = requete.executeQuery("SELECT DISTINCT(\"ID_DOC\") AS ID FROM \"document\"");

        while (rs.next()) {
            v.add(rs.getInt("ID"));
        }

        rs.last();
        int nbdoc = rs.getRow();
        rs.close();
        
        rs = requete.executeQuery("SELECT count(distinct(\"MOT_IN\")) as nbre FROM \"temporairearff\"");
        
            int nbmot = 0;
            
            while (rs.next()) {
                nbmot = rs.getInt("nbre");
            }

            String[] head = new String[(nbmot + 2)];
            head[0] = "id_doc/mot";
            head[(nbmot + 1)] = "class";
            
            int i = 1;
            
            rs = requete.executeQuery("SELECT distinct(\"MOT_IN\") as mot FROM \"temporairearff\" ORDER BY mot ");

            while (rs.next()) {
                head[i] = rs.getString("mot");
                i++;
            }
            rs.close();
            
            Object vals[][] = new Object[nbdoc][(nbmot + 2)];
            
            for (int in = 0; in < v.size(); in++) {

            vals[in][0] = v.get(in);

            //rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"temporairearff\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");
            rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"indexmot\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");

            String cls = "";
            
            while (rs.next()) {
               
                cls = rs.getString("CLASSE");
               
                for (int num = 1; num <= nbmot; num++) {

                    if (head[num].equals(rs.getString("MOT_IN"))) {
                        vals[in][num] = rs.getFloat("TF_IDF");
                    }
                    if (vals[in][num] == null) {
                        vals[in][num] = 0;
                    }

                }
            }
                vals[in][nbmot + 1] = cls;
            }

            TM = new DefaultTableModel(vals, head);
            VueProjet.TableVecteurTFIDF.setModel(TM);
            VueProjet.jScrollPaneArffTFIDF.setViewportView(VueProjet.TableVecteurTFIDF);

    }
    
    public void affichageTFIDFIndexStopWords() throws ClassNotFoundException, SQLException {
        
        con = ConnexionDB.getConnection();
        ResultSet rs;
       
        Vector<Integer> v = new Vector<Integer>();
        Statement requete = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
       
        //rs = requete.executeQuery("SELECT count(\"ID_DOC\") AS nbre ,\"ID_DOC\" AS ID FROM \"temporairearff\" GROUP BY \"ID_DOC\"");
        
        rs = requete.executeQuery("SELECT DISTINCT(\"ID_DOC\") AS ID FROM \"document\"");

        while (rs.next()) {
            
            v.add(rs.getInt("ID"));
        }

        rs.last();
        int nbdoc = rs.getRow();
        rs.close();
        
        rs = requete.executeQuery("SELECT count(distinct(\"MOT_IN\")) as nbre FROM \"temporairearff\"");
        
            int nbmot = 0;
            
            while (rs.next()) {
                nbmot = rs.getInt("nbre");
            }

            String[] head = new String[(nbmot + 2)];
            head[0] = "id_doc/mot";
            head[(nbmot + 1)] = "class";
            
            int i = 1;
            
            rs = requete.executeQuery("SELECT distinct(\"MOT_IN\") as mot FROM \"temporairearff\" ORDER BY mot ");

            while (rs.next()) {
                head[i] = rs.getString("mot");
                i++;
            }
            rs.close();
            
            Object vals[][] = new Object[nbdoc][(nbmot + 2)];
            
            for (int in = 0; in < v.size(); in++) {

            vals[in][0] = v.get(in);

            //rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"temporairearff\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");
            rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"indexmot\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");

            String cls = "";
            
            while (rs.next()) {
               
                cls = rs.getString("CLASSE");
               
                for (int num = 1; num <= nbmot; num++) {

                    if (head[num].equals(rs.getString("MOT_IN"))) {
                        vals[in][num] = rs.getFloat("TF_IDF");
                    }
                    if (vals[in][num] == null) {
                        vals[in][num] = 0;
                    }

                }
            }
                vals[in][nbmot + 1] = cls;
            }

            TM = new DefaultTableModel(vals, head);
            VueProjet.TableVecteurTFIDF.setModel(TM);
            VueProjet.jScrollPaneArffTFIDF.setViewportView(VueProjet.TableVecteurTFIDF);

    }
    
     public void affichageTFIDFIndexStopWordsSeuilMot() throws ClassNotFoundException, SQLException {
        
        con = ConnexionDB.getConnection();
        ResultSet rs;
       
        Vector<Integer> v = new Vector<Integer>();
        
        Statement requete = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
       
        //rs = requete.executeQuery("SELECT count(\"ID_DOC\") AS nbre ,\"ID_DOC\" AS ID FROM \"temporairearff\" GROUP BY \"ID_DOC\"");

        rs = requete.executeQuery("SELECT DISTINCT(\"ID_DOC\") AS ID FROM \"document\"");

        while (rs.next()) {
            
            v.add(rs.getInt("ID"));
        }

        rs.last();
        int nbdoc = rs.getRow();
        rs.close();
        
        rs = requete.executeQuery("SELECT count(distinct(\"MOT_IN\")) as nbre FROM \"temporairearff\"");
        
            int nbmot = 0;
            
            while (rs.next()) {
                nbmot = rs.getInt("nbre");
            }

            String[] head = new String[(nbmot + 2)];
            head[0] = "id_doc/mot";
            head[(nbmot + 1)] = "class";
            
            int i = 1;
            
            rs = requete.executeQuery("SELECT distinct(\"MOT_IN\") as mot FROM \"temporairearff\" ORDER BY mot ");

            while (rs.next()) {
                head[i] = rs.getString("mot");
                i++;
            }
            rs.close();
            
            Object vals[][] = new Object[nbdoc][(nbmot + 2)];
            
            for (int in = 0; in < v.size(); in++) {

            vals[in][0] = v.get(in);

            //rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"temporairearff\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");
            
            rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"indexmot\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");

            String cls = "";
            
            while (rs.next()) {
               
                cls = rs.getString("CLASSE");
               
                for (int num = 1; num <= nbmot; num++) {

                    if (head[num].equals(rs.getString("MOT_IN"))) {
                        vals[in][num] = rs.getFloat("TF_IDF");
                    }
                    if (vals[in][num] == null) {
                        vals[in][num] = 0;
                    }

                }
            }
                vals[in][nbmot + 1] = cls;
            }

            TM = new DefaultTableModel(vals, head);
            
            VueProjet.TableVecteurTFIDF.setModel(TM);
            
            VueProjet.jScrollPaneArffTFIDF.setViewportView(VueProjet.TableVecteurTFIDF);

    }
     
     /*******************   PROCEDURE QUE JE CHANGE EN BAS ******************************/
     
     /*public void affichageTFIDFIndexSemantique() throws ClassNotFoundException, SQLException {
        
        con = ConnexionDB.getConnection();
        
        ResultSet rs;
       
        Vector<Integer> v = new Vector<Integer>();
        
        Statement requete = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
       
        //rs = requete.executeQuery("SELECT count(\"ID_DOC\") AS nbre ,\"ID_DOC\" AS ID FROM \"temporairearff\" GROUP BY \"ID_DOC\"");
        
        rs = requete.executeQuery("SELECT count(\"ID_DOC\") AS nbre ,\"ID_DOC\" AS ID FROM \"document\" GROUP BY \"ID_DOC\"");

        int nbdoc = 0;

        while (rs.next()) {
            
            v.add(rs.getInt("ID"));
        }

        rs.last();
        nbdoc = rs.getRow();
        rs.close();
        
        rs = requete.executeQuery("SELECT count(distinct(\"MOT_IN\")) as nbre FROM \"temporairearff\"");
        
            int nbmot = 0;
            
            while (rs.next()) {
                nbmot = rs.getInt("nbre");
            }

            String[] head = new String[(nbmot + 2)];
            head[0] = "id_doc/mot";
            head[(nbmot + 1)] = "class";
            
            int i = 1;
            
            rs = requete.executeQuery("SELECT distinct(\"MOT_IN\") as mot FROM \"temporairearff\" ORDER BY mot ");

            while (rs.next()) {
                head[i] = rs.getString("mot");
                i++;
            }
            rs.close();
            
            Object vals[][] = new Object[nbdoc][(nbmot + 2)];
            
            for (int in = 0; in < v.size(); in++) {

            vals[in][0] = v.get(in);

            //rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"temporairearff\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");
            
            rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"indexmot\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");

            String cls = "";
            
            while (rs.next()) {
               
                cls = rs.getString("CLASSE");
               
                for (int num = 1; num <= nbmot; num++) {

                    if (head[num].equals(rs.getString("MOT_IN"))) {
                        vals[in][num] = rs.getFloat("TF_IDF");
                    }
                    if (vals[in][num] == null) {
                        vals[in][num] = 0;
                    }

                }
            }
                vals[in][nbmot + 1] = cls;
            }

            TM = new DefaultTableModel(vals, head);
            VueProjet.TableVecteurTFIDF.setModel(TM);
            VueProjet.jScrollPaneArffTFIDF.setViewportView(VueProjet.TableVecteurTFIDF);

    }*/
    
    public void affichageTFIDFIndexSemantique() throws ClassNotFoundException, SQLException {
        
        con = ConnexionDB.getConnection();
        
        ResultSet rs;
       
        Vector<Integer> v = new Vector<>();
        
        Statement requete = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
       
        //rs = requete.executeQuery("SELECT count(\"ID_DOC\") AS nbre ,\"ID_DOC\" AS ID FROM \"temporairearff\" GROUP BY \"ID_DOC\"");
        
        rs = requete.executeQuery("SELECT DISTINCT(\"ID_DOC\") AS ID FROM \"document\"");

        while (rs.next()) {
            
            v.add(rs.getInt("ID"));
        }

        rs.last();
        int nbdoc = rs.getRow();
        rs.close();
        
        rs = requete.executeQuery("SELECT count(distinct(\"MOT_IN\")) as nbre FROM \"temporairearff\"");
        
            int nbmot = 0;
            
            while (rs.next()) {
                nbmot = rs.getInt("nbre");
            }

            String[] head = new String[(nbmot + 2)];
            head[0] = "id_doc/mot";
            head[(nbmot + 1)] = "class";
            
            int i = 1;
            
            rs = requete.executeQuery("SELECT distinct(\"MOT_IN\") as mot FROM \"temporairearff\" ORDER BY mot ");

            while (rs.next()) {
                head[i] = rs.getString("mot");
                i++;
            }
            rs.close();
            
            Object vals[][] = new Object[nbdoc][(nbmot + 2)];
            
            for (int in = 0; in < v.size(); in++) {

            vals[in][0] = v.get(in);

            //rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"temporairearff\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");
            
            rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"indexmot\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");

            String cls = "";
            
            while (rs.next()) {
               
                cls = rs.getString("CLASSE");
               
                for (int num = 1; num <= nbmot; num++) {

                    if (head[num].equals(rs.getString("MOT_IN"))) {
                        vals[in][num] = rs.getFloat("TF_IDF");
                    }
                    if (vals[in][num] == null) {
                        vals[in][num] = 0;
                    }

                }
            }
                vals[in][nbmot + 1] = cls;
            }

            TM = new DefaultTableModel(vals, head);
            VueProjet.TableVecteurTFIDF.setModel(TM);
            VueProjet.jScrollPaneArffTFIDF.setViewportView(VueProjet.TableVecteurTFIDF);

    }
     
       /**
     * Index Morpho synataxique
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
     
    public void affichageTFIDFIndexMorphoSyntax() throws ClassNotFoundException, SQLException {
        System.err.println("------------Debut de l'affichage -------------------------");
        con = ConnexionDB.getConnection();
        ResultSet rs;
       
        Vector<Integer> v = new Vector<Integer>();
        Statement requete = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
       
        //rs = requete.executeQuery("SELECT count(\"ID_DOC\") AS nbre, \"ID_DOC\" AS ID FROM \"temporairearff\" GROUP BY \"ID_DOC\"");
        rs = requete.executeQuery("SELECT DISTINCT(\"ID_DOC\") AS ID FROM \"document\"");

        while (rs.next()) {
            v.add(rs.getInt("ID"));
        }

        rs.last();
        int nbdoc = rs.getRow();
        rs.close();
        
        rs = requete.executeQuery("SELECT count(distinct(\"MOT_IN\")) as nbre FROM \"temporairearff\"");
        
            int nbmot = 0;
            
            while (rs.next()) {
                nbmot = rs.getInt("nbre");
            }

            String[] head = new String[(nbmot + 2)];
            head[0] = "id_doc/mot";
            head[(nbmot + 1)] = "class";
            
            int i = 1;
            
            rs = requete.executeQuery("SELECT distinct(\"MOT_IN\") as mot FROM \"temporairearff\" ORDER BY mot ");

            while (rs.next()) {
                head[i] = rs.getString("mot");
                i++;
            }
          
            Object vals[][] = new Object[nbdoc][(nbmot + 2)];
            
            for (int in = 0; in < v.size(); in++) {

            vals[in][0] = v.get(in);

            //rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"temporairearff\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");
            rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"indexmot\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");

            String cls = "";
            
            while (rs.next()) {
               
                cls = rs.getString("CLASSE");
               
                for (int num = 1; num <= nbmot; num++) {

                    if (head[num].equals(rs.getString("MOT_IN"))) {
                        vals[in][num] = rs.getFloat("TF_IDF");
                    }
                    if (vals[in][num] == null) {
                        vals[in][num] = 0;
                    }

                }
            }
                vals[in][nbmot + 1] = cls;
            }
            System.out.println("-------vals-----------\t"+vals);
            System.out.println("-------head-----------\t"+head);

            TM = new DefaultTableModel(vals, head);
            VueProjet.TableVecteurTFIDF.setModel(TM);
            VueProjet.jScrollPaneArffTFIDF.setViewportView(VueProjet.TableVecteurTFIDF);
            
            
            
            
            
            System.err.println("------------Fin de l'affichage -------------------------");
    }
    public void affichageTFIDFIndexCombine() throws ClassNotFoundException, SQLException {
        
        con = ConnexionDB.getConnection();
        ResultSet rs;
       
        Vector<Integer> v = new Vector<Integer>();
        Statement requete = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
       
        //rs = requete.executeQuery("SELECT count(\"ID_DOC\") AS nbre, \"ID_DOC\" AS ID FROM \"temporairearff\" GROUP BY \"ID_DOC\"");
        rs = requete.executeQuery("SELECT DISTINCT(\"ID_DOC\") AS ID FROM \"document\"");

        while (rs.next()) {
            v.add(rs.getInt("ID"));
        }

        rs.last();
        int nbdoc = rs.getRow();
        rs.close();
        
        rs = requete.executeQuery("SELECT count(distinct(\"MOT_IN\")) as nbre FROM \"temporairearff\"");
        
            int nbmot = 0;
            
            while (rs.next()) {
                nbmot = rs.getInt("nbre");
            }

            String[] head = new String[(nbmot + 2)];
            head[0] = "id_doc/mot";
            head[(nbmot + 1)] = "class";
            
            int i = 1;
            
            rs = requete.executeQuery("SELECT distinct(\"MOT_IN\") as mot FROM \"temporairearff\" ORDER BY mot");

            while (rs.next()) {
                head[i] = rs.getString("mot");
                i++;
            }
            rs.close();
            
            Object vals[][] = new Object[nbdoc][(nbmot + 2)];
            
            for (int in = 0; in < v.size(); in++) {

            vals[in][0] = v.get(in);

            //rs = requete.executeQuery("SELECT \"MOT_IN\",\"TF_IDF\",\"CLASSE\" FROM \"temporairearff\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");
            rs = requete.executeQuery("SELECT \"MOT_IN\", \"TF_IDF\", \"CLASSE\" FROM \"indexmot\" where \"ID_DOC\"='" + v.get(in) + "' ORDER BY \"MOT_IN\"");

            String cls = "";
            
            while (rs.next()) {
               
                cls = rs.getString("CLASSE");
               
                for (int num = 1; num <= nbmot; num++) {

                    if (head[num].equals(rs.getString("MOT_IN"))) {
                        vals[in][num] = rs.getFloat("TF_IDF");
                    }
                    if (vals[in][num] == null) {
                        vals[in][num] = 0;
                    }

                }
            }
                vals[in][nbmot + 1] = cls;
            }

            TM = new DefaultTableModel(vals, head);
            VueProjet.TableVecteurTFIDF.setModel(TM);
            VueProjet.jScrollPaneArffTFIDF.setViewportView(VueProjet.TableVecteurTFIDF);

    }
    
    public void arffIndexCentral() throws IOException {
        int nbcol;
        int nbli;
        int col;
        int li;
        
        TM = VueProjet.TableVecteurTFIDF.getModel();
        nbcol = TM.getColumnCount();
        nbli = TM.getRowCount();
     
        
        Set<String> c = new HashSet<>();
        List<String> v = new ArrayList<>();
        li = -1;

        FileWriter fichierOut = new FileWriter("./fichiersArff/arffIndexCentral.arff");

        BufferedWriter tmp = new BufferedWriter(fichierOut);
        PrintWriter out = new PrintWriter(tmp);

        if (li == -1) {
            try {
                tmp.write("@relation indexation");
                tmp.newLine();
                tmp.newLine();
                for (col = 1; col < nbcol; col++) {
                    if (TM.getColumnName(col).equals("class")) {
                  
                        for (int lii = 0; lii < nbli; lii++) {
                            c.add(String.valueOf(TM.getValueAt(lii, col)));
                        }
                        Iterator kk = c.iterator();
                        while (kk.hasNext()) 
                        {
                            v.add(kk.next().toString());

                        }
                        c.clear();
                        String Ligclasse = "";
                        for (int i = 0; i < v.size(); i++) {
                       
                            if (i < v.size() - 1) {
                                Ligclasse += v.get(i) + ",";
                            } else {
                                Ligclasse += v.get(i);
                            }

                        }

                        
                        tmp.write("@attribute class{" + Ligclasse + "}");
                        System.out.println(Ligclasse);

                        tmp.newLine();
                        tmp.newLine();
                        v.clear();
                    } else {

                        tmp.write("@attribute " + TM.getColumnName(col) + " real");
                        tmp.newLine();
                    }
                }
                tmp.newLine();
                tmp.newLine();
                tmp.write("@data");
                tmp.newLine();
            } catch (IOException ex) {
                Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        li = 1;
        int ling = 1;
        if (li == 1) {

            for (ling = 0; ling < nbli; ling++) {

                String ligne = "";
                for (col = 1; col < nbcol; col++) {
             
                    if (col < nbcol - 1) {

                        ligne += String.valueOf(TM.getValueAt(ling, col)) + ",";

                    } else {
                       
                        ligne += String.valueOf(TM.getValueAt(ling, col));
                    }
                }
                try {
                
                    tmp.write(ligne);
                    tmp.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    
        if (ling == nbli) {
            try {
                tmp.close();
            } catch (IOException ex) {
                Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }  
    public void arffIndexStopWords() throws IOException {
        int nbcol;
        int nbli;
        int col;
        int li;
        
        TM = VueProjet.TableVecteurTFIDF.getModel();
        nbcol = TM.getColumnCount();
        nbli = TM.getRowCount();
     
        
        Set<String> c = new HashSet<>();
        List<String> v = new ArrayList<>();
        li = -1;

        FileWriter fichierOut = new FileWriter("./fichiersArff/arffIndexStopWords.arff");

        BufferedWriter tmp = new BufferedWriter(fichierOut);
        PrintWriter out = new PrintWriter(tmp);

        if (li == -1) {
            try {
                tmp.write("@relation indexation");
                tmp.newLine();
                tmp.newLine();
                for (col = 1; col < nbcol; col++) {
                    if (TM.getColumnName(col).equals("class")) {
                  
                        for (int lii = 0; lii < nbli; lii++) {
                            c.add(String.valueOf(TM.getValueAt(lii, col)));
                        }
                        Iterator kk = c.iterator();
                        while (kk.hasNext()) 
                        {
                            v.add(kk.next().toString());

                        }
                        c.clear();
                        String Ligclasse = "";
                        for (int i = 0; i < v.size(); i++) {
                       
                            if (i < v.size() - 1) {
                                Ligclasse += v.get(i) + ",";
                            } else {
                                Ligclasse += v.get(i);
                            }

                        }

                        
                        tmp.write("@attribute class{" + Ligclasse + "}");
                        System.out.println(Ligclasse);

                        tmp.newLine();
                        tmp.newLine();
                        v.clear();
                    } else {

                        tmp.write("@attribute " + TM.getColumnName(col) + "  real");
                        tmp.newLine();
                    }
                }
                tmp.newLine();
                tmp.newLine();
                tmp.write("@data");
                tmp.newLine();
            } catch (IOException ex) {
                Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        li = 1;
        int ling = 1;
        if (li == 1) {

            for (ling = 0; ling < nbli; ling++) {

                String ligne = "";
                for (col = 1; col < nbcol; col++) {
             
                    if (col < nbcol - 1) {

                        ligne += String.valueOf(TM.getValueAt(ling, col)) + ",";

                    } else {
                       
                        ligne += String.valueOf(TM.getValueAt(ling, col));
                    }
                }
                try {
                
                    tmp.write(ligne);
                    tmp.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    
        if (ling == nbli) {
            try {
                tmp.close();
            } catch (IOException ex) {
                Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }    
    
    public void arffIndexCombine() throws IOException {
        int nbcol;
        int nbli;
        int col;
        int li;
        
        TM = VueProjet.TableVecteurTFIDF.getModel();
        nbcol = TM.getColumnCount();
        nbli = TM.getRowCount();
     
        
        Set<String> c = new HashSet<>();
        List<String> v = new ArrayList<>();
        li = -1;

        FileWriter fichierOut = new FileWriter("./fichiersArff/arffIndexCombine.arff");

        BufferedWriter tmp = new BufferedWriter(fichierOut);
        PrintWriter out = new PrintWriter(tmp);

        if (li == -1) {
            try {
                tmp.write("@relation indexation");
                tmp.newLine();
                tmp.newLine();
                for (col = 1; col < nbcol; col++) {
                    if (TM.getColumnName(col).equals("class")) {
                  
                        for (int lii = 0; lii < nbli; lii++) {
                            c.add(String.valueOf(TM.getValueAt(lii, col)));
                        }
                        Iterator kk = c.iterator();
                        while (kk.hasNext()) 
                        {
                            v.add(kk.next().toString());

                        }
                        c.clear();
                        String Ligclasse = "";
                        for (int i = 0; i < v.size(); i++) {
                       
                            if (i < v.size() - 1) {
                                Ligclasse += v.get(i) + ",";
                            } else {
                                Ligclasse += v.get(i);
                            }

                        }

                        
                        tmp.write("@attribute class{" + Ligclasse + "}");
                        System.out.println(Ligclasse);

                        tmp.newLine();
                        tmp.newLine();
                        v.clear();
                    } else {

                        tmp.write("@attribute " + TM.getColumnName(col) + "  real");
                        tmp.newLine();
                    }
                }
                tmp.newLine();
                tmp.newLine();
                tmp.write("@data");
                tmp.newLine();
            } catch (IOException ex) {
                Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        li = 1;
        int ling = 1;
        if (li == 1) {

            for (ling = 0; ling < nbli; ling++) {

                String ligne = "";
                for (col = 1; col < nbcol; col++) {
             
                    if (col < nbcol - 1) {

                        ligne += String.valueOf(TM.getValueAt(ling, col)) + ",";

                    } else {
                       
                        ligne += String.valueOf(TM.getValueAt(ling, col));
                    }
                }
                try {
                
                    tmp.write(ligne);
                    tmp.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    
        if (ling == nbli) {
            try {
                tmp.close();
            } catch (IOException ex) {
                Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }   
    
    public void arffIndexSemantique() throws IOException {
        int nbcol;
        int nbli;
        int col;
        int li;
        
        TM = VueProjet.TableVecteurTFIDF.getModel();
        nbcol = TM.getColumnCount();
        nbli = TM.getRowCount();
     
        
        Set<String> c = new HashSet<>();
        List<String> v = new ArrayList<>();
        li = -1;

        FileWriter fichierOut = new FileWriter("./fichiersArff/arffIndexSemantique.arff");

        BufferedWriter tmp = new BufferedWriter(fichierOut);
        PrintWriter out = new PrintWriter(tmp);

        if (li == -1) {
            try {
                tmp.write("@relation indexation");
                tmp.newLine();
                tmp.newLine();
                for (col = 1; col < nbcol; col++) {
                    if (TM.getColumnName(col).equals("class")) {
                  
                        for (int lii = 0; lii < nbli; lii++) {
                            c.add(String.valueOf(TM.getValueAt(lii, col)));
                        }
                        Iterator kk = c.iterator();
                        while (kk.hasNext()) 
                        {
                            v.add(kk.next().toString());

                        }
                        c.clear();
                        String Ligclasse = "";
                        for (int i = 0; i < v.size(); i++) {
                       
                            if (i < v.size() - 1) {
                                Ligclasse += v.get(i) + ",";
                            } else {
                                Ligclasse += v.get(i);
                            }

                        }

                        
                        tmp.write("@attribute class{" + Ligclasse + "}");
                        System.out.println(Ligclasse);

                        tmp.newLine();
                        tmp.newLine();
                        v.clear();
                    } else {

                        tmp.write("@attribute " + TM.getColumnName(col) + " real");
                        tmp.newLine();
                    }
                }
                tmp.newLine();
                tmp.newLine();
                tmp.write("@data");
                tmp.newLine();
            } catch (IOException ex) {
                Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        li = 1;
        int ling = 1;
        if (li == 1) {

            for (ling = 0; ling < nbli; ling++) {

                String ligne = "";
                for (col = 1; col < nbcol; col++) {
             
                    if (col < nbcol - 1) {

                        ligne += String.valueOf(TM.getValueAt(ling, col)) + ",";

                    } else {
                       
                        ligne += String.valueOf(TM.getValueAt(ling, col));
                    }
                }
                try {
                
                    tmp.write(ligne);
                    tmp.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    
        if (ling == nbli) {
            try {
                tmp.close();
            } catch (IOException ex) {
                Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }    
    
    public void arffIndexStopWordsSeuilMot() throws IOException {
        
        int nbcol;
        int nbli;
        int col;
        int li;
        
        TM = VueProjet.TableVecteurTFIDF.getModel();
        nbcol = TM.getColumnCount();
        nbli = TM.getRowCount();
     
        
        Set<String> c = new HashSet<>();
        List<String> v = new ArrayList<>();
        li = -1;

        FileWriter fichierOut = new FileWriter("./fichiersArff/arffIndexStopWordsSeuilMot.arff");

        BufferedWriter tmp = new BufferedWriter(fichierOut);
        PrintWriter out = new PrintWriter(tmp);

        if (li == -1) {
            try {
                tmp.write("@relation indexation");
                tmp.newLine();
                tmp.newLine();
                for (col = 1; col < nbcol; col++) {
                    if (TM.getColumnName(col).equals("class")) {
                  
                        for (int lii = 0; lii < nbli; lii++) {
                            c.add(String.valueOf(TM.getValueAt(lii, col)));
                        }
                        Iterator kk = c.iterator();
                        while (kk.hasNext()) 
                        {
                            v.add(kk.next().toString());

                        }
                        c.clear();
                        String Ligclasse = "";
                        for (int i = 0; i < v.size(); i++) {
                       
                            if (i < v.size() - 1) {
                                Ligclasse += v.get(i) + ",";
                            } else {
                                Ligclasse += v.get(i);
                            }

                        }

                        
                        tmp.write("@attribute class{" + Ligclasse + "}");
                        System.out.println(Ligclasse);

                        tmp.newLine();
                        tmp.newLine();
                        v.clear();
                    } else {

                        tmp.write("@attribute " + TM.getColumnName(col) + " real");
                        tmp.newLine();
                    }
                }
                tmp.newLine();
                tmp.newLine();
                tmp.write("@data");
                tmp.newLine();
            } catch (IOException ex) {
                Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        li = 1;
        int ling = 1;
        if (li == 1) {

            for (ling = 0; ling < nbli; ling++) {

                String ligne = "";
                for (col = 1; col < nbcol; col++) {
             
                    if (col < nbcol - 1) {

                        ligne += String.valueOf(TM.getValueAt(ling, col)) + ",";

                    } else {
                       
                        ligne += String.valueOf(TM.getValueAt(ling, col));
                    }
                }
                try {
                
                    tmp.write(ligne);
                    tmp.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    
        if (ling == nbli) {
            try {
                tmp.close();
            } catch (IOException ex) {
                Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }    
    
    public void arffIndexMorphoSyntax() throws IOException {
      System.err.println("------------debu de ARFF -------------------------");
        int nbcol;
        int nbli;
        int col;
        int li;
        
        TM = VueProjet.TableVecteurTFIDF.getModel();
        nbcol = TM.getColumnCount();
        nbli = TM.getRowCount();
     
        
        Set<String> c = new HashSet<>();
        List<String> v = new ArrayList<>();
        li = -1;

        FileWriter fichierOut = new FileWriter("./fichiersArff/arffIndexMorphoSyntax.arff");

        BufferedWriter tmp = new BufferedWriter(fichierOut);
        PrintWriter out = new PrintWriter(tmp);

        if (li == -1) {
            try {
                tmp.write("@relation indexation");
                tmp.newLine();
                tmp.newLine();
                for (col = 1; col < nbcol; col++) {
                    if (TM.getColumnName(col).equals("class")) {
                  
                        for (int lii = 0; lii < nbli; lii++) {
                            c.add(String.valueOf(TM.getValueAt(lii, col)));
                        }
                        Iterator kk = c.iterator();
                        while (kk.hasNext()) 
                        {
                            v.add(kk.next().toString());

                        }
                        c.clear();
                        String Ligclasse = "";
                        for (int i = 0; i < v.size(); i++) {
                       
                            if (i < v.size() - 1) {
                                Ligclasse += v.get(i) + ",";
                            } else {
                                Ligclasse += v.get(i);
                            }

                        }

                        
                        tmp.write("@attribute class{" + Ligclasse + "}");
                        System.out.println(Ligclasse);

                        tmp.newLine();
                        tmp.newLine();
                        v.clear();
                    } else {

                        tmp.write("@attribute " + TM.getColumnName(col) + " real");
                        tmp.newLine();
                    }
                }
                tmp.newLine();
                tmp.newLine();
                tmp.write("@data");
                tmp.newLine();
            } catch (IOException ex) {
                Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        li = 1;
        int ling = 1;
        if (li == 1) {

            for (ling = 0; ling < nbli; ling++) {

                String ligne = "";
                for (col = 1; col < nbcol; col++) {
             
                    if (col < nbcol - 1) {

                        ligne += String.valueOf(TM.getValueAt(ling, col)) + ",";

                    } else {
                       
                        ligne += String.valueOf(TM.getValueAt(ling, col));
                    }
                }
                try {
                
                    tmp.write(ligne);
                    tmp.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    
        if (ling == nbli) {
            try {
                tmp.close();
            } catch (IOException ex) {
                Logger.getLogger(CalculTFIDF.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    System.err.println("------------fin de ARFF -------------------------");
    }    
    
}
