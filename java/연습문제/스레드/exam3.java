package ch12.exam03;

import ch12.exam01.MovieThread;

public class ThreadExample {
    public static void main(String[] args) {
        Thread thread = new MovieThread();
        thread.setDaemon(true);
        thread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }

}
```
package ch12.exam03;

public class MovieThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("동영상을 재생합니다.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO: handle exception
            }
            
        }
    }

}
