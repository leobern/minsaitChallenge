//
//import br.com.minsait.auth.config.SecurityConfig;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.core.token.TokenService;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
////@WebMvcTest({AuthController.class})
////@Import({SecurityConfig.class, TokenService.class})
////
////class AuthControllerTest {
////
////    @Autowired
////    MockMvc mvc;
////
////
////    @Test
////    void rootWhenUnauthenticatedThen401() throws  Exception{
////        this.mvc.perform(get("/")).andExpect(status().isUnauthorized());
////    }
////
////    @Test
////    void getTokenForUser() throws Exception{
////        MvcResult mvcResult= this.mvc.perform(post("/token").with(httpBasic("leonardoBernardino","12345"))).andExpect(status().isOk()).andReturn();
////    }
////
////
////
////}