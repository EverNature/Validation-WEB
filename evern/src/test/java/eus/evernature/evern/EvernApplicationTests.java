package eus.evernature.evern;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class EvernApplicationTests {

	@Autowired
	private MockMvc mockMvc;
  
	@Test
	void shouldCreateMockMvc() {
	  assertNotNull(mockMvc);
	}
  
	@Test
	@WithMockUser(username = "testUser", roles = "USER")
	void shouldReturnHomePage() throws Exception {
	  String url = "/home";
  
	  mockMvc.perform(MockMvcRequestBuilders.get(url))
		  .andExpect(MockMvcResultMatchers.status().isOk());
	}
  
	@Test
	@WithAnonymousUser
	void shouldReturnRedirectionError() throws Exception {
	  String url = "/home";
  
	  mockMvc.perform(MockMvcRequestBuilders.get(url))
		  .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}
}
