package com.example.csws.common.shRunner;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Component
public class ShParser {

    public boolean isSuccess(String resultString) {
        String[] splitedResult = resultString.split(" ");

        // failure 나오면 false 리턴
        for(String token : splitedResult) {
            if(token.equals("Failure\n")) {
                return false;
            }
        }

        // 성공 리턴
        return true;
    }

    public BufferedReader readResultFile(String... str) {

        BufferedReader br;
        String serverName = str[0];
        String userName = str[1];
        String shellFileName = str[2];
        int index = shellFileName.indexOf(".");

        String baseResultFilePath = "/home/" + serverName + "/info/" + userName;
        String resultFilePath = baseResultFilePath + shellFileName.substring(0, index) + ".txt";

        try {
            FileReader fileReader = new FileReader(resultFilePath);
            br = new BufferedReader(fileReader);
            return br;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public List<JSONObject> printStatusforManager(BufferedReader br) throws IOException {

        List<JSONObject> resultList = new ArrayList<>(); // 반환할 결과 리스트
        StringTokenizer st; // 버퍼를 읽어서 각 단어를 토큰화하는 클래스. tab, 띄어쓰기로 단어 구분.
        StringBuilder status = new StringBuilder(); // 여러 단어를 하나로 묶기 위해 필요한 빌더 클래스.
        // String을 매번 +로 더하는 것보다 빌더의 버퍼를 이용하는 게 성능적으로 좋음.

        br.readLine();  // 쉘 출력 결과의 1행(칼럼명)은 필요없기 때문에 다음 줄로 이동.
        while (true) {
            st = new StringTokenizer(br.readLine());    // 한 줄 읽어오기
            if (!st.hasMoreTokens()) {  // 읽어온 줄에 토큰이 없으면(== null) 파일의 끝이므로 탈출.
                break;
            }
            JSONObject obj = new JSONObject();  // 리스트에 새로 추가할 객체

            obj.put("names", st.nextToken());   // 첫 번째 토큰(단어)을 "NAMES" 칼럼의 내용으로 저장
            while (st.hasMoreTokens()) {    // 2번째 이후 단어들은 "STATUS" 칼럼의 내용
                // 띄어쓰기 때문에 토큰별로 나뉜 단어들을 다시 하나의 String으로 합친다.
                status.append(st.nextToken()).append(" ");
            }
            obj.put("status", status.toString()); // 합친 String을 STATUS 칼럼 내용으로 저장

            resultList.add(obj);
        }

        br.close(); // 사용 끝난 버퍼 닫기

        return resultList;
    }

}
