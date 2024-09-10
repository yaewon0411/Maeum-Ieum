package com.develokit.maeum_ieum.controller;

import com.develokit.maeum_ieum.dto.assistant.ReqDto;
import com.develokit.maeum_ieum.dto.caregiver.RespDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.develokit.maeum_ieum.dto.assistant.ReqDto.*;
import static com.develokit.maeum_ieum.dto.assistant.RespDto.*;

@Tag(name = "노인 사용자 API", description = "노인 사용자가 호출하는 API 목록")
public interface ElderlyControllerDocs {

    @Operation(summary = "노인 로그인(전달 받은 코드 입력)", description = "요양사에게 전달 받은 코드 입력할 때 사용")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "접속 성공", content = @Content(schema = @Schema(implementation = VerifyAccessCodeRespDto.class), mediaType = "application/json")),
    })
    ResponseEntity<?> verifyAccessCode(@RequestBody VerifyAccessCodeReqDto verifyAccessCodeReqDto);
}
