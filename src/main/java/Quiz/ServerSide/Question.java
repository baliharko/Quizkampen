package Quiz.ServerSide;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String question;
    private String answer;
    private ArrayList<String> options;

    public Question(String question, String answer, ArrayList<String> options) {
        this.question = question;
        this.answer = answer;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isRightAnswer(String attempt) {
        return this.answer.equalsIgnoreCase(attempt);
    }
}
