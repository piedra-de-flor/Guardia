package com.capstone.capstonedesign.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class PythonRunner {
    public void runPythonScript(List<String> imageFiles) {
        try {
            // Python 스크립트 실행 명령어
            String[] command = {"python", "C:\\Users\\USER\\Desktop\\공부\\server_connect\\inference.py"};

            // 프로세스 빌더 생성
            ProcessBuilder pb = new ProcessBuilder(command);

            // 작업 디렉토리 설정 (선택사항)
            // pb.directory(new File("path/to/working/directory"));

            // 이미지 파일 이름을 명령어에 추가
            for (String imageFile : imageFiles) {
                pb.command().add(imageFile);
            }

            // 환경변수 설정 (선택사항)
            // Map<String, String> env = pb.environment();
            // env.put("key", "value");

            // 프로세스 시작
            Process process = pb.start();

            // 프로세스의 출력을 읽어오기 위한 버퍼드 리더 생성
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // 프로세스 출력 읽기
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 프로세스 종료 대기
            int exitCode = process.waitFor();
            System.out.println("Exited with error code " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
