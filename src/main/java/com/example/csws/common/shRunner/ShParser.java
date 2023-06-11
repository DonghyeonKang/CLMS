package com.example.csws.common.shRunner;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
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

    public ParserResponseDto checkContainerResource(String resultStr) throws IOException {
        ParserResponseDto responseDto = new ParserResponseDto(); // 쉘 실행 결과 및 JSON 리스트 반환 클래스

        if (resultStr == null) {
            System.out.println("Input string cannot be null");
        }

        if (!isSuccess(resultStr)) { // 쉘 실행에 실패한 경우 결과에 false 입력 후 반환
            responseDto.success = false;
            return responseDto;
        } else { // 실행 성공시 true 입력 후 파싱 진행.
            responseDto.success = true;
        }

        responseDto.resultList = new ArrayList<>(); // 반환할 결과 리스트 선언
        BufferedReader br = new BufferedReader(new StringReader(resultStr)); // 출력 결과 문자열을 버퍼에 담기
        String line; // 한 줄씩 읽어올 변수
        int data = 1; // 구분선에 따라 json에 담는 데이터 형식 구분

        br.readLine(); // 쉘 출력 결과의 1행은 쉘 실행 시작 알림이기 때문에 생략
        br.readLine(); // 그 다음 행도 쓸모없는 행(칼럼 이름)이기 때문에 생략

        while (true) {
            line = br.readLine();
            if (line == null) { // 읽어온 줄이 null이면 출력의 끝이므로 탈출
                break;
            } else if (line.contains("===")) { // 구분선을 포함한 줄은 생략
                data++; // 구분선 기준으로 정보가 바뀔 때마다 1 증가.
                br.readLine(); // 구분선 변경 직후의 행은 쓸모없는 행(칼럼 이름)이기 때문에 생략
                continue;
            }

            JSONObject obj = new JSONObject();  // 리스트에 새로 추가할 객체

            switch (data) { // 1 메모리 / 2 디스크 / 3 CPU 정보에 따라 json에 담는 정보 구분
                case 1:
                    // 1번째 단어 : 메모리 사용량 / 3번째 단어 : 메모리 제한(단위 포함)
                    String[] words = line.split(" "); // 띄어쓰기 기준으로 단어 구분
                    obj.put("MEM USAGE", words[0]);
                    obj.put("LIMIT", words[2]);
                    responseDto.getResultList().add(obj);
                    break;
                case 2:
                    obj.put("Disk Usage", line);
                    responseDto.getResultList().add(obj);
                    break;
                case 3:
                    obj.put("CPU Usage(%)", line);
                    responseDto.getResultList().add(obj);
                    break;
            }
        }

        br.close(); // 사용 끝난 버퍼 닫기
        return responseDto; // 출력 결과 및 json 리스트가 담긴 dto 객체 반환
    }

    public ParserResponseDto checkServerResource(String resultStr) throws IOException {
        ParserResponseDto responseDto = new ParserResponseDto(); // 쉘 실행 결과 및 JSON 리스트 반환 클래스

        if (!isSuccess(resultStr)) { // 쉘 실행에 실패한 경우 결과에 false 입력 후 반환
            responseDto.success = false;
            return responseDto;
        } else { // 실행 성공시 true 입력 후 파싱 진행.
            responseDto.success = true;
        }

        responseDto.resultList = new ArrayList<>(); // 반환할 결과 리스트 선언
        BufferedReader br = new BufferedReader(new StringReader(resultStr)); // 출력 결과 문자열을 버퍼에 담기
        StringTokenizer st; // 버퍼 내용을 각 단어별로 토큰화하는 클래스. " ", \n, \t으로 단어 구분.
        String line; // 한 줄씩 읽어올 변수
        int data = 0; // 구분선에 따라 json에 담는 데이터 형식 구분

        br.readLine();  // 쉘 출력 결과의 1행은 쉘 실행 시작 알림이기 때문에 생략
        
        while (true) {
            line = br.readLine();
            if (line == null) { // 읽어온 줄이 null이면 출력의 끝이므로 탈출
                break;
            } else if (line.contains("===")) { // 구분선을 포함한 줄은 생략
                data++; // 구분선 기준으로 정보가 바뀔 때마다 1 증가.
                if (data == 2) { // Disk 정보로 넘어간 직후, 쓸모없는 행(칼럼 이름) 생략.
                    br.readLine();
                }
                continue;
            }

            JSONObject obj = new JSONObject();  // 리스트에 새로 추가할 객체
            st = new StringTokenizer(line); // 문장을 단어별로 토큰화

            switch (data) { // 1 메모리 / 2 디스크 / 3 CPU 정보에 따라 json에 담는 정보 구분
                case 1:
                    // 1번째 토큰 : 메모리 정보 / 2, 3번째 토큰 : 메모리 사용량 + 단위(kb)
                    String dataName = st.nextToken().split(":")[0]; // 이름 끝에서 ":" 제거
                    obj.put(dataName, st.nextToken() + " " + st.nextToken());
                    responseDto.resultList.add(obj); // 반환할 리스트에 추가
                    break;
                case 2:
                    obj.put("Filesystem", st.nextToken());
                    obj.put("Size", st.nextToken());
                    obj.put("Used", st.nextToken());
                    obj.put("Avail", st.nextToken());
                    obj.put("Use%", st.nextToken());
                    obj.put("Mounted on", st.nextToken());
                    responseDto.resultList.add(obj);
                    break;
                case 3:
                    obj.put("CPU", st.nextToken() + "%");
                    responseDto.resultList.add(obj);
                    break;
            }
        }

        br.close(); // 사용 끝난 버퍼 닫기
        return responseDto; // 출력 결과 및 json 리스트가 담긴 dto 객체 반환
    }

    public ParserResponseDto printStatusforManager(String resultStr) throws IOException {
        ParserResponseDto responseDto = new ParserResponseDto(); // 쉘 실행 결과 및 JSON 리스트 반환 클래스

        if (!isSuccess(resultStr)) { // 쉘 실행에 실패한 경우 결과에 false 입력 후 반환
            responseDto.success = false;
            return responseDto;
        } else { // 실행 성공시 true 입력 후 파싱 진행.
            responseDto.success = true;
        }

        responseDto.resultList = new ArrayList<>(); // 반환할 결과 리스트 선언
        StringBuilder status = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new StringReader(resultStr))) {
            StringTokenizer st;
            String line;

            br.readLine();
            br.readLine();

            while ((line = br.readLine()) != null) {
                st = new StringTokenizer(line);
                if (!st.hasMoreTokens()) {
                    break;
                }
                JSONObject obj = new JSONObject();
                obj.put("NAMES", st.nextToken());

                while (st.hasMoreTokens()) {
                    status.append(st.nextToken()).append(" ");
                }
                obj.put("STATUS", status.toString().trim());
                status.setLength(0);

                responseDto.resultList.add(obj);
            }
        } catch (IOException e) {
            responseDto.success = false;
        }

        return responseDto;
    }

    public ParserResponseDto printStatusforUser(String resultStr) throws IOException {
        ParserResponseDto responseDto = new ParserResponseDto();

        if (!isSuccess(resultStr)) {
            responseDto.success = false;
            return responseDto;
        } else { // 실행 성공시 true 입력 후 파싱 진행.
            responseDto.success = true;
        }

        responseDto.success = true;
        responseDto.resultList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new StringReader(resultStr))) {
            StringTokenizer st;
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                st = new StringTokenizer(line);

                if (!st.hasMoreTokens()) {
                    break;
                }

                JSONObject obj = new JSONObject();
                StringBuilder status = new StringBuilder();
                obj.put("NAMES", st.nextToken());

                while (st.hasMoreTokens()) {
                    status.append(st.nextToken()).append(" ");
                }
                obj.put("STATUS", status.toString().trim());

                responseDto.resultList.add(obj);
            }
        } catch (IOException e) {
            responseDto.success = false;
            // Optionally, log the error or handle it accordingly.
        }

        return responseDto;
    }


    // 테스트를 위한 임시 메서드. 파일을 읽어서 문자열로 반환.
    public String readResultFile(String filename) {
        BufferedReader br;

        String baseResultFilePath = "/C:/Users/GHL/info/pika"; // 임의의 기본 경로
        String resultFilePath = baseResultFilePath + filename;

        try {
            FileReader fileReader = new FileReader(resultFilePath);
            br = new BufferedReader(fileReader);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            br.close();
            System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }

    }

}
