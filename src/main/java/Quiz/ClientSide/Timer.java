package Quiz.ClientSide;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-18
 * Time:    20:18
 * Project: Inlämning04
 * Copyright: MIT
 */
public class Timer extends Thread {

        int timeToAnswer;
        int second = 100;

        public Timer (int timeToAnswer) {
            this.timeToAnswer = timeToAnswer;
        }


        @Override
        public void run() {

            while(timeToAnswer >= second) {
                System.out.println("Time left: " + timeToAnswer);
                try {
                    Thread.sleep(second);
                    timeToAnswer -= second;

                } catch (InterruptedException e) {
                    System.out.println("Time left: " + timeToAnswer);

                }

            }
        }
    }

