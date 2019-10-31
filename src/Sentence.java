import java.lang.reflect.Array;
import java.util.ArrayList;

public class Sentence {
    private String sentence;
    private ArrayList<Word> words;
    private int word_count, syllable_count;

    public Sentence(String sen){ //constructor
        sentence = sen;
        words = break_sentence_into_words(sen);
        word_count = count(" ")+1-count(" – ");
        syllable_count = num_syl();
    }

    //methods:
    private int count(String t){
        int idx = 0, cnt = 0;
        while(sentence.indexOf(t, idx) != -1){
            cnt++;
            idx = sentence.indexOf(t, idx) + 1;
        }
        return cnt;
    }

    private ArrayList<Word> break_sentence_into_words(String sen) { //change some technicalities depending on the text
        ArrayList<Word> ret = new ArrayList<Word>();
        String other = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] words = sen.split(" ");
        for (String x:words) {
            if(!x.equals("– ")) continue;
            if(!other.contains(x.substring(0, 1))) x = x.substring(1);
            if(!other.contains(x.substring(x.length()-1))) x = x.substring(0, x.length()-1);
            ret.add(new Word(x.toLowerCase(), syllables(x)));
        }
        return ret;
    }

    private int num_syl() {
        int tot = 0;
        for(Word w : words){
            tot += w.getSyllables();
        }
        return tot;
    }

    private static int syllables(String s) { //get syllables of s
        return Main.getSyllablesFromString(s);
    }

    //getters and setters:
    public String getSentence() {
        return sentence;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public int getWord_count() {
        return word_count;
    }

    public int getSyllable_count() {
        return syllable_count;
    }
}