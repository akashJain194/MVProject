import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static ArrayList<String> formal_words, informal_words;
    public static String[] dataset;

    public static void main(String[] args) {
        formal_words = read_file_as_list("texts/formal_words");
        informal_words = read_file_as_list("texts/informal_words");
        dataset = readFileAsString("texts/test data set updated - test data set updated.csv");
        ArrayList<QuestionAndAnswers> questionAndAnswersList = makeQuestionAndAnswersList(dataset);
        for (int i = 0; i < questionAndAnswersList.size(); i++) {
            QuestionAndAnswers thisQandA = questionAndAnswersList.get(i);
            System.out.println(thisQandA.getQuestion().toString());
            for (int j = 0; j < thisQandA.getAnswers().size(); j++) {
                Document thisAnswer = thisQandA.getAnswer(j);
                System.out.println(thisAnswer.toString());
                System.out.println("relevance: " + relevance(thisQandA.getQuestion().getKey_words(), thisAnswer.getKey_words()));
                System.out.println("isProfessional: " + isProfessional(thisAnswer));

            }
            System.out.println();

        }
        System.out.println(isProfessional(questionAndAnswersList.get(0).getQuestion()));

    } //TODO: if time then parse big dataset

    private static ArrayList<QuestionAndAnswers> makeQuestionAndAnswersList(String[] dataset) {
        ArrayList<QuestionAndAnswers> qAndAs = new ArrayList<>();
        int ans1 = 12 + 1, ans2 = 10 + ans1, ans3 = 8 + ans2, ans4 = 6 + ans3, ans5 = 9 + ans4, ans6 = 25 + ans5, ans7 = 5 + ans6;
        boolean isques = true;
        ArrayList<Document> ans = new ArrayList<Document>();
        Document cur = new Document(dataset[1]);
        for (int i = 2; i < dataset.length; i++) {
            if (isques) {
                //process question
                cur = new Document(dataset[i]);
                isques = false;
                continue;
            }
            //process ans
            ans.add(new Document(dataset[i]));
            if (i == ans1 || i == ans2 || i == ans3 || i == ans4 || i == ans5 || i == ans6 || i == ans7) {
                qAndAs.add(new QuestionAndAnswers(cur, ans));
                isques = true;
                ans.clear();
            }
        }
        return qAndAs;
    }

    public static String[] readFileAsString(String filename) {
        Scanner scanner;
        StringBuilder output = new StringBuilder();
        try {
            scanner = new Scanner(new FileInputStream(filename), "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                output.append(line.trim() + "\n");
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + filename);
        }
        String newOutput = output.toString();
        String[] qAndA = newOutput.split("\"");
        return qAndA;
    }

    public static ArrayList<String> read_file_as_list(String filename) {
        Scanner scanner;
        ArrayList<String> output = new ArrayList<String>();

        try {
            scanner = new Scanner(new FileInputStream(filename), "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                output.add(line.trim());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + filename);
        }

        return output;
    }

    //    public WordBucket getImportantWords(Document QorA){
//        for (int i = 0; i < ; i++) {
//
//        }
//    }
    public static boolean relevance(WordBucket questionWords, WordBucket answerWords) {
        int count = 0;
        double thresholdForRelevance = 0.10;
        ArrayList<WordFreq> qWords = questionWords.getWords();
        ArrayList<WordFreq> aWords = answerWords.getWords();

        for (int i = 0; i < aWords.size(); i++) {
            String aWord = aWords.get(i).getWord();
            for (int j = 0; j < qWords.size(); j++) {
                String qWord = qWords.get(j).getWord();
                if (aWord.equals(qWord)) count++;
            }
        }
        double percentOfAnswer = (double) (count) / aWords.size();
        if (percentOfAnswer >= thresholdForRelevance) return true;
        return false;
    }

    public static boolean isProfessional(Document response) {
        double a = 2.0, b = 2.0, c = 1.0, d = 0.05; //factors for the equation
        double score = (response.get_multi_syllabic_percent() * a + b * response.get_percent_formal() - c * response.get_percent_informal()
                + d * (100 - response.get_kincaid_score()));
        System.out.print("Score: " + score);
        return score >= 5;
    }

    private static ArrayList<Integer> getSyllableList(ArrayList<Word> testWords) {
        ArrayList<Integer> syllables = new ArrayList<>();
        for (int i = 0; i < testWords.size(); i++) {
            String thisWord = testWords.get(i).getWord();

            syllables.add(getSyllablesFromString(thisWord));
        }
        return syllables;
    }

    public static int getSyllablesFromString(String thisWord) {
        ArrayList<String> vowelChains = getVowelChains(thisWord);
        int syllables = vowelChains.size();
        if (vowelChains.size() == 0) return 1;
        syllables += syllablesForSilentE(thisWord, vowelChains);
        syllables += syllablesForIChains(thisWord, vowelChains);
        syllables += syllablesForEd(thisWord, vowelChains);
        if (thisWord.contains("oolo")) syllables++;


        if (syllables == 0) syllables = 1;
        return syllables;
    }

    public static int syllablesForEd(String thisWord, ArrayList<String> vowelChains) {

        if (thisWord.length() > 5) {
            if (!containsVowel(thisWord.substring(thisWord.length() - 5, thisWord.length() - 2)) && thisWord.substring(thisWord.length() - 2).equals("ed"))
                return 0;
        }
        if (thisWord.substring(thisWord.length() - 2).equals("ed")) return -1;
        return 0;
    }

    private static int syllablesForIChains(String thisWord, ArrayList<String> vowelChains) {
        if (thisWord.length() > 4) {
            if (thisWord.substring(thisWord.length() - 3).equals("ier")) return 1;
        }
        if (thisWord.contains("ia") && !thisWord.contains("cia")) return 1;
        if (thisWord.contains("iest")) return 1;
        if (thisWord.contains("iu")) return 1;
        return 0;
    }

    private static int syllablesForSilentE(String thisWord, ArrayList<String> vowelChains) {
        int change = 0;
        if (thisWord.length() > 4) {
            if (vowelChains.size() > 1) {
                // if (thisWord.substring(3, 4).equals("e") && vowelChains.get(1).equals("e"))change--;
            }
            String substringBeforeEnd = thisWord.substring(thisWord.length() - 3, thisWord.length() - 1);
            if (thisWord.length() > 4 && !containsVowel(substringBeforeEnd) && !substringBeforeEnd.equals("th"))
                return change;
        }
        if (thisWord.substring(thisWord.length() - 1).equals("e") && vowelChains.get(vowelChains.size() - 1).equals("e"))
            change--;
        return change;
    }

    private static ArrayList<String> getVowelChains(String thisWord) {
        ArrayList<String> vowelChains = new ArrayList<>();
        String thisVowelChain = "";
        while (thisWord.length() > 0) {
            String letter = thisWord.substring(0, 1);
            if (isVowel(letter)) {
                thisVowelChain += letter;
            } else {
                if (!thisVowelChain.equals("")) vowelChains.add(thisVowelChain);
                thisVowelChain = "";
            }
            thisWord = thisWord.substring(1);
        }
        if (!thisVowelChain.equals("")) vowelChains.add(thisVowelChain);
        return vowelChains;
    }

    private static boolean containsVowel(String thisString) {
        for (int i = 0; i < thisString.length(); i++) {
            String letter = thisString.substring(i, i + 1);
            if (isVowel(letter)) return true;
        }
        return false;
    }

    private static boolean isVowel(String letter) {
        String vowels = "aeiouy";
        if (vowels.contains(letter)) return true;
        return false;
    }

}

