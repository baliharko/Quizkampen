package Quiz.ServerSide;


import java.io.*;
import java.security.PublicKey;
import java.util.*;

public class Database {

    private ArrayList<Question> databas;
    private int questionNo;

    private static String readQuestionfromFile() {
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


    private void addQuestionsTodatabas(String q) {
        this.databas = new ArrayList<>();

        String[] questionsToArray = q.split("\n");//array[5] är rätt svar

        for (int i = 0; i < questionsToArray.length; i++) {
            String[] line = questionsToArray[i].split(",");
            String[] val = {line[2], line[3], line[4], line[5]};
            Question qs = new Question(line[0], line[1], line[6].trim(), val);//line0=catagory,line1=frågor,linr6=svar
            databas.add(qs);
        }

    }

    public Database() {
        this.questionNo = 0;
        try {
            addQuestionsTodatabas(readQuestionfromFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Question getQuestion(int index) {
        Question out = databas.get(index);
        this.questionNo++;
        return out;
    }

    public List<String> getCategoryList() {
        List<String> categoryList = new ArrayList<>();
        for (int i = 0; i < databas.size(); i++) {
            String category = databas.get(i).getCategory();
            if (!categoryList.contains(category)) {
                categoryList.add(category);
            }
        }
        return categoryList;
    }

    public List<String> getQustionByCategory(String inputCategory) {
        List<String> questionsByCategory = new ArrayList<>();
        String cat;
        String ques;
        for (int i = 0; i < databas.size(); i++) {
            cat = databas.get(i).getCategory();
            if (cat.equals(inputCategory)) {
                ques = databas.get(i).getQuestion();
                questionsByCategory.add(ques);
            }
        }
        Collections.shuffle(questionsByCategory);
        return questionsByCategory;
    }

    //Test
//    public static void main(String[] args) {
//        Database ds = new Database();
//        ds.addQuestionsTodatabas(readQuestionfromFile());
//        List<String> catagories = ds.getCategoryList();
//        System.out.println(catagories);
//
//        ListIterator<String> listIterator = ds.getQustionByCategory(catagories.get(0)).listIterator();
//        while (listIterator.hasNext()) {
//            System.out.println(listIterator.next());
//        }
//    }
}
