package com.develokit.maeum_ieum.domain.report;

import com.develokit.maeum_ieum.dto.report.RespDto;
import com.develokit.maeum_ieum.service.report.WeeklyReportAnalysisService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.develokit.maeum_ieum.dto.report.RespDto.*;
import static com.develokit.maeum_ieum.service.report.WeeklyReportAnalysisService.*;
import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    String response = "### HealthStatusIndicator: GOOD\n" +
            "**이유:** 노인은 건강 문제를 언급하지 않았으며, 그림 그리기 같은 활동을 하려고 합니다.\n" +
            "\n" +
            "### ActivityLevelIndicator: GOOD\n" +
            "**이유:** 노인이 새로운 주제를 시도하면서 그림 그리기를 계획하고 있어 활동적입니다.\n" +
            "\n" +
            "### CognitiveFunctionIndicator: GOOD\n" +
            "**이유:** 노인이 창의적 활동을 고려하고, 새로운 주제에 대해 논리적으로 고민하며 대화를 이어갔습니다.\n" +
            "\n" +
            "### LifeSatisfactionIndicator: GOOD\n" +
            "**이유:** 노인은 국화를 그리는 계획을 세우면서 긍정적인 반응을 보였고, 생활에 만족감을 드러냈습니다.\n" +
            "\n" +
            "### PsychologicalStabilityIndicator: GOOD\n" +
            "**이유:** 노인은 불안이나 스트레스를 나타내지 않고, 안정된 감정 상태에서 편안한 대화를 이어갔습니다.\n" +
            "\n" +
            "### SocialConnectivityIndicator: GOOD\n" +
            "**이유:** 노인은 AI와의 대화를 통해 정서적 만족을 얻었고, 긍정적인 사회적 상호작용을 보였습니다.\n" +
            "\n" +
            "### SupportNeedsIndicator: EXCELLENT\n" +
            "**이유:** 노인은 독립적으로 활동 계획을 세우고 실행할 수 있으며, 외부 지원이 필요하지 않아 보입니다.\n" +
            "\n" +
            "이를 바탕으로 노인의 상태를 종합적으로 이해하고 필요한 지원 여부를 결정하는 데 도움이 될 수 있습니다.\n";

    @Test
    void 파싱테스트(){
        Map<String, IndicatorResult> resultMap = new HashMap<>();

        // 각 섹션을 "**이유:**"를 기준으로 분리
        String[] sections = response.split("\\n\\n");

        for (String section : sections) {
            if (section.contains("**")) {
                // 지표와 값을 추출하는 부분 (지표명과 값을 ":" 기준으로 분리)
                String[] indicatorAndReason = section.split("\\*\\*이유:\\*\\*");
                if (indicatorAndReason.length == 2) {
                    String indicatorPart = indicatorAndReason[0].trim(); // 지표명과 값
                    String reason = indicatorAndReason[1].trim(); // 이유

                    // "###" 제거 및 지표명과 값을 분리 (예: "### HealthStatusIndicator: GOOD")
                    String[] indicatorInfo = indicatorPart.split(":");
                    if (indicatorInfo.length == 2) {
                        String indicator = indicatorInfo[0].replace("###", "").trim(); // 지표명
                        String value = indicatorInfo[1].trim(); // 값

                        // 지표명과 값이 제대로 분리된 상태로 저장
                        System.out.println("indicator = " + indicator);
                        System.out.println("value = " + value);
                        System.out.println("reason = " + reason);
                        System.out.println();
                        resultMap.put(indicator, new IndicatorResult(value, reason));
                    }
                }
            }
        }

//        for (String s : resultMap.keySet()) {
//            System.out.println(s +": "+resultMap.get(s).getReason());
//        }
    }

    @Test
    void 올바르게파싱되는경우() {
        String quantitativeAnalysis = "{\"healthStatusIndicator\":\"유우시쿤 건강상태 초 사이코🤍\",\"activityLevelIndicator\":\"유우시쿤 활동량 초 타카이🤍 일일 평균 걸음 수: 15,000보\",\"cognitiveFunctionIndicator\":\"인지 기능 테스트 점수: 25/30, 일상생활 수행 능력 양호\",\"lifeSatisfactionIndicator\":\"주관적 행복도 점수: 4/10, 유우시쿤 개선이 필요행ㅠㅠ!!\",\"psychologicalStabilityIndicator\":\"우울증 선별 검사 점수: 15/20, 전문가 상담 권장\",\"socialConnectivityIndicator\":\"주간 사회활동 참여 횟수: 4회, 사회적 관계 만족도 높음\",\"supportNeedsIndicator\":\"일상생활 지원 필요도: 중간, 유우시군 주 2회 방문 요양 서비스 권장🤍\"}";
        Gson gson = new Gson();

        QuantitativeAnalysis response
                = gson.fromJson(quantitativeAnalysis, QuantitativeAnalysis.class);

        System.out.println("response = " + response);
    }
}