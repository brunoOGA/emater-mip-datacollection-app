package br.edu.utfpr.cp.emater.midmipsystem.view.analysis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@Getter
@NoArgsConstructor
@RequestScope
public class ViewUtilities {

    @Value("${mip.app.current-version}")
    private String currentVersion;

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);

        var firstRow = sheet.getFirstRowNum();
        var row = sheet.createRow(firstRow);

        row.createCell(0).setCellValue("Unidade de Referência");
        row.createCell(1).setCellValue("Município");
        row.createCell(2).setCellValue("Data de Emergência");
        row.createCell(3).setCellValue("No. Amostras MIP");
        row.createCell(4).setCellValue("1a. Amostra MIP");
        row.createCell(5).setCellValue("Última Amostra MIP");
        row.createCell(6).setCellValue("No. Aplicações MIP (Total)");
        row.createCell(7).setCellValue("No. Aplicações MIP (Biológico Apenas)");

        row.createCell(8).setCellValue("No. Amostras MID");
        row.createCell(9).setCellValue("Presença de Esporos");
        row.createCell(10).setCellValue("1a. Amostra MID");
        row.createCell(11).setCellValue("Última Amostra MID");
        row.createCell(12).setCellValue("No. Aplicações MID (Fungicida)");
    }

}
