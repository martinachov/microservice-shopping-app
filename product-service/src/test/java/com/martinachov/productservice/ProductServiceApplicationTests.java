package com.martinachov.productservice;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martinachov.productservice.infrastructure.adapters.output.persistence.repository.ProductRepository;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ProductServiceApplicationTests {

	@Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@Autowired
	private MockMvc mockMvc;

    @Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
        dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

	@BeforeAll
    public static void setUp() {
        mongoDBContainer.setPortBindings(List.of("27017:27017"));
        mongoDBContainer.start();
    }
	
	@Test
    void should_return_all_products() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

	@Test
	void should_create_product() throws Exception {
		Map<String, Object> productMap = Map.of("name","prueba1","description","prueba1","price","123");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productMap))				)
				.andExpect(MockMvcResultMatchers.status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());

	}

	@AfterAll
    public static void tearDown() {
        mongoDBContainer.stop();
    }
}
