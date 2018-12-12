package org.salamansar.oder.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mockito;
import org.salamansar.oder.module.payments.dto.IncomeDto;

/**
 *
 * @author Salamansar
 */
public class JsonMarshallerTest {
	
	private JsonMarshaller marshaller;
	private RandomGenerator generator = new RandomGenerator();
	
	@Before
	public void setUp() {
		marshaller = new JsonMarshaller(new ObjectMapper());
	}
	
	
	@Test
	public void successfullMarshalling() {
		IncomeDto dto = generator.generate(IncomeDto.class);
		
		String result = marshaller.toJsonString(dto);
		
		assertNotNull(result);
		assertTrue(result.contains("id"));
		assertTrue(result.contains("incomeDate"));
		assertTrue(result.contains("amount"));
		assertTrue(result.contains("description"));
		assertTrue(result.contains("documentNumber"));
	}
	
	@Test
	public void failureMarshalling() throws JsonProcessingException {
		ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
		marshaller = new JsonMarshaller(mapper);
		ObjectWriter writer = Mockito.mock(ObjectWriter.class);
		Mockito.when(mapper.writer()).thenReturn(writer);
		Mockito.when(writer.writeValueAsString(Mockito.any()))
				.thenThrow(new JsonGenerationException("test exception", (JsonGenerator)null));
		IncomeDto dto = generator.generate(IncomeDto.class);
		
		String result = marshaller.toJsonString(dto);
		
		assertNotNull(result);
		assertEquals("Failure to marshall object to Json", result);
	}
	
}
