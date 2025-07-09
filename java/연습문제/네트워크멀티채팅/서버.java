package ch16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
    final static int nPort = 8000;
    public static void main(String[] args) {
        MultiServer multiServer = new MultiServer();
        multiServer.start();
    }

    public void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(nPort);
            while (true) {
                System.out.println("[클라이언트 연결 대기중]");
                socket = serverSocket.accept();

                ReceiveThread receiveThread = new ReceiveThread(socket);
                receiveThread.start();
            }

        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket!=null) {
                try {
                    serverSocket.close();
                    System.out.println("[서버종료]");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("[서버소켓통신에러]");
                }
            }
        }
    }

}

```

package ch16.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReceiveThread extends Thread {
    static List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());

    Socket socket = null;
    BufferedReader in = null;
    PrintWriter out = null;

    public ReceiveThread(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            list.add(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String name = "";
        try {
            name = in.readLine();
            System.out.println("[" + name + "새연결생성]");
            sendAll("[" + name + "]님이 들어오셨습니다.");

            while (in != null) {
                String inputMsg = in.readLine();
                if ("quit".equals(inputMsg)) break;

                //  디버깅: 바이트 배열 출력
                System.out.println("수신된 원본 문자열: " + inputMsg);
                byte[] rawBytes = inputMsg.getBytes("UTF-8");
                System.out.print("UTF-8 바이트 배열: ");
                for (byte b : rawBytes) {
                    System.out.print(String.format("%02X ", b));
                }
                System.out.println();

                System.out.println(name + ">>" + inputMsg);
                sendAll(name + ">>" + inputMsg);
            }
        } catch (Exception e) {
            System.out.println("[" + name + " 접속끊김]");
        } finally {
            sendAll("[" + name + "]님이 나가셨습니다");
            list.remove(out);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[" + name + " 연결종료]");
    }

    private void sendAll(String s) {
        for (PrintWriter out : list) {
            out.println(s);
            out.flush();
        }
    }
}
