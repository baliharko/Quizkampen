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

    private static ArrayList<Question> databas;

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


    private static void addQuestionsTodatabas(String q) {
        databas = new ArrayList<>();
        String[] questionsToArray = q.split("\n");//array[5] är rätt svar

        for (int i = 0; i < questionsToArray.length; i++) {
            String[] line = questionsToArray[i].split(",");
            Question qs = new Question();
        }

        //ArrayList<String> options = new ArrayList<>();

        options.add(question[1]);
        options.add(question[2]);
        options.add(question[3]);
        options.add(question[4]);
        Question question1 = new Question(question[0], question[5], options);
        databas.add(question1);
    }

    public static void main(String[] args) throws FileNotFoundException {

        String test = readQuestionfromFile();
        addQuestionsTodatabas(test);
        Question q = databas.get(0);

        System.out.println(q.getQuestion());
        System.out.println(q.getAnswer());
    }

}
