package com.banking.cards.controller;

import com.banking.cards.constants.CardsConstants;
import com.banking.cards.dto.CardDto;
import com.banking.cards.dto.CardsContactInfoDto;
import com.banking.cards.dto.ErrorResponseDto;
import com.banking.cards.dto.ResponseDto;
import com.banking.cards.service.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Cards Rest API",
        description = "APIs to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping("/api")
@Validated
public class CardsController {

    private final ICardService cardService;
    private final Environment environment;
    private final CardsContactInfoDto cardsContactInfoDto;
    private final String buildInfo;

    public CardsController(ICardService cardService, Environment environment, CardsContactInfoDto cardsContactInfoDto,  @Value("${build.info}") String buildInfo) {
        this.cardService = cardService;
        this.environment = environment;
        this.cardsContactInfoDto = cardsContactInfoDto;
        this.buildInfo = buildInfo;
    }

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Response on successful creation of card"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Response if there is a card for the mobile number already exist",
                    content = @Content(schema =
                    @Schema(implementation = ErrorResponseDto.class))
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> create(@Valid @RequestParam String mobileNumber) {
        cardService.createCard(mobileNumber);
        ResponseDto response = new ResponseDto("Card Successfully Created", HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Response when successfully fetched data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Response if a card not found for the mobile number",
                    content = @Content(schema =
                    @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardDto> fetch(@RequestParam String mobileNumber) {
        CardDto cardDto = cardService.fetchCardDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardDto);
    }

    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardDto cardsDto) {
        boolean isUpdated = cardService.updateCard(cardsDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.MESSAGE_200, HttpStatus.ACCEPTED));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.MESSAGE_417_UPDATE, HttpStatus.CONFLICT));
        }
    }

    @Operation(
            summary = "Delete Card Details REST API",
            description = "REST API to delete Card details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam
                                                         @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        boolean isDeleted = cardService.deleteCard(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, HttpStatus.OK));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, HttpStatus.EXPECTATION_FAILED));
        }
    }


    @Operation(summary = "Fetch Build Info Rest API")
    @ApiResponse(responseCode = "200")
    @GetMapping("/build-info")
    public ResponseEntity<String> buildInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(buildInfo);
    }

    @Operation(summary = "Fetch Java Home Info  Rest API")
    @ApiResponse(responseCode = "200")
    @GetMapping("/java-info")
    public ResponseEntity<String> javaInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(summary = "Fetch Contact Info Rest API")
    @ApiResponse(responseCode = "200")
    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> contactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(cardsContactInfoDto);
    }
}
