package com.example.csws.controller.lecture;

import com.example.csws.entity.lecture.CreateLectureRequest;
import com.example.csws.service.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {
    private final LectureService lectureService;


    @GetMapping
    public JSONObject getLectureList(@RequestParam(value = "departmentId") Long departmentId) {
        JSONObject obj = new JSONObject();
        return obj;
    }

    @PostMapping
    public JSONObject createLecture(@RequestBody CreateLectureRequest createLectureRequest) {
        lectureService.createLecture(createLectureRequest);

        // 저장된 도메인 명 응답
        JSONObject obj = new JSONObject();
        obj.put("success", true);
        return obj;
    }
}
