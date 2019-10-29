import java.util.ArrayList;

public class QuestionAndAnswers {
    private String question;
    private ArrayList<String> answers;

    public QuestionAndAnswers(String question) {
        this.question = question;
    }

    private ArrayList<String> getAnswers(String[] qAndA) {
        return this.answers;
    }

    private String getQuestion(String[] qAndA) {
        return this.question;
    }

    private void addAnswer(String answer) {
        this.answers.add(answer);
    }
}
