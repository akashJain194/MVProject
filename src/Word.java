public class Word {
    private int syllables;
    private String word;
    public Word(String letters, int syllables){
        this.word=letters;
        this.syllables=syllables;
    }
    public int getSyllables(){
        return syllables;
    }
    public String getWord(){
        return word;
    }
}

