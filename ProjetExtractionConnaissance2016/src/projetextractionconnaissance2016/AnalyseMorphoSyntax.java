
package projetextractionconnaissance2016;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import org.annolab.tt4j.ExecutableResolver;
import org.annolab.tt4j.PlatformDetector;
import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;

public class AnalyseMorphoSyntax {
    
      public  static Vector<String> lemmeMot(Vector<String> vectorMot) throws IOException, TreeTaggerException{
         
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

                    //st2 = new StringTokenizer(words, " ");
                    tt.setModel("/TreeTagger/model/english.par");
                    tt.setExecutableProvider(er);

                    String[] argum = {"-sgml", "-token", "-lemma"};
                    
                    tt.setArguments(argum);

                    tt.setHandler(new TokenHandler<String>() {
                        @Override
                        public void token(String o, String string, String string1) {
                         
                                // System.out.println(o + "\t" + string + "\t" + string1);
                                
                                if (string1.equals("<unknown>")) {
                                   vectorMot.remove(o);
                                }
                               
                         }
                    });         
                   tt.process(vectorMot);             
       
        return vectorMot;
    }
}
