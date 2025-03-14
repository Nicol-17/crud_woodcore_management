package com.crud.crudProyecto.service;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {
    private final DataSource dataSource;

    public ReportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public byte[] generarReport(String reportName) throws Exception {
        // Cargar el reporte
        InputStream reportStream = this.getClass().getResourceAsStream("/reports/" + reportName + ".jasper");

        if (reportStream == null) {
            throw new RuntimeException("No se encontró el reporte: " + reportName);
        }

        // Inicializar los parámetros
        Map<String, Object> parms = new HashMap<>();
        parms.put("titulo", "Mi Reporte");

        // Obtener conexión y generar el reporte
        try (Connection conn = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parms, conn);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }
}
