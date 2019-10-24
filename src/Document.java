import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Document {
    private String text;
    private ArrayList<Sentence> sentences;
    private ArrayList<String> unique_words;
    //TODO: Initialize the word bucket with the "key words"
    private WordBucket key_words;

    private int word_count, syl_count, char_count;

    public static Document get_document_from(String filepath){
        String text = read_file_as_string(filepath);//read the file
        Document d = new Document(text);
        return d;
    }

    public Document(String text){
        this.text = text;
        //pre-processing
        sentences = splitIntoSentences(text);
        unique_words = get_unique_words();

        //calculating other important information
        word_count = find_word_count();
        syl_count = find_syl_count();
        char_count = find_char_count();
    }

    //methods:
    public int find_word_count(){
        int wc = 0;
        for(Sentence sen : sentences)wc += sen.getWord_count();
        return wc;
    }

    private int find_syl_count() {
        int sc = 0;
        for(Sentence sen : sentences) sc += sen.getSyllable_count();
        return sc;
    }

    public int find_char_count(){
        int chars = 0;
        for(Sentence sen : sentences){
            for(Word word: sen.getWords()){
                chars += word.getWord().length();
            }
        }
        return chars;
    }

    public int get_sentences_count(){ return sentences.size(); }

    public double get_avg_words_per_sentence(){ return 1.0*word_count/get_sentences_count(); }

    public double get_avg_syl_per_word() { return 1.0*syl_count/word_count; }

    public double get_avg_chars_per_word(){ return 1.0*char_count/word_count; }

    public int get_vocab_size(){
        return unique_words.size();
    }

    public boolean occur_in_same_sentence(String a, String b){
        for(Sentence sentence : sentences){
            String sen = sentence.getSentence();
            if(count_occurences(sen, a) >= 1 && count_occurences(sen, b) >= 1) return true;
        }
        return false;
    }

    public double get_kincaid_score(){
        return 206.835-1.015*get_avg_words_per_sentence()-84.6*get_avg_syl_per_word();
    }

    public int count_occurences(String t){
        int idx = 0, cnt = 0;
        while(text.indexOf(t, idx) != -1){
            cnt++;
            idx = text.indexOf(t, idx) + 1;
        }
        return cnt;
    }

    private int count_occurences(String str, String contain) {  //count occurrence of a string in another string
        int idx = 0, cnt = 0;
        while(str.indexOf(contain, idx) != -1){
            cnt++;
            idx = str.indexOf(contain, idx) + 1;
        }
        return cnt;
    }

    public ArrayList<Sentence> splitIntoSentences(String text) {
        ArrayList<Sentence> output = new ArrayList<>();

        Locale locale = Locale.US;
        BreakIterator breakIterator = BreakIterator.getSentenceInstance(locale);
        breakIterator.setText(text);

        int prevIndex = 0;
        int boundaryIndex = breakIterator.first();
        while(boundaryIndex != BreakIterator.DONE) {
            String sentence = text.substring(prevIndex, boundaryIndex).trim();
            if (sentence.length()>0)
                output.add(new Sentence(sentence));
            prevIndex = boundaryIndex;
            boundaryIndex = breakIterator.next();
        }

        String sentence = text.substring(prevIndex).trim();
        if (sentence.length()>0)
            output.add(new Sentence(sentence));

        return output;
    }

    public ArrayList<String> get_unique_words(){
        ArrayList<String> ret = new ArrayList<String>();
        for(Sentence sen : sentences){
            for(Word word : sen.getWords()){
                if(!ret.contains(word)) ret.add(word.getWord());
            }
        }
        return ret;
    }

    public static String read_file_as_string(String filename) {
        Scanner scanner;
        StringBuilder output = new StringBuilder();

        try {
            scanner = new Scanner(new FileInputStream(filename), "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                output.append(line.trim()+"\n");
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + filename);
        }

        return output.toString();
    }

    public String getText() {
        return text;
    }

    public int getWord_count() {
        return word_count;
    }

    public int getSyl_count() {
        return syl_count;
    }

    public int getChar_count() {
        return char_count;
    }

    public ArrayList<Sentence> getSentences() {
        return sentences;
    }

    public ArrayList<String> getUnique_words() {
        return unique_words;
    }

    public double get_multi_syllabic_percent() {
        return (1.0*multi_syllable_count()/syl_count);
    }

    private int multi_syllable_count() {
        int sc = 0;
        for(Sentence sen : sentences) {
            for(Word w : sen.getWords()){
                if(w.getSyllables() > 1) sc++;
            }
        }
        return sc;
    }

    public double get_percent_formal() {
    }

    public double get_percent_informal() {
    }
}