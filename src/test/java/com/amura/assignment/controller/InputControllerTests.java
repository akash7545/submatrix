/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amura.assignment.controller;

import static org.junit.Assert.assertEquals;
import com.amura.assignment.model.Request;
import com.amura.assignment.model.Response;
import com.amura.assignment.spring.Application;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Test class to validate API calls for submatrix calculation
 * @author akashinde
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class InputControllerTests {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    private final static String URI = "/v1/submatrix";
    
    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    /**
     * Test case to check valid matrix
     * @throws Exception
     */
    @Test
    public void testValidMatrix() throws Exception {
        Request request = new Request();
        request.setInput("[[1, 0, 0, 0, 0, 1], [0, 1, 1, 1, 0, 0],[0, 1, 1, 1, 0, 0],[0, 0, 0, 1, 0, 0]]");

        String inputJson = mapToJson(request);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI)
                .accept("application/json")
                .contentType("application/json").content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Response response = (Response) mapFromJson(content, Response.class);
        LinkedHashMap subMatrixResponse = (LinkedHashMap) response.getResponse();
        assertEquals( 1, subMatrixResponse.get("x"));
        assertEquals( 1, subMatrixResponse.get("y"));
        assertEquals( 2, subMatrixResponse.get("height"));
        assertEquals( 3, subMatrixResponse.get("length"));
    }

    /**
     * Test case to check empty matrix
     * @throws Exception
     */
    @Test
    public void testEmptyMatrix() throws Exception {
        Request request = new Request();
        request.setInput("[]");

        String inputJson = mapToJson(request);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI)
                .accept("application/json")
                .contentType("application/json").content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    /**
     * Test case to check null matrix
     * @throws Exception
     */
    @Test
    public void testNullInput() throws Exception {
        Request request = new Request();
        request.setInput(null);

        String inputJson = mapToJson(request);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI)
                .accept("application/json")
                .contentType("application/json").content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
    
    /**
     * Test case to check invalid number of columns
     * @throws Exception
     */
    @Test
    public void testInValidColumn() throws Exception {
        Request request = new Request();
        request.setInput("[[1, 0, 0, 0, 1], [0, 1, 1, 1, 0, 0],[0, 1, 1, 1, 0, 0],[0, 0, 0, 1, 0, 0]]");

        String inputJson = mapToJson(request);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI)
                .accept("application/json")
                .contentType("application/json").content(inputJson)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Response response = (Response) mapFromJson(content, Response.class);
        assertEquals("Invalid input: varying number of columns", response.getResponse());
    }

    /**
     * Test case to check invalid data in matrix
     * @throws Exception
     */
    @Test
    public void testInValidColumnData() throws Exception {
        Request request = new Request();
        request.setInput("[[1, 0, 0, 0,aaa, 1], [0, 1, 1, 1, 0, 0],[0, 1, 1, 1, 0, 0],[0, 0, 0, 1, 0, 0]]");

        String inputJson = mapToJson(request);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI)
                .accept("application/json")
                .contentType("application/json").content(inputJson)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Response response = (Response) mapFromJson(content, Response.class);
        assertEquals("Invalid input: cannot parse input", response.getResponse());
    }
}
