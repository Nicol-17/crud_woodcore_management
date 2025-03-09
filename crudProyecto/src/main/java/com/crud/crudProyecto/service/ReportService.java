package com.crud.crudProyecto.service;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@Service
public class ReportService {
    private final DataSource dataSource;

    public ReportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public byte [] generarReport(String reportName) throws Exception {
        //Cargar reporte
        InputStream reportStream = this.getClass().getResourceAsStream("/reports/"+reportName+".jasper");
        Map<String, Object> parms = null;
        //llenado
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream,parms , dataSource.getConnection());

        //Exporta a un report de tipo PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
