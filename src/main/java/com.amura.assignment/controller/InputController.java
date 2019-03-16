package com.amura.assignment.controller;

import com.amura.assignment.business.InputProcessingService;
import com.amura.assignment.model.Request;
import com.amura.assignment.model.Response;
import com.amura.assignment.model.SubMatrixResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InputController {

  @Autowired
  private InputProcessingService inputProcessingService;

    @RequestMapping(value="/submatrix", method=RequestMethod.POST, consumes="application/json", produces = "application/json")
    public ResponseEntity<Object> processInput(@RequestBody Request request) {
        Response response = new Response();
        try {
            if (request == null || request.getInput()==null ||
                request.getInput().isEmpty() || request.getInput().contains("[]")) {

                response.setResponse("Invalid input");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            SubMatrixResponse subMatrixResponse = inputProcessingService.processInput(request.getInput());
            response.setResponse(subMatrixResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
