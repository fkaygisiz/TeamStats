package com.fatih.stats.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatih.stats.model.StatisticsInput;
import com.fatih.stats.validation.TimeUnit;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class StatisticsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void accessProtected() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(unauthenticated()).andExpect(status().isUnauthorized());
	}
	
	@Test
	public void shouldGetNoErrorFromGetStatistics() throws Exception {
		StatisticsInput si = new StatisticsInput();
		si.setLast(5);
		si.setMavgPoints(2);
		si.setTimeUnit(TimeUnit.MINUTES.getValue());
		ObjectMapper om = new ObjectMapper();
		String writeValueAsString = om.writeValueAsString(si);
		this.mockMvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON).content(writeValueAsString)
				.with(httpBasic("admin", "password"))).andExpect(status().isOk());
		
	}
	
	@Test
	public void shouldGetForbiddenErrorFromGetChartWithUserRole() throws Exception {
		StatisticsInput si = new StatisticsInput();
		si.setLast(5);
		si.setMavgPoints(2);
		si.setTimeUnit(TimeUnit.MINUTES.getValue());
		ObjectMapper om = new ObjectMapper();
		String writeValueAsString = om.writeValueAsString(si);
		this.mockMvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON).content(writeValueAsString)
				.with(httpBasic("user", "password"))).andExpect(status().isForbidden());;
		
	}
}
