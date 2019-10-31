import javax.print.Doc;
import java.util.ArrayList;

public class QuestionAndAnswers {
    private Document question;
    private ArrayList<Document> answers;

    public QuestionAndAnswers(Document question, ArrayList<Document> ans) {
        this.question = question;
        answers = ans;
    }

    private ArrayList<Document> getAnswers() {
        return this.answers;
    }

    private Document getQuestion() {
        return this.question;
    }

    public void addAnswer(Document answer) {
        this.answers.add(answer);
    }
}
