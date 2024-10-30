package br.com.argos.argosaisprint3.config;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(ModelMapperConfig.class)
public class ModelMapperConfigTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testModelMapperBean() {
        assertNotNull(modelMapper, "ModelMapper bean should not be null");
    }
}
