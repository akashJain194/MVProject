public class WordFreq {
    private String word;
    private int frequency;

    public WordFreq(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public void add(int count) {
        frequency += count;
    }

    public int getFrequency() {
        return frequency;
    }

    public String getWord() {
        return word;
    }
}
