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
                if(aWord.equals(qWord)) count+=aWords.get(i).getFrequency();
            }
        }
        double percentOfAnswer = (double)(count) / aWords.size();
        if(percentOfAnswer >= 0.1) return true;
        else return false;
    }

/*
    public boolean isProfessional(Document response){
        int a, b, c, d; //factors for the equation
        return (response.get_multi_syllabic_percent()*a + b*response.get_formal() - c*response.get_informal()
        + d*response.get_kincaid_score()) >= 5;
    }
*/

    private static ArrayList<Integer> getSyllableList(ArrayList<Word> testWords) {
        ArrayList<Integer> syllables = new ArrayList<>();
        for (int i = 0; i < testWords.size(); i++) {
            String thisWord = testWords.get(i).getLetters();

            syllables.add(getSyllablesFromString(thisWord));
        }
        return syllables;
    }

    public static int getSyllablesFromString(String thisWord) {
        ArrayList<String> vowelChains = getVowelChains(thisWord);
        int syllables = vowelChains.size();
        if(vowelChains.size()==0)return 1;
        syllables+=syllablesForSilentE(thisWord, vowelChains);
        syllables+=syllablesForIChains(thisWord, vowelChains);
        syllables+=syllablesForEd(thisWord, vowelChains);
        if(thisWord.contains("oolo"))syllables++;



        if(syllables==0)syllables=1;
        return syllables;
    }

    public static int syllablesForEd(String thisWord, ArrayList<String> vowelChains) {

        if(thisWord.length()>5){
            if(!containsVowel(thisWord.substring(thisWord.length()-5, thisWord.length()-2))&&thisWord.substring(thisWord.length()-2).equals("ed"))return 0;
        }
        if(thisWord.substring(thisWord.length()-2).equals("ed"))return -1;
        return 0;
    }

    private static int syllablesForIChains(String thisWord, ArrayList<String> vowelChains) {
        if(thisWord.length()>4) {
            if (thisWord.substring(thisWord.length() - 3).equals("ier")) return 1;
        }
        if(thisWord.contains("ia")&&!thisWord.contains("cia"))return 1;
        if(thisWord.contains("iest"))return 1;
        if(thisWord.contains("iu"))return 1;
        return 0;
    }

    private static int syllablesForSilentE(String thisWord, ArrayList<String> vowelChains) {
        int change = 0;
        if(thisWord.length()>4) {
            if(vowelChains.size()>1) {
                // if (thisWord.substring(3, 4).equals("e") && vowelChains.get(1).equals("e"))change--;
            }
            String substringBeforeEnd = thisWord.substring(thisWord.length() - 3, thisWord.length() - 1);
            if (thisWord.length() > 4 && !containsVowel(substringBeforeEnd) && !substringBeforeEnd.equals("th"))
                return change;
        }
        if(thisWord.substring(thisWord.length()-1).equals("e")&&vowelChains.get(vowelChains.size()-1).equals("e"))change--;
        return change;
    }

    private static ArrayList<String> getVowelChains(String thisWord) {
        ArrayList<String> vowelChains= new ArrayList<>();
        String thisVowelChain = "";
        while(thisWord.length()>0){
            String letter = thisWord.substring(0, 1);
            if(isVowel(letter)){
                thisVowelChain+=letter;
            }else{
                if(!thisVowelChain.equals(""))vowelChains.add(thisVowelChain);
                thisVowelChain="";
            }
            thisWord=thisWord.substring(1);
        }
        if(!thisVowelChain.equals(""))vowelChains.add(thisVowelChain);
        return vowelChains;
    }
    private static boolean containsVowel(String thisString){
        for (int i = 0; i < thisString.length(); i++) {
            String letter = thisString.substring(i, i+1);
            if(isVowel(letter))return true;
        }
        return false;
    }
    private static boolean isVowel(String letter) {
        String vowels = "aeiouy";
        if(vowels.contains(letter))return true;
        return false;
    }

}

