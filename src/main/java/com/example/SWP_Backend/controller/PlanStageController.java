package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.dto.DayPlanDTO;
import com.example.SWP_Backend.dto.SmokingInfoHistoryRequest;
import com.example.SWP_Backend.service.PlanStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/stages")
public class PlanStageController {

    private final PlanStageService planStageService;

    @Autowired
    public PlanStageController(PlanStageService planStageService) {
        this.planStageService = planStageService;
    }

    /**
     * API nhận thông tin lịch sử hút thuốc từ phía FE,
     * Tính mức độ nặng/nhẹ/trung bình ở BE,
     * Sau đó sinh kế hoạch từng ngày cho người dùng theo mức độ và số ngày.
     *
     * @param request DTO chứa số năm hút thuốc, số điếu mỗi ngày, số ngày muốn lập kế hoạch.
     * @return Danh sách kế hoạch từng ngày phù hợp mức độ.
     */
    @PostMapping("/generate")
    public List<DayPlanDTO> generateStages(@RequestBody SmokingInfoHistoryRequest request) throws IOException {
        // Lấy thông tin từ request
        int years = request.getYears();
        int cigarettesPerDay = request.getCigarettesPerDay();
        int soNgay = request.getSoNgay();

        // Tính mức độ (nặng/trung bình/nhẹ) bằng method đã viết trong PlanStageService
        String mucDoKeHoach = planStageService.tinhMucDo(years, cigarettesPerDay);

        // Gọi tiếp service sinh kế hoạch từng ngày theo mức độ đã tính được
        return planStageService.loadDaysForUser(
                "src/main/resources/ke_hoach_cai_thuoc_chi_tiet.xlsx",
                mucDoKeHoach,
                soNgay
        );
    }
}
