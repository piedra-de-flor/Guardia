package com.capstone.capstonedesign.service;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import com.capstone.capstonedesign.service.support.ImageToFloatArrayConverter;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class ONNXService {
    private OrtSession session;
    private OrtEnvironment env;

    public ONNXService() {
        try {
            // ONNX 파일의 경로
            Path modelPath = Paths.get("C:\\Users\\USER\\Desktop\\공부\\capstone-design\\src\\main\\resources\\best.onnx");

            // ONNX 런타임 환경 설정
            env = OrtEnvironment.getEnvironment();

            // ONNX 세션 생성
            session = env.createSession(modelPath.toString());
            System.out.println("onnx 파일 연동 성공");
        } catch (OrtException e) {
            e.printStackTrace();
        }
    }

    // ONNX 모델 실행
    public void predict() {
        String imagePath = "C:\\Users\\USER\\Desktop\\공부\\capstone-design\\src\\main\\resources\\onnxTest.jpg";

        // 이미지를 float[][] 배열로 변환
        float[][][][] inputData = ImageToFloatArrayConverter.convertImageToFloatArray(imagePath);
        System.out.println(inputData.length + "," + inputData[0].length + "," + inputData[0][0].length + "," + inputData[0][0][0].length);
        try {
            // 입력 데이터를 OnnxTensor로 변환
            OnnxTensor inputTensor = OnnxTensor.createTensor(env, inputData);

            // 입력 데이터 매핑
            Map<String, OnnxTensor> inputs = new HashMap<>();
            inputs.put("images", inputTensor); // input_node_name은 모델의 입력 노드 이름

            // 모델 실행 및 결과 획득
            try (OrtSession.Result results = session.run(inputs)) {
                // 결과 처리
                // 결과 획득 후 작업 수행
                for (int i = 0; i < results.size(); i++) {
                    System.out.println(results.get(i));
                }
                // resultMap을 사용하여 결과 처리
            }

        } catch (OrtException e) {
            e.printStackTrace();
        }
    }

    // ONNX 세션 닫기
    public void closeSession() {
        try {
            session.close();
        } catch (OrtException e) {
            e.printStackTrace();
        }
    }
}