package yodafy;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;

public class YodaMode {
	 protected StanfordCoreNLP pipeline;

	    public YodaMode() {
	        Properties props;
	        props = new Properties();
	        props.put("annotators", "tokenize, ssplit, pos, lemma");
	        this.pipeline = new StanfordCoreNLP(props);
	    }

	    public List<String> lemmatize(String documentText)
	    {
	        List<String> lemmas = new LinkedList<String>();
	        Annotation document = new Annotation(documentText);
	        this.pipeline.annotate(document);
	        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	        for(CoreMap sentence: sentences) {
	            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
	                lemmas.add(token.get(LemmaAnnotation.class));
	            }
	        }
	        return lemmas;
	    }

	}
