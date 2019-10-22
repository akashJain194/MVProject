import java.lang.reflect.Array;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) {

    }

    public boolean relevant(WordBucket questionWords, WordBucket answerWords) {
        int count = 0;
        ArrayList<WordFreq> qWords = questionWords.getWords();
        ArrayList<WordFreq> aWords = answerWords.getWords();
        for (int i = 0; i < aWords.size(); i++) {
            for (int j = 0; j < qWords.size(); j++) {
                String aWord = aWords.get(i).getWord();
                String qWord = qWords.get(i).getWord();
                if(aWord.equals(qWord)) count++;
            }
        }
        double percentOfAnswer = (double)(count) / aWords.size();
        if(percentOfAnswer >= 0.1) return true;
        else return false;
    }
}

