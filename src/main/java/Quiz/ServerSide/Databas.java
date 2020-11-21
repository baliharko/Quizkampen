package Quiz.ServerSide;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Databas {
    //TODO:
    // Arralist databastyp
    // method som ska läsa från text filen
    // trim för att hitta end of file

    private static ArrayList<Question> questions;

    private static String readQuestionfromFile() throws FileNotFoundException {
        String path = "src/main/java/Quiz/ServerSide/Resources/Questions.txt";
        StringBuilder sb = new StringBuilder();
        try (Scanner scan = new Scanner(new File(path))) {
            while (scan.hasNext()) {
                sb.append(scan.nextLine()+  "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    private static void addQuestionsToList(String q) {
        questions = new ArrayList<>();
        String[] question = q.split(",");//array[5] är rätt svar

        ArrayList<String> val = new ArrayList<>();

        val.add(question[1]);
        val.add(question[2]);
        val.add(question[3]);
        val.add(question[4]);
        Question question1 = new Question(question[0], question[5], val);
        questions.add(question1);
    }

    public static void main(String[] args) throws FileNotFoundException {

        String test = readQuestionfromFile();
        addQuestionsToList(test);
        Question q = questions.get(0);

        System.out.println(q.getQuestion());
        System.out.println(q.getAnswer());
    }

}
