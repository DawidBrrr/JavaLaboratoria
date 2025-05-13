import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
//        List<Runnable> threads= new ArrayList<>();
//        for(int i = 0; i<270; i++){
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    new Client().start();
//                }
//            });
//            thread.start();
//            threads.add(thread);
//        }

        System.out.println(
                QuestionHandler.getInstance().getQuestion(1));
    }
}
