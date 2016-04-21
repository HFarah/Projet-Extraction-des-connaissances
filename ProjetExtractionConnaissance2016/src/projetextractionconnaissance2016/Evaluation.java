
package projetextractionconnaissance2016;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Instances;


public class Evaluation {
    
        float fmessure=0;
        float prisso=0;
        float rappel=0;
         
        public Evaluation(){}
        
    public void evaluationBayes(String chemin) throws FileNotFoundException, IOException, Exception{
        //Path pathFile = chemin.
       // System.out.println("---------"+pathFile);
        //String chemint = "./fichiersArff/hay-test.arff";

        BufferedReader breader = new BufferedReader(new FileReader(chemin));
        
        Instances trainingSet = new Instances(breader);
       // int trainSize = (int) Math.round(trainingSet.numInstances() * 0.66);
       // System.out.println("train size"+trainSize);
        
        
        //int testSize = trainingSet.numInstances() - trainSize;
       // System.out.println("train size"+testSize);
        
      //  Instances train = new Instances(trainingSet, 0, trainSize);
        
       // Instances test = new Instances(trainingSet, trainSize, testSize);
        
        //train.setClassIndex(train.numAttributes()-1);
       // test.setClassIndex(test.numAttributes()-1);
       
        trainingSet.setClassIndex(trainingSet.numAttributes()-1);
        
        breader.close();
        
        
        NaiveBayes nb=new NaiveBayes();
        
        nb.buildClassifier(trainingSet);
        
        weka.classifiers.Evaluation evl=new weka.classifiers.Evaluation(trainingSet);
           
        evl.crossValidateModel(nb, trainingSet, 10, new Random(1));
      
        fmessure= (float) evl.weightedFMeasure();
        prisso=(float) evl.weightedPrecision();
        rappel = (float) evl.weightedRecall();
        
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        
        dataset.addValue(prisso,"Precision" ,"Precision");
        dataset.addValue(rappel,"Recall" ,"Recall");
        dataset.addValue(fmessure,"FMeasure" ,"FMeasure");
      
        final JFreeChart barChart = ChartFactory.createBarChart3D("Evaluation du classifier ","Les Mots", "valeurs ", 
						dataset,PlotOrientation.VERTICAL, true, true, false);
	final ChartPanel cPanel = new ChartPanel(barChart);
        
        VueProjet.jScrollPane_Histogramme.setViewportView(cPanel);
                        
        
        VueProjet.jTextArea_MatriceConfusion.setText(evl.toMatrixString());
  
    }
    
     public void evaluationArbreDecision(String chemin) throws FileNotFoundException, IOException, Exception{
        
        BufferedReader breader =null;
        breader =new BufferedReader(new FileReader(chemin));
        Instances trainingSet = new Instances(breader);
        
        int trainSize = (int) Math.round(trainingSet.numInstances() * 0.66);
        
        int testSize = trainingSet.numInstances() - trainSize;
        
        Instances train = new Instances(trainingSet, 0, trainSize);
        
        Instances test = new Instances(trainingSet, trainSize, testSize);
        
        train.setClassIndex(train.numAttributes()-1);
        test.setClassIndex(train.numAttributes()-1);
        
        breader.close();
        
        J48 nb=new J48();
        nb.buildClassifier(train);
        
        weka.classifiers.Evaluation evl=new weka.classifiers.Evaluation(train);
        evl.evaluateModel(nb,test );
         
          
        fmessure= (float) evl.weightedFMeasure();
        prisso=(float) evl.weightedPrecision();
        rappel = (float) evl.weightedRecall();
        
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        
        dataset.addValue(prisso,"Precision" ,"Precision");
        dataset.addValue(rappel,"Recall" ,"Recall");
        dataset.addValue(fmessure,"FMeasure" ,"FMeasure");
      
        final JFreeChart barChart = ChartFactory.createBarChart3D("Evaluation du classifier ","Les Mots", "valeurs ", 
						dataset,PlotOrientation.VERTICAL, true, true, false);
	final ChartPanel cPanel = new ChartPanel(barChart);
        
        VueProjet.jScrollPane_Histogramme.setViewportView(cPanel);
                        
        
        VueProjet.jTextArea_MatriceConfusion.setText(evl.toMatrixString());
        
        
    }
    public void evaluationSVM(String chemin) throws FileNotFoundException, IOException, Exception{
        
        BufferedReader breader =new BufferedReader(new FileReader(chemin));
        
        Instances trainingSet = new Instances(breader);
        
        /*int trainSize = (int) Math.round(trainingSet.numInstances() * 0.66);
        
        int testSize = trainingSet.numInstances() - trainSize;
        
        Instances train = new Instances(trainingSet, 0, trainSize);
        
        Instances test = new Instances(trainingSet, trainSize, testSize);
        
        train.setClassIndex(train.numAttributes()-1);
        
        test.setClassIndex(train.numAttributes()-1);*/
        
        trainingSet.setClassIndex(trainingSet.numAttributes()-1);
        
        breader.close();
        
        SMO nb=new SMO();
        
        nb.buildClassifier(trainingSet);
        
        weka.classifiers.Evaluation evl=new weka.classifiers.Evaluation(trainingSet);
           
        evl.crossValidateModel(nb, trainingSet, 5, new Random(1));
        
        /*
        nb.buildClassifier(train);
        
        weka.classifiers.Evaluation evl=new weka.classifiers.Evaluation(train);
        
        evl.evaluateModel(nb,test );*/
         
          
        fmessure= (float) evl.weightedFMeasure();
        prisso=(float) evl.weightedPrecision();
        rappel = (float) evl.weightedRecall();
        
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        
        dataset.addValue(prisso,"Precision" ,"Precision");
        dataset.addValue(rappel,"Recall" ,"Recall");
        dataset.addValue(fmessure,"FMeasure" ,"FMeasure");
      
        final JFreeChart barChart = ChartFactory.createBarChart3D("Evaluation du classifier ","Les Mots", "valeurs ", 
						dataset,PlotOrientation.VERTICAL, true, true, false);
	final ChartPanel cPanel = new ChartPanel(barChart);
        
        VueProjet.jScrollPane_Histogramme.setViewportView(cPanel);
                        
        
        VueProjet.jTextArea_MatriceConfusion.setText(evl.toMatrixString());
        
        
    }
    public void evaluationKNN(String chemin) throws FileNotFoundException, IOException, Exception{
        
        BufferedReader breader = new BufferedReader(new FileReader(chemin));
        
        Instances trainingSet = new Instances(breader);
        
        /*int trainSize = (int) Math.round(trainingSet.numInstances() * 0.66);
        
        int testSize = trainingSet.numInstances() - trainSize;
        
        Instances train = new Instances(trainingSet, 0, trainSize);
        
        Instances test = new Instances(trainingSet, trainSize, testSize);
        
        train.setClassIndex(train.numAttributes()-1);
        
        test.setClassIndex(train.numAttributes()-1);
        
        breader.close();*/
        
        trainingSet.setClassIndex(trainingSet.numAttributes()-1);
        
        breader.close();
        
        IBk nb=new IBk();
        
        nb.setKNN(5);
        
        nb.buildClassifier(trainingSet);
        
        weka.classifiers.Evaluation evl=new weka.classifiers.Evaluation(trainingSet);
           
        evl.crossValidateModel(nb, trainingSet, 5, new Random(1));
          
        fmessure= (float) evl.weightedFMeasure();
        prisso=(float) evl.weightedPrecision();
        rappel = (float) evl.weightedRecall();
        
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        
        dataset.addValue(prisso,"Precision" ,"Precision");
        dataset.addValue(rappel,"Recall" ,"Recall");
        dataset.addValue(fmessure,"FMeasure" ,"FMeasure");
      
        final JFreeChart barChart = ChartFactory.createBarChart3D("Evaluation du classifier ","Les Mots", "valeurs ", 
						dataset,PlotOrientation.VERTICAL, true, true, false);
	final ChartPanel cPanel = new ChartPanel(barChart);
        
        VueProjet.jScrollPane_Histogramme.setViewportView(cPanel);
                        
        
        VueProjet.jTextArea_MatriceConfusion.setText(evl.toMatrixString());
        
        
    }
}
