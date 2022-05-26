package eus.evernature.evern.models;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import eus.evernature.evern.service.expert.ExpertService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ExpertTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ExpertService expertService;

    
    @Test
    @Transactional
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    @Transactional
    void expertEqualsIsOk() throws Exception {
        Expert expert = expertService.getExpert("testUser");

        assertTrue("Equals not working", expert.equals(expert));
    }

    @Test
    @Transactional
    void expertHashCodeIsOk() throws Exception {
        Expert expert = expertService.getExpert("testUser");

        assertEquals(expert.hashCode(), expert.hashCode());
    }

    @Test
    @Transactional
    void expertTostringIsOk() throws Exception {
        Expert expert = expertService.getExpert("testUser");

        assertEquals(expert.toString(), expert.toString());
    }
}
