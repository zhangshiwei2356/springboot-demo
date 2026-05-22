package com.demo.business.support;

import com.demo.business.entity.ArchiveRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 档案元数据导出为 Excel（.xlsx）。
 */
public final class ArchiveExcelExporter {

    private static final String[] HEADERS = {
            "档案ID", "标题", "分类", "描述", "文件类型", "附件文件名", "附件大小(字节)", "创建时间", "更新时间"
    };

    private ArchiveExcelExporter() {
    }

    public static byte[] toExcelBytes(ArchiveRecord record) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("档案信息");
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                headerRow.createCell(i).setCellValue(HEADERS[i]);
            }
            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue(record.getId() != null ? record.getId() : 0L);
            dataRow.createCell(1).setCellValue(nullToEmpty(record.getTitle()));
            dataRow.createCell(2).setCellValue(nullToEmpty(record.getCategory()));
            dataRow.createCell(3).setCellValue(nullToEmpty(record.getDescription()));
            dataRow.createCell(4).setCellValue(nullToEmpty(record.getFileType()));
            dataRow.createCell(5).setCellValue(nullToEmpty(record.getOriginalFileName()));
            if (record.getFileSize() != null) {
                dataRow.createCell(6).setCellValue(record.getFileSize());
            } else {
                dataRow.createCell(6).setCellValue("");
            }
            dataRow.createCell(7).setCellValue(nullToEmpty(record.getCreateTime()));
            dataRow.createCell(8).setCellValue(nullToEmpty(record.getUpdateTime()));
            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(out);
            return out.toByteArray();
        }
    }

    private static String nullToEmpty(String value) {
        return value != null ? value : "";
    }
}
