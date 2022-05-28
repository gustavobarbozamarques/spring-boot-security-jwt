package br.com.gustavobarbozamarques.springbootsecurityjwt.controllers;

import br.com.gustavobarbozamarques.springbootsecurityjwt.config.SecurityConfigTest;
import br.com.gustavobarbozamarques.springbootsecurityjwt.filters.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PrivateController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
)
@Import(SecurityConfigTest.class)
public class PrivateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserResource_whenNotAuthenticated_shouldReturnUnauthorized() throws Exception {
        this.mockMvc
                .perform(get("/api/private/user"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "username1", authorities = {"USER"})
    public void testUserResource_whenAuthenticated_shouldReturnOk() throws Exception {
        this.mockMvc
                .perform(get("/api/private/user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "username1", authorities = {"USER"})
    public void testAdminResource_whenAuthenticatedIsOnlyUser_shouldReturnForbidden() throws Exception {
        this.mockMvc
                .perform(get("/api/private/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "username2", authorities = {"USER", "ADMIN"})
    public void testAdminResource_whenAuthenticatedIsAdmin_shouldReturnOk() throws Exception {
        this.mockMvc
                .perform(get("/api/private/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
