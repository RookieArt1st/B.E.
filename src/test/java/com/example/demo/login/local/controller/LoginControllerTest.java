package com.example.demo.login.local.controller;

import com.example.demo.login.local.entity.Member;
import com.example.demo.login.local.record.LoginRequestRecord;
import com.example.demo.login.local.record.ResponseRecord;
import com.example.demo.login.local.repository.MemberRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JoinService joinService;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void login_success() throws Exception {
        // given
        String existingUsername = "sohee";
        String password = "1234";
        LoginRequestRecord loginRequestRecord = new LoginRequestRecord(existingUsername, password);
        Member existingMember = loginRequestRecord.toMemberEntity();
        memberRepository.save(existingMember);

        // when
        LoginRequestRecord checkLoginRequestRecord = new LoginRequestRecord(existingUsername, password);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/main/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(checkLoginRequestRecord)));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        System.out.println("mvcResult :: " + mvcResult.getResponse().getContentAsString());
    }


    @Test
    @DisplayName("로그아웃을 확인합니다.")
    void logout() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/main/logout"));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        System.out.println("mvcResult :: " + mvcResult.getResponse().getContentAsString());
    }
}
