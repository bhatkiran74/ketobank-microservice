package com.keto.cards.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.keto.cards.services.ICardsService;
import com.keto.cards.utils.dto.CardsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CardsController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CardsControllerTest {
    @Autowired
    private CardsController cardsController;

    @MockBean
    private ICardsService iCardsService;

    @Test
    void testCreateCard() throws Exception {
        // Arrange
        doNothing().when(iCardsService).createCard(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/cards/create")
                .param("mobileNumber", "foo");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(cardsController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"statusCode\":\"201\",\"statusMsg\":\"Card created successfully\"}"));
    }

    @Test
    void testFindCardsDetailsByMobileNumber() throws Exception {
        // Arrange
        CardsDto cardsDto = new CardsDto();
        cardsDto.setAmountUsed(10);
        cardsDto.setAvailableAmount(1);
        cardsDto.setCardNumber("42");
        cardsDto.setCardType("Card Type");
        cardsDto.setMobileNumber("42");
        cardsDto.setTotalLimit(1);
        when(iCardsService.findCardsDetailsByMobileNumber(Mockito.<String>any())).thenReturn(cardsDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cards/fetch")
                .param("mobileNumber", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cardsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"mobileNumber\":\"42\",\"cardNumber\":\"42\",\"cardType\":\"Card Type\",\"totalLimit\":1,\"amountUsed\":10,"
                                        + "\"availableAmount\":1}"));
    }

    @Test
    void testDeleteCardDetails() throws Exception {
        // Arrange
        when(iCardsService.deleteCard(Mockito.<String>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/cards/delete")
                .param("mobileNumber", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cardsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"statusCode\":\"200\",\"statusMsg\":\"Request processed successfully\"}"));
    }

    @Test
    void testDeleteCardDetails2() throws Exception {
        // Arrange
        when(iCardsService.deleteCard(Mockito.<String>any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/cards/delete")
                .param("mobileNumber", "foo");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(cardsController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(417))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"statusCode\":\"417\",\"statusMsg\":\"Delete operation failed. Please try again or contact Dev team\"}"));
    }

    @Test
    void testUpdateCardWhenIsUpdateTrue() throws Exception {
        CardsDto cardsDto =new CardsDto();
        cardsDto.setMobileNumber("8999025679");
        cardsDto.setCardType("CREDIT");
        cardsDto.setAmountUsed(10);
        cardsDto.setTotalLimit(10000);
        cardsDto.setCardNumber("12345679");
        when(iCardsService.updateCard(cardsDto)).thenReturn(true);
        var result = cardsController.updateCard(cardsDto);
    }
    @Test
    void testUpdateCardWhenIsUpdateFalse() throws Exception {
        CardsDto cardsDto =new CardsDto();
        cardsDto.setMobileNumber("8999025679");
        cardsDto.setCardType("CREDIT");
        cardsDto.setAmountUsed(10);
        cardsDto.setTotalLimit(10000);
        cardsDto.setCardNumber("12345679");
        var result = cardsController.updateCard(cardsDto);
    }
}
