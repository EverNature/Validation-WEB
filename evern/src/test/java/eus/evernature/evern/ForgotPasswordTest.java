package eus.evernature.evern;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import net.bytebuddy.utility.RandomString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ForgotPasswordTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void GetForgotPasswordPageIsOk() throws Exception {
        String url = "/login/account-recovery";

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void requestForgotPasswordTokenIsOk() throws Exception {
        String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        String url = "/login/account-recovery";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        String email = "evernature.solutions@gmail.com";

        params.add("email", email);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken).param(csrfToken.getParameterName(), csrfToken.getToken())
                .params(params))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void requestForgotPasswordWithInvalidMailTokenIsNok() throws Exception {
        String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        String url = "/login/account-recovery";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        String email = "invent.invent@gmail.com";

        params.add("email", email);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken).param(csrfToken.getParameterName(), csrfToken.getToken())
                .params(params))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
        
    @Test
    void getResetPasswordIsOk() throws Exception {
        String url = "/login/account-recovery/reset_password";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        String token = RandomString.make(45);

        params.add("token", token);

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void resetPasswordWithInvalidTokenIsNotSuccessful() throws Exception {
        String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        String url = "/login/account-recovery/reset_password";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        String token = RandomString.make(45);

        params.add("token", token);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken).param(csrfToken.getParameterName(), csrfToken.getToken())
                .params(params))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}