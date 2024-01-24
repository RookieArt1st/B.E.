package com.example.demo.login.local.controller;

import com.example.demo.login.local.record.JoinRequestRecord;
import com.example.demo.login.local.service.JoinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
class JoinControllerTest {
    @InjectMocks
    private JoinController joinController;
    @Mock
    private JoinService joinService;

    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(joinController).build();
    }
    @Test
    @DisplayName("회원 가입을 확인합니다. (성공 케이스)")
    void create_success() throws Exception {
        //given
        JoinRequestRecord joinRequestRecord = new JoinRequestRecord("1234","jong_seok","홍은동","010-5133-5728","123-456-789-000");

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(joinRequestRecord)));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        System.out.println("mvcResult :: " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("ID 유효성 체크_실패")
    void isUniqueUserName_Fail() throws Exception {
        // given
        String existingUsername = "existingUser";
        when(joinService.isUniqueUsername(existingUsername)).thenReturn(false);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/users/idCheck")
                        .param("username", existingUsername)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        System.out.println("mvcResult :: " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("ID 유효성 체크_성공")
    void isUniqueUserName_Success() throws Exception {
        // given
        String uniqueUsername = "uniqueUser";
        when(joinService.isUniqueUsername(uniqueUsername)).thenReturn(true);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/users/idCheck")
                        .param("username", uniqueUsername)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        System.out.println("mvcResult :: " + mvcResult.getResponse().getContentAsString());
    }
}
