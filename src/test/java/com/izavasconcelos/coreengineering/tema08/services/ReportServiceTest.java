package com.izavasconcelos.coreengineering.tema08.services;

import org.junit.Test;

public class ReportServiceTest {
    ReportService reportService = new ReportService();
    @Test
    public void loansReportTest() {
        String loans = reportService.loansReport();
        System.out.println(loans);
    }
    @Test
    public void topTenUsersTest() {
        String top = reportService.topTenUsersReport();
        System.out.println(top);
    }
    @Test
    public void overdueReportTest() {
        String overdue = reportService.overdueUserReport();
        System.out.println(overdue);
    }
}
