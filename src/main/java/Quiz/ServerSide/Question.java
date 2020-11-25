package Quiz.ServerSide;

import java.io.Serializable;

public class Question implements Serializable {
    private String question;
    private String answer;
    private String[] options;

    public Question(String question, String answer, String[] options) {
        this.question = question;
        this.answer = answer;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public synchronized boolean isRightAnswer(String attempt) {
        return this.answer.equalsIgnoreCase(attempt);
    }

    public String[] getOptions() {
        return this.options;
    }
}
