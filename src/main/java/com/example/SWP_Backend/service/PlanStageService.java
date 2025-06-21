package com.example.SWP_Backend.service;

import com.example.SWP_Backend.entity.PlanStage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanStageService {

    /**
     * Load plan stages from an Excel file based on the user's plan intensity and max duration.
     * @param filePath file path to the Excel file
     * @param mucDoKeHoach intensity level of the plan ("Nhẹ", "Nặng", etc.)
     * @param soNgayToiDa maximum number of days allowed
     * @return list of matching PlanStage entities
     * @throws IOException if file cannot be read
     */
    public List<PlanStage> loadStagesForUser(String filePath, String mucDoKeHoach, int soNgayToiDa) throws IOException {
        List<PlanStage> stages = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath); Workbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String mucDo = getString(row.getCell(0));
                int soNgay = parseSoNgay(getString(row.getCell(1)));

                // Nếu giai đoạn không tồn tại thì bỏ qua dòng
                Cell giaiDoanCell = row.getCell(2);
                if (giaiDoanCell == null || giaiDoanCell.getCellType() != CellType.NUMERIC) {
                    continue;
                }
                int giaiDoan = (int) giaiDoanCell.getNumericCellValue();

                if (mucDo.equalsIgnoreCase(mucDoKeHoach) && soNgay <= soNgayToiDa) {
                    String mucTieu = getString(row.getCell(4));
                    StringBuilder hoatDongBuilder = new StringBuilder();

                    for (int col = 5; col <= 9; col++) {
                        String val = getString(row.getCell(col));
                        if (!val.isEmpty()) {
                            hoatDongBuilder.append("- ").append(val).append("\n");
                        }
                    }

                    PlanStage stage = new PlanStage();
                    stage.setStageName("Giai đoạn " + giaiDoan);
                    stage.setDescription(mucTieu + "\n" + hoatDongBuilder);
                    stage.setTargetDurationDays(soNgay);
                    stage.setSequenceOrder(giaiDoan);

                    stages.add(stage);
                }
            }
        }
        return stages;
    }


    /**
     * Parse the "Số ngày" column (e.g. "10 ngày, 5 ngày") and sum up all numbers.
     */
    private int parseSoNgay(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return 0;
        }
        int total = 0;
        String[] parts = raw.split(",");
        for (String part : parts) {
            String numStr = part.replaceAll("[^\\d]", ""); // Extract numbers only
            if (!numStr.isEmpty()) {
                total += Integer.parseInt(numStr);
            }
        }
        return total;
    }

    /**
     * Get cell's string value safely.
     */
    private String getString(Cell cell) {
        return cell == null ? "" : cell.toString().trim();
    }
}
