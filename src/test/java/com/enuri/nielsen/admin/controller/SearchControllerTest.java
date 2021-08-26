package com.enuri.nielsen.admin.controller;

import com.enuri.nielsen.infra.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@MockMvcTest
class SearchControllerTest {

    @Autowired MockMvc mockMvc;

    @DisplayName("닐슨 구매 내역 데이터 조회 뷰 보여주는지 여부")
    @Test
    void showNielSenSearchView() throws Exception {
        mockMvc.perform(get("/admin/search"))
                .andExpect(view().name("admin/search"))
                .andExpect(status().isOk());
    }

}