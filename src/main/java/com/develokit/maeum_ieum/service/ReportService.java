package com.develokit.maeum_ieum.service;

import com.develokit.maeum_ieum.domain.report.Report;
import com.develokit.maeum_ieum.domain.report.ReportRepository;
import com.develokit.maeum_ieum.domain.report.ReportStatus;
import com.develokit.maeum_ieum.domain.report.ReportType;
import com.develokit.maeum_ieum.domain.user.elderly.Elderly;
import com.develokit.maeum_ieum.domain.user.elderly.ElderlyRepository;
import com.develokit.maeum_ieum.util.CustomUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ElderlyRepository elderlyRepository;
    private final Logger log = LoggerFactory.getLogger(ReportService.class);

    @Scheduled(cron = "0 0 0 * * *") //매일 해당 요일에 보고서 생성을 지정한 사용자의 주간 보고서 생성
    @Transactional
    public void generateWeeklyReport(){
        //해당 요일에 발행되도록 지정된 대기 상태의 보고서들 가져오기
        LocalDateTime today = LocalDateTime.now();
        List<Report> reportsToGenerate = reportRepository.findByReportDayAndReportStatus(today.getDayOfWeek(), ReportStatus.PENDING);

        for (Report report : reportsToGenerate) {
            //보고서 생성 시작일로부터 일주일이 됐다면
            if(ChronoUnit.DAYS.between(report.getStartDate(), today) == 7){
                try {
                    //보고서 상태 변경: 대기 -> 분석 진행 중
                    report.updateReportStatus(ReportStatus.PROCESSING);

                    //분석 시작
                    generateReportContent(report);

                    //보고서 상태 변경: 분석 진행 중 -> 분석 완료
                    report.updateReportStatus(ReportStatus.COMPLETED);
                    report.updateEndDate(today);
                }catch (Exception e){
                    log.error("보고서 분석 중 오류 발생: {elderlyId : "+report.getElderly().getId() + "} " + e);
                    report.updateReportStatus(ReportStatus.ERROR);
                }
                // 각 반복마다 변경사항을 명시적으로 저장 ->  대량 처리 시 메모리 관리에 도움
                reportRepository.save(report);
            }
        }



        LocalDateTime[] weekStartAndEnd = CustomUtil.getWeekStartAndEnd(LocalDateTime.now());
        LocalDateTime startDate = weekStartAndEnd[0];
        LocalDateTime endDate = weekStartAndEnd[1];

        //해당 요일에 보고서 생성되도록 지정된 노인 사용자 찾기
        List<Elderly> elderlyList = elderlyRepository.findByReportDay(today);
        List<Report> reportList = new ArrayList<>();

        //보고서 생성 안됐는지 검증하고 보고서 생성하기
        for (Elderly elderly : elderlyList) {
            if(!reportExistsForWeek(elderly, startDate)){
                reportList.add(new Report(elderly, startDate, endDate, ReportType.WEEKLY));
            }
        }
        //생성된 보고서 저장
        if(!reportList.isEmpty()){
            reportRepository.saveAll(reportList);
            log.debug(today+" 주간 보고서 생성 완료");
        }
    }

    //해당 주의 보고서가 이미 존재하면 true 반환
    private boolean reportExistsForWeek(Elderly elderly, LocalDateTime startDate){
        return reportRepository.findByStartDate(elderly, startDate) > 0;
    }

    //지표에 따른 보고서 생성하기
    private void generateReportContent(Report report){


    }



    //보고서 지정된 요일에 주간 보고서 자동 생성 -> 그 요일이 되는 자정에 생성하도록






    //보고서 지정된 요일에 월간 보고서 자동 생성


}
