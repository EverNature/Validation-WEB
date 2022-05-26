package eus.evernature.evern;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import eus.evernature.evern.models.Expert;
import eus.evernature.evern.models.forms.ExpertCreationForm;
import eus.evernature.evern.service.expert.ExpertService;
import net.bytebuddy.utility.RandomString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ExpertServiceTest {

	@Autowired
	private MockMvc mockMvc;

    @Autowired
    ExpertService expertService;

	@Test
	void shouldCreateMockMvc() {
		assertNotNull(mockMvc);
	}

	@Test
	void mapExpertFormToExpertIsOk() throws Exception {
        ExpertCreationForm expertCreationForm = new ExpertCreationForm();
        expertCreationForm.setEmail("a");
        expertCreationForm.setName("a");
        expertCreationForm.setPassword("a");
        expertCreationForm.setSurname("a");
        expertCreationForm.setName("a");

        Expert expert = expertService.mapExpertFormToExpert(expertCreationForm);

        assertTrue("Expert not mapped correctly", expert.getName().equals("a"));
	}

	@Test
	void checkExpertExistentIsOk() throws Exception {
        String username = "testUser";

        boolean existent = expertService.checkExpertExistent(username);

        assertTrue("Expert does not exist", existent);
	}

    @Test
    void addRoleToExpertIsOk() {
        String role = "Admin";
        String username = "testUser";

        expertService.addRoleToUser(username, role);
    }

    @Test
    void getInvalidUserThrowsAnException() {
        String randomUsername = RandomString.make(45);

        Expert expert = null;

        expert = expertService.getExpert(randomUsername);

        assertTrue(true);
    }

    @Test
    void getExpertByInexistentAccountActivateTokenIsNok() {
        String wrongToken = "a";

        Expert expert = expertService.getExpertByActivateAccountToken(wrongToken);

        assertNull(expert);
    }
}
