package com.amura.assignment.business;

import com.amura.assignment.model.SubMatrixResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class InputProcessingService {

    @Autowired
    private ObjectMapper objectMapper;

    public SubMatrixResponse processInput(String input) {
        SubMatrixResponse response = new SubMatrixResponse();
        Integer[][] inputMatrix = getMatrixFromInput(input);
        if (inputMatrix != null) {

            int max = 0; int row=0, col=0;
            for (int i=1;i<inputMatrix.length;i++) {
                for (int j = 1; j < inputMatrix[i].length; j++) {

                    if (inputMatrix[i - 1][j] != 0 && inputMatrix[i][j - 1] != 0) {
                        inputMatrix[i][j] = inputMatrix[i - 1][j] + 1;
                        if (max <= inputMatrix[i - 1][j] + 1) {
                            row = i;
                            col = j;
                            max = inputMatrix[i - 1][j] + 1;
                        }
                    }
                }
            }

            //Calculating height
            int height =0;
            for (int i=row;i>0;i--) {
                if (inputMatrix[i][col]!=0) {
                    height++;
                } else {
                    break;
                }
            }

            //Calculating length
            int length =0;
            for (int i=col;i>0;i--) {
                if (inputMatrix[row][i]!=0) {
                    length++;
                } else {
                    break;
                }
            }
            response.setX(row-height+1);
            response.setY(col-length+1);
            response.setHeight(height);
            response.setLength(length);
        }
        return response;
    }

    private Integer[][] getMatrixFromInput(String input) {
        try {
            Integer[][] inputMatrix = objectMapper.readValue(input, Integer[][].class);
            return inputMatrix;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
