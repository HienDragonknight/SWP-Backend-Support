package com.example.SWP_Backend.service;

import com.example.SWP_Backend.dto.DayPlanDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanStageService {

    /// Sau đây là phần tính mức độ
    public static String tinhMucDo(int years, int cigarettesPerDay) {
        double packYear = (cigarettesPerDay / 20.0) * years;
        if (packYear < 5) return "Nhẹ";
        else if (packYear < 20) return "Trung bình";
        else return "Nặng";
    }

    /**
     * Đọc từng dòng từ file Excel và trả về từng ngày (DayPlanDTO).
     * Có log debug để kiểm tra từng bước khi đọc file.
     * @param filePath        Đường dẫn file Excel
     * @param mucDoKeHoach    Mức độ kế hoạch ("Nhẹ", "Nặng", ...)
     * @param soNgayToiDa     Tổng số ngày mục tiêu của user (10, 20, 30)
     * @return                Danh sách kế hoạch từng ngày cho user
     * @throws IOException    Nếu file không đọc được
     */
    public List<DayPlanDTO> loadDaysForUser(String filePath, String mucDoKeHoach, int soNgayToiDa) throws IOException {
        List<DayPlanDTO> days = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath); Workbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheetAt(0);

            // Duyệt từng dòng từ dòng 2 (i = 1), dòng 1 là header
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    System.out.println("Row " + i + " null");
                    continue;
                }

                // ----- Đọc các trường từ từng cột -----
                // Cột A: Mức độ (Nhẹ/Nặng)
                String mucDo = getString(row.getCell(0)).trim();

                // Cột B: Số ngày (ví dụ: "10 ngày")
                int soNgay = parseSoNgay(getString(row.getCell(1)).trim());

                // Cột C: Ngày thứ mấy (vấn đề ở đây - có thể là "1.0", "2.0", ...)
                int day = 0;
                Cell dayCell = row.getCell(2);
                if (dayCell != null) {
                    if (dayCell.getCellType() == CellType.NUMERIC) {
                        // Đọc trực tiếp nếu là số (kiểu double → ép về int)
                        day = (int) dayCell.getNumericCellValue();
                    } else {
                        // Nếu là text, thử convert sang double rồi ép về int (chấp nhận "1.0")
                        try {
                            day = (int) Double.parseDouble(dayCell.toString().trim());
                        } catch (Exception e) {
                            System.out.println("Row " + i + " - Lỗi đọc số ngày (day): '" + dayCell.toString().trim() + "'");
                            continue;
                        }
                    }
                } else {
                    System.out.println("Row " + i + " - Không có cell ngày");
                    continue;
                }

                // Cột D: Giai đoạn (phải là số, kiểu numeric)
                Cell giaiDoanCell = row.getCell(3);
                int giaiDoan;
                if (giaiDoanCell != null && giaiDoanCell.getCellType() == CellType.NUMERIC) {
                    giaiDoan = (int) giaiDoanCell.getNumericCellValue();
                } else {
                    System.out.println("Row " + i + " - Lỗi đọc giai đoạn");
                    continue;
                }

                // ----- Log giá trị đọc được cho debug -----
                System.out.println("mucDo = [" + mucDo + "], soNgay = " + soNgay + ", day = " + day + ", giaiDoan = " + giaiDoan);

                // Điều kiện: đúng mức độ và số ngày yêu cầu
                if (mucDo.equalsIgnoreCase(mucDoKeHoach) && soNgay <= soNgayToiDa) {
                    // Cột E: Mục tiêu của ngày
                    String mucTieu = getString(row.getCell(4)).trim();

                    // Các cột F-J: Hoạt động (5 hoạt động/ngày)
                    List<String> hoatDong = new ArrayList<>();
                    for (int col = 5; col <= 9; col++) {
                        String val = getString(row.getCell(col)).trim();
                        if (!val.isEmpty()) hoatDong.add(val);
                    }

                    // Tạo DTO từng ngày
                    DayPlanDTO dayDTO = new DayPlanDTO();
                    dayDTO.setMucDoKeHoach(mucDoKeHoach);
                    dayDTO.setDay(day);
                    dayDTO.setStageOrder(giaiDoan);
                    dayDTO.setStageName("Giai đoạn " + giaiDoan); // FE có thể tự map tên nếu muốn
                    dayDTO.setGoal(mucTieu);
                    dayDTO.setActivities(hoatDong);
                    days.add(dayDTO);
                }
            }
        }
        return days;
    }

    /**
     * Parse số ngày từ chuỗi kiểu "10 ngày", "20 ngày"...
     * Trả về tổng số (nếu là "10 ngày, 5 ngày" thì trả về 15).
     */
    private int parseSoNgay(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return 0;
        }
        int total = 0;
        String[] parts = raw.split(",");
        for (String part : parts) {
            String numStr = part.replaceAll("[^\\d]", "");
            if (!numStr.isEmpty()) {
                total += Integer.parseInt(numStr);
            }
        }
        return total;
    }

    /**
     * Đọc giá trị cell an toàn, tránh lỗi null pointer.
     */
    private String getString(Cell cell) {
        return cell == null ? "" : cell.toString().trim();
    }
}
