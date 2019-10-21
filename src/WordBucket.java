
import java.util.ArrayList;

public class WordBucket {
    //   private ArrayList<String> words;
    private ArrayList<WordFreq> words;
    boolean sorted;

    public WordBucket(ArrayList<String> words) {
        this.words = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            add(words.get(i));
        }
        sorted = false;
    }

    public WordBucket() {
        this.words = new ArrayList<>();
        sorted = true;
    }

    //    private boolean contains(String target){
//        for (int i = 0; i < words.size(); i++) {
//            if(words.get(i).getWord().equals(target))return true;
//        }
//        return false;
//    }
    public ArrayList<WordFreq> getWords() {
        return words;
    }

    public WordFreq getWord(int loc) {
        if (loc >= 0 && loc < words.size()) {
            return words.get(loc);
        }
        WordFreq blank = new WordFreq("", 0);
        return blank;
    }

    public void add(String word) {
        add(word, 1);
        sorted = false;
    }

    public void add(WordFreq newWord) {
        words.add(newWord);
        sorted = false;
    }

    public void add(String word, int count) {
        boolean unique = true;
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWord().equals(word)) {
                words.get(i).add(count);
                unique = false;
            }
        }
        if (unique) {
            WordFreq newWord = new WordFreq(word, count);
            words.add(newWord);
        }
        sorted = false;
    }

    public int getCountOf(String target) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWord().equals(target)) return words.get(i).getFrequency();
        }
        return 0;
    }

    public int size() {
        int count = 0;
        for (int i = 0; i < words.size(); i++) {
            count += words.get(i).getFrequency();
        }
        return count;
    }

    public int getNumUnique() {
        return words.size();
    }

    public String getMostFreq() {
        if (!sorted) sortWords();
        return words.get(0).getWord();
    }

    public ArrayList<String> getNMostFreq(int n) {
        if (!sorted) sortWords();
        ArrayList<String> freqWords = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            freqWords.add(words.get(i).getWord());
        }
        return freqWords;
    }

    private void sortWords() {
        ArrayList<WordBucket> bucketList = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            WordBucket newBucket = new WordBucket();
            newBucket.add(words.get(i).getWord(), words.get(i).getFrequency());
            bucketList.add(newBucket);
        }
        while (bucketList.size() > 1) {
            bucketList = sortOneStep(bucketList);
        }
        words = bucketList.get(0).getWords();
    }

    private ArrayList<WordBucket> sortOneStep(ArrayList<WordBucket> bucketList) {
        ArrayList<WordBucket> newList = new ArrayList<>();
        if (bucketList.size() % 2 != 0) {

        }
        for (int i = 0; i < bucketList.size(); i += 2) {
            if (i + 1 < bucketList.size()) {
                newList.add(combineInOrder(bucketList.get(i), bucketList.get(i + 1)));
            } else newList.add(bucketList.get(i));
        }
        return newList;
    }

    public WordBucket combineInOrder(WordBucket a, WordBucket b) {

        WordBucket newBucket = new WordBucket();
        int aLoc = 0, bLoc = 0;
        while (aLoc < a.size() || bLoc < b.size()) ;
        if (a.getWord(aLoc).getFrequency() > b.getWord(bLoc).getFrequency()) {
            newBucket.add(a.getWord(aLoc));
            aLoc++;
        } else {
            newBucket.add(b.getWord(bLoc));
            bLoc++;
        }
        return newBucket;
    }
}