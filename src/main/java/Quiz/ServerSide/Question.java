package Quiz.ServerSide;

import java.io.Serializable;

public class Question implements Serializable {
    private String question;
    private String answer;

    public Question(String q, String a) {
        this.question = q;
        this.answer = a;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
