package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static final int ServerPost = 8000;

    public static void main(String[] args) {
        ChatClient multiClient = new ChatClient();
        multiClient.start();
    }

    public void start() {
        Socket socket = null;
        BufferedReader in = null;
        try {
            socket = new Socket("192.168.40.75", ServerPost);// localhost 주소에 server역할의 pc ip주소를 기입하면 됌
            System.out.println("[서버와 연결되었습니다.]");

            String name = "user" + (int) (Math.random() * 10);
            Thread sendThread = new SendThread(socket, name);
            sendThread.start();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            while (in != null) {
                String inputMsg = in.readLine();
                if (("[" + name + "]님이 나가셨습니다").equals(inputMsg))
                    break;
                System.out.println("from: " + inputMsg);
            }
        } catch (IOException e) {
            System.out.println("[서버 접속끊김]");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[서버 연결 종료]");
    }
}
```

package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendThread extends Thread {
    Socket socket = null;
    String name;

    BufferedReader reader;

    public SendThread(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

            out.write(name + "\n");
            out.flush();

            System.out.println("[디버깅] 전송할 이름: " + name);
            printBytes(name);
            Scanner scanner = new Scanner(System.in, "CP949");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

            while (true) {
                String msg = scanner.nextLine();
                // CP949 입력 → UTF-8 문자열로 강제 변환
                String converted = new String(msg.getBytes("CP949"), "UTF-8");
                out.write(msg + "\n");
                out.flush();
                if (msg.equals("quit"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printBytes(String msg) {
        try {
            byte[] bytes = msg.getBytes("UTF-8");
            System.out.print("[UTF-8 바이트]: ");
            for (byte b : bytes) {
                System.out.printf("%02X ", b);
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
