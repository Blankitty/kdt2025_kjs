package ch15.sec03;

public class VO {
    private String districtCode;
    private String districtName;
    private String yearMonth;
    private int totalPopulation;   // 총 인구수
    private int householdCount;    // 세대수
    private double avgPopulationPerHousehold; // 세대당 인구
    private int malePopulation;    // 남자 인구수
    private int femalePopulation;


    public VO(String districtCode, String districtName, String yearMonth, int totalPopulation, int householdCount,
            double avgPopulationPerHousehold, int malePopulation, int femalePopulation) {
        this.districtCode = districtCode;
        this.districtName = districtName;
        this.yearMonth = yearMonth;
        this.totalPopulation = totalPopulation;
        this.householdCount = householdCount;
        this.avgPopulationPerHousehold = avgPopulationPerHousehold;
        this.malePopulation = malePopulation;
        this.femalePopulation = femalePopulation;
    }

    public String getDistrictCode() {
        return districtCode;
    }
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
    public String getDistrictName() {
        return districtName;
    }
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
    public String getYearMonth() {
        return yearMonth;
    }
    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }
    public int getTotalPopulation() {
        return totalPopulation;
    }
    public void setTotalPopulation(int totalPopulation) {
        this.totalPopulation = totalPopulation;
    }
    public int getHouseholdCount() {
        return householdCount;
    }
    public void setHouseholdCount(int householdCount) {
        this.householdCount = householdCount;
    }
    public double getAvgPopulationPerHousehold() {
        return avgPopulationPerHousehold;
    }
    public void setAvgPopulationPerHousehold(double avgPopulationPerHousehold) {
        this.avgPopulationPerHousehold = avgPopulationPerHousehold;
    }
    public int getMalePopulation() {
        return malePopulation;
    }
    public void setMalePopulation(int malePopulation) {
        this.malePopulation = malePopulation;
    }
    public int getFemalePopulation() {
        return femalePopulation;
    }
    public void setFemalePopulation(int femalePopulation) {
        this.femalePopulation = femalePopulation;
    } 

}
```
package ch15.sec03;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;



public class SeoulPeople {
    static ArrayList<VO> vo = new ArrayList<>();

    public static void readFromFile() throws Exception {
        BufferedReader inFile = new BufferedReader(
            new InputStreamReader(new FileInputStream("C:/D/seoul_people.csv"), "UTF-8")
        );

        String sLine;
        while ((sLine = inFile.readLine()) != null) {
            try {
                StringTokenizer st = new StringTokenizer(sLine, ",");
                if (st.countTokens() < 8) continue; // 열 부족 시 스킵

                String districtCode = st.nextToken().trim();
                String districtName = st.nextToken().trim();
                String yearMonth = st.nextToken().trim();
                int totalPopulation = safeParseInt(st.nextToken());
                int householdCount = safeParseInt(st.nextToken());
                double avgPopulation = safeParseDouble(st.nextToken());
                int malePopulation = safeParseInt(st.nextToken());
                int femalePopulation = safeParseInt(st.nextToken());

                vo.add(new VO(districtCode, districtName, yearMonth,
                        totalPopulation, householdCount, avgPopulation,
                        malePopulation, femalePopulation));

            } catch (Exception e) {
                System.out.println("잘못된 줄 건너뜀: " + sLine);
            }
        }
        inFile.close();
    }

    // 안전한 숫자 파싱
    private static int safeParseInt(String s) {
        try {
            return Integer.parseInt(s.trim().replace(",", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    private static double safeParseDouble(String s) {
        try {
            return Double.parseDouble(s.trim().replace(",", ""));
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static void main(String[] args) {
    try {
        readFromFile();
    } catch (Exception e) {
        System.out.println("파일 읽기 오류: " + e.getMessage());
        e.printStackTrace();
        return;
    }

    // 리스트가 비어 있으면 바로 종료
    if (vo.isEmpty()) {
        System.out.println(" 데이터가 없습니다. 최대/최소값을 구할 수 없습니다.");
        return;
    }

    // 평균 인구 계산
    int total = 0;
    for (VO v : vo) {
        total += v.getTotalPopulation();
    }
    double average = (double) total / vo.size();
    System.out.println("서울시 전체 인구수 평균: " + average);

    VO maxMale = Collections.max(vo, Comparator.comparingInt(VO::getMalePopulation));
    VO maxFemale = Collections.max(vo, Comparator.comparingInt(VO::getFemalePopulation));
    VO maxAvg = Collections.max(vo, Comparator.comparingDouble(VO::getAvgPopulationPerHousehold));

    // 최소값은 필터링된 리스트로 계산
    VO minMale = Collections.min(
        vo.stream().filter(v -> v.getMalePopulation() > 0).collect(Collectors.toList()),
    Comparator.comparingInt(VO::getMalePopulation));

    VO minFemale = Collections.min(
        vo.stream().filter(v -> v.getFemalePopulation() > 0).collect(Collectors.toList()),
    Comparator.comparingInt(VO::getFemalePopulation));

    VO minAvg = Collections.min(
        vo.stream().filter(v -> v.getAvgPopulationPerHousehold() > 0).collect(Collectors.toList()),
    Comparator.comparingDouble(VO::getAvgPopulationPerHousehold));

    System.out.println("남자 인구 가장 많은 구: " + maxMale.getDistrictCode() + " (" + maxMale.getMalePopulation() + ")");
    System.out.println("남자 인구 가장 적은 구: " + minMale.getDistrictCode() + " (" + minMale.getMalePopulation() + ")");
    System.out.println("여자 인구 가장 많은 구: " + maxFemale.getDistrictCode() + " (" + maxFemale.getFemalePopulation() + ")");
    System.out.println("여자 인구 가장 적은 구: " + minFemale.getDistrictCode() + " (" + minFemale.getFemalePopulation() + ")");
    System.out.println("세대당 인구 가장 많은 구: " + maxAvg.getDistrictCode() + " (" + maxAvg.getAvgPopulationPerHousehold() + ")");
    System.out.println("세대당 인구 가장 적은 구: " + minAvg.getDistrictCode() + " (" + minAvg.getAvgPopulationPerHousehold() + ")");
}

    
}
    
