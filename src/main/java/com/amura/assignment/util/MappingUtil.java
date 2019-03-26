package com.amura.assignment.util;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Mapping utility for Object conversion
 * @author akashinde
 *
 */
public class MappingUtil {

	/**
	 * This method converts String formatted matrix in Integer matrix
	 * @param input
	 * @return Valid Integer matrix
	 * @throws Exception
	 */
	public static Integer[][] convertToMatrix(String input) throws Exception {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Integer[][] inputMatrix = objectMapper.readValue(input, Integer[][].class);
			return inputMatrix;
		} catch (Exception e) {
			throw new Exception("Invalid input: cannot parse input");
		}
	}
}
