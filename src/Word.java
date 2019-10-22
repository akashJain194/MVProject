public class Word {
    private int syllables;
    private String letters;
    public Word(String letters, int syllables){
        this.letters=letters;
        this.syllables=syllables;
    }
    public int getSyllables(){
        return syllables;
    }
    public String getLetters(){
        return letters;
    }
}

