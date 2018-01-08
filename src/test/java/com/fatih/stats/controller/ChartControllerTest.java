package com.fatih.stats.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatih.stats.model.ChartInput;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class ChartControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void accessProtected() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(unauthenticated()).andExpect(status().isUnauthorized());
	}

	@Test
	public void shouldGetCorrectResposneFromGetChart() throws Exception {
		ChartInput ci = new ChartInput();
		String[] dimension = { "team" };
		ci.setDimensions(dimension);
		String[] measures = { "champions", "leagues" };
		ci.setMeasures(measures);
		ObjectMapper om = new ObjectMapper();
		String writeValueAsString = om.writeValueAsString(ci);
		this.mockMvc.perform(get("/chart").contentType(MediaType.APPLICATION_JSON).content(writeValueAsString)
				.with(httpBasic("user", "password"))).andExpect(status().isOk());
	}

	@Test
	public void shouldGetSubsetValidationErrorForMeasuresFromGetChart() throws Exception {
		ChartInput ci = new ChartInput();
		String[] dimension = { "team" };
		ci.setDimensions(dimension);
		String[] measures = { "champions1", "league" };
		ci.setMeasures(measures);
		ObjectMapper om = new ObjectMapper();
		String writeValueAsString = om.writeValueAsString(ci);
		this.mockMvc
				.perform(get("/chart").contentType(MediaType.APPLICATION_JSON).content(writeValueAsString)
						.with(httpBasic("user", "password")))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Measures can only be a subset of")));
	}

	@Test
	public void shouldGetNullValidationErrorForMeasuresFromGetChart() throws Exception {
		ChartInput ci = new ChartInput();
		String[] dimension = { "team" };
		ci.setDimensions(dimension);
		ObjectMapper om = new ObjectMapper();
		String writeValueAsString = om.writeValueAsString(ci);
		this.mockMvc
				.perform(get("/chart").contentType(MediaType.APPLICATION_JSON).content(writeValueAsString)
						.with(httpBasic("user", "password")))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Measures cannot be null")));
	}
}
