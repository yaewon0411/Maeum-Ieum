package com.develokit.maeum_ieum.dto.assistant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ReqDto {

    @Getter
    @NoArgsConstructor
    @Schema(description = "AI 어시스턴트 생성 요청을 위한 DTO")
    public static class CreateAssistantReqDto{

        @Nullable
        @Schema(description = "AI 이름: 최소 1자에서 최대 256자")
        @Size(min = 1, max = 256, message = "최소 1자에서 최대 256자까지 입력해야 합니다")
        private String name;

        @Nullable
        @Schema(description = "AI 규칙(필수): 최소 1자에서 최대 512자")
        @Size(min = 1, max = 512, message = "AI 규칙은 필수 설정입니다")
        private String description; //AI 필수 규칙 설정

        @Nullable
        @Schema(description = "AI 규칙(선택): 대화 주제")
        private String conversationTopic; //대화 주제 -> description
        @Nullable
        @Schema(description = "AI 규칙(선택): 응답 형식")
        private String responseType; //응답 형식
        @Nullable
        @Schema(description = "AI 규칙(선택): 성격")
        private String personality; //성격
        @Nullable
        @Schema(description = "AI 규칙(선택): 금기 주제")
        private String forbiddenTopic; //금기 주제
    }

}