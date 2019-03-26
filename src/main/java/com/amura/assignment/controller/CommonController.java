package com.amura.assignment.controller;

import com.amura.assignment.model.Request;
import com.amura.assignment.util.MappingUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Common controller with basic validation
 * @author akashinde
 *
 */
@RestController
@RequestMapping("/v1")
public class CommonController {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * This method validated given input in String format
     * @param request
     * @return true if given input is valid
     * @throws Exception
     */
    public boolean validate(Request request) throws Exception {
        if (request == null || request.getInput() == null ||
                request.getInput().isEmpty() || request.getInput().contains("[]")) {
            return false;
        }

        Integer[][] inputMatrix = MappingUtil.convertToMatrix(request.getInput());
        
        if (inputMatrix == null) {
            throw new Exception("Invalid input: cannot parse input");
        }

        for (int i = 0; i < inputMatrix.length; i++) {
            if (i > 0 && inputMatrix[i].length != inputMatrix[i - 1].length) {
                throw new Exception("Invalid input: varying number of columns");
            }
        }
        return true;
    }
}
