package com.amura.assignment.business;

import com.amura.assignment.model.SubMatrixResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business service to process input from controller
 * @author akashinde
 *
 */
@Service
public class InputProcessingService {

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * This method processes input received from controller and returns response
	 * @param input
	 * @return Response containing details of submatrix in given matrix
	 * @throws Exception
	 */
	public SubMatrixResponse processInput(String input) throws Exception {
		SubMatrixResponse response = new SubMatrixResponse();
		Integer[][] inputMatrix = objectMapper.readValue(input, Integer[][].class);

		int max = 0;
		int row = 0, col = 0;

		boolean allZero = true;
		for (int i = 0; i < inputMatrix.length; i++) {
			for (int j=0;j<inputMatrix[i].length;j++) {
				if (inputMatrix[i][j] != 0) {
					allZero = false;
				}
			}
		}

		response.setX(0);
        response.setY(0);
        response.setHeight(0);
        response.setLength(0);
        
		if (!allZero) {
			for (int i = 1; i < inputMatrix.length; i++) {
				for (int j = 0; j < inputMatrix[i].length; j++) {

					if (inputMatrix[i][j] != 0) {
						inputMatrix[i][j] = inputMatrix[i - 1][j] + 1;
						if (j > 0) {
						if (max <= inputMatrix[i][j] && inputMatrix[i][j-1]>0 && ( inputMatrix[i][j-1]- inputMatrix[i-1][j]==1)) {
							row = i;
							col = j;
							max = inputMatrix[i][j];
						}
						}
					}
				}
			}
			
			//Calculating height
			int height = 0;
			for (int i = row; i >= 0; i--) {
				if (inputMatrix[i][col] != 0) {
					height++;
				} else {
					break;
				}
			}

			//Calculating length
			int length = 0;
			for (int i = col; i >= 0; i--) {
				if (inputMatrix[row][i] != 0) {
					length++;
				} else {
					break;
				}
			}

			response.setX((row+1) - height);
			response.setY((col+1) - length);
			response.setHeight(height);
			response.setLength(length);
		}
		return response;
	}
}
