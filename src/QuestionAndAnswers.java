import javax.print.Doc;
import java.util.ArrayList;

public class QuestionAndAnswers {
    private Document question;
    private ArrayList<Document> answers;

    public QuestionAndAnswers(Document question, ArrayList<Document> ans) {
        this.question = question;
        answers = ans;
    }

    public ArrayList<Document> getAnswers() {
        return this.answers;
    }

   public Document getQuestion() {
        return this.question;
    }

    public void addAnswer(Document answer) {
        this.answers.add(answer);
    }
    public Document getAnswer(int index){
        return answers.get(index);
    }
}
