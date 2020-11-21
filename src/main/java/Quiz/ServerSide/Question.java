package Quiz.ServerSide;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question implements Serializable {
    private String question;
    private String answer;
    private String[] val;
    private String quizCatagory;

    public Question(String quizCatagory, String question, String answer, String[] val) {
        this.quizCatagory=quizCatagory;
        this.question = question;
        this.answer = answer;
        this.val = val;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuizCatagory() {
        return quizCatagory;
    }

    public String[] getVal() {
        return val;
    }

    public void printVal() {
        for (String element : val) {
            System.out.println(element);
        }
    }

    public boolean isRightAnswer(String attempt) {
        return this.answer.equalsIgnoreCase(attempt);
    }
}
