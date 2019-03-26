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

/**
 * Input controller with API interface to process input
 * @author akashinde
 *
 */
@RestController
public class InputController extends CommonController {

  @Autowired
  private InputProcessingService inputProcessingService;

  /**
   * This method handles API call to get submatrix details
   * Processes Input matrix to get submatrix details
   * @param request
   * @return Response object containing matrix details (x,y,height,length) or error message (in case of error)
   */
    @RequestMapping(value="/submatrix", method=RequestMethod.POST, consumes="application/json", produces = "application/json")
    public ResponseEntity<Object> processInput(@RequestBody Request request) {
        Response response = new Response();
        try {
            if (!validate(request)) {
                response.setResponse("Invalid input");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }
            SubMatrixResponse subMatrixResponse = inputProcessingService.processInput(request.getInput());
            response.setResponse(subMatrixResponse);
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (Exception e) {
        	response.setResponse(e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
