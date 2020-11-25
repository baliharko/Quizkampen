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
                sb.append(scan.nextLine() + "\n");
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
            String[] val = {line[2], line[3], line[4], line[5]};
//            Question qs = new Question(line[0], line[1], line[6].trim(), val);//line0=catagory,line1=frågor,linr6=svar
//            databas.add(qs);
        }

    }

    public static void main(String[] args) throws FileNotFoundException {

//        String test = readQuestionfromFile();
//        addQuestionsTodatabas(test);
//        Question q = databas.get(11);
//
//        System.out.println("Ämne: " + q.getQuizCatagory());
//        System.out.println("Question: " + q.getQuestion());
//        System.out.println("Valet är:");
//        q.printVal();
//        System.out.println("Svar: " + q.getAnswer());


    }

}
