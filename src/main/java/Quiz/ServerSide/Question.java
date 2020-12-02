package Quiz.ServerSide;

import java.io.Serializable;

public class Question implements Serializable {
    private String category;
    private String question;
    private String answer;
    private String[] options;

    public Question(String category, String question, String answer, String[] options) {
        this.category = category;
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

    public String getCategory() {
        return category;
    }
}
