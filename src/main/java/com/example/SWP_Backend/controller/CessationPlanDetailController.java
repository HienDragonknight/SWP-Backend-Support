package com.example.SWP_Backend.controller;


import com.example.SWP_Backend.entity.CessationPlan;
import com.example.SWP_Backend.entity.CessationPlanDetail;
import com.example.SWP_Backend.repository.CessationPlanRepository;
import com.example.SWP_Backend.service.CessationPlanDetailService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/plan-details")
public class CessationPlanDetailController {

    private final CessationPlanDetailService detailService;
    private final CessationPlanRepository planRepository;

    public CessationPlanDetailController(CessationPlanDetailService detailService,
                                         CessationPlanRepository planRepository) {
        this.detailService = detailService;
        this.planRepository = planRepository;
    }

    @PostMapping("/import/{planId}")
    public List<CessationPlanDetail> importExcel(
            @PathVariable Long planId,
            @RequestParam("file") MultipartFile file) throws Exception {

        CessationPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        List<CessationPlanDetail> details = new ArrayList<>();
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua header

                CessationPlanDetail detail = new CessationPlanDetail();
                detail.setPlan(plan);
                detail.setDay((int) row.getCell(2).getNumericCellValue()); // cột ngày
                detail.setGoal(row.getCell(4).getStringCellValue());
                detail.setActivity1(row.getCell(5).getStringCellValue());
                detail.setActivity2(row.getCell(6).getStringCellValue());
                detail.setActivity3(row.getCell(7).getStringCellValue());
                detail.setActivity4(row.getCell(8).getStringCellValue());
                detail.setActivity5(row.getCell(9).getStringCellValue());
                details.add(detail);
            }
        }
        return detailService.saveAll(details);
    }

    @GetMapping("/{planId}")
    public List<CessationPlanDetail> getDetailsByPlanId(@PathVariable Long planId) {
        return detailService.getDetailsByPlanId(planId);
    }
}

