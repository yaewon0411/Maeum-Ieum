package com.develokit.maeum_ieum.domain.report.indicator;

import lombok.Getter;

//건강 상태 지표
@Getter
public enum HealthStatusIndicator implements ReportIndicator{

    VERY_POOR("정말 별로예요"),
    POOR("별로예요"),
    FAIR("그냥 그래요"),
    GOOD("좋아요"),
    EXCELLENT("아주 좋아요");

    private final String description;




    HealthStatusIndicator(String description) {
        this.description = description;
    }

    @Override
    public String getFieldName() {
        return "healthStatusIndicator";
    }
}
