package ch14.sec02.exam01;

public class Student {
    private String hakbun;
    private String name;
    private int korean, eng, math;

    public Student(String hakbun, String name, int korean, int eng, int math) {
        this.hakbun = hakbun;
        this.name = name;
        this.korean = korean;
        this.eng = eng;
        this.math = math;
    }

    public String getHakbun() {
        return hakbun;
    }

    public void setHakbun(String hakbun) {
        this.hakbun = hakbun;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKorean() {
        return korean;
    }

    public void setKorean(int korean) {
        this.korean = korean;
    }

    public int getEng() {
        return eng;
    }

    public void setEng(int eng) {
        this.eng = eng;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }
    double getAverage() {
        return getTotal() / 3;
    }
    int getTotal() {
        return (korean + eng + math);
    }

}
```
package ch14.sec02.exam01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class scoreExam {
    static ArrayList<Student> students = new ArrayList<>();

    public static void readFromFile() throws Exception {
        Reader reader = new FileReader("C:/D/student.txt");
        BufferedReader inFile = new BufferedReader(reader);
        while (true) {
            String sLine = null;
            if ( (sLine = inFile.readLine()) == null)
                break;
            StringTokenizer st = new StringTokenizer(sLine, ",");
            if ( st.countTokens() != 5)
                continue;
            String hakbun = null;
            String name = null;
            int kor, eng, math;

            hakbun = st.nextToken().trim();
            name = st.nextToken().trim();
            kor = Integer.parseInt(st.nextToken().trim());
            eng = Integer.parseInt(st.nextToken().trim());
            math = Integer.parseInt(st.nextToken().trim());

            students.add(new Student(hakbun, name, kor, eng, math));
        }
    }
    public static void main(String[] args) {
        try {
            readFromFile();
        } catch(Exception e ) {}

        for (Student st: students) {
            System.out.println(st.getHakbun() + " " + st.getName());
        }

        int koreanSum = 0;
        for (Student st : students) {
            koreanSum += st.getKorean();
        }
        double koreanAverage = (double) koreanSum / students.size();
        System.out.println("국어 전체 평균 : " + koreanAverage);

        Student topStudent = null;
        double topAverage = 0;

        for (Student st : students) {
            if (st.getAverage() > topAverage) {
                topAverage = st.getAverage();
                topStudent = st;
            }
        }

        if (topStudent != null) {
            System.out.println("최고 평균 학생 : " + topStudent.getHakbun() + " " + topStudent.getName());
            System.out.println("평균 점수 : " + topAverage);
        }
        
    }

}

