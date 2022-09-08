package cane.brothers;

import cane.brothers.tags.DummyTag;
import cane.brothers.tags.TagRepository;
import cane.brothers.tags.TagView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author mniedre
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TestConfig.class})
public class AuthMvcIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagRepository tagRepo;

    @Test
    public void shouldRejectIfNoAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tags")
                .accept(MediaType.ALL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenInvalidToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tags")
                .header("Authorization", "Bearer " + "Ssnv70PPr9Ulycw4suahxPXr1a6l53pnBLKhW9MUbOPYMrsII7tl/8vEa45hxh+HsWuxEk1yj7bwjIem5vMYrCUtg9koCqm3bYj1TWUi5loO3Sj72l2der0zTZW4BlF2JPXNCDOxnBpPMFtngwfTLb0ZDl3qyVJ5BRTUT12ZUOsrtR37KXBuuO8Vg0kVfiiVXAuqeoZbULc9ZWIdvtJ3Be9AZUp/MypLh/oWvJIJLMJUpNAl9uuL+XlZPlu/iwBWRfhkQKBq8ER4cp86+SO3QUJafsO/QhuOoNa0nRw386TnioPoyNQ13wtckjcxFZmJguM4A+V6fJ3LTcvzDwQ0zxe+V21fCtQ9bbOPPbMCB3ykueR/2uIJw5lQjLOc3XUg/gxJRapkrdVHWeb6XgiWbhO/6mIxUaa5KxARSTKVRcf53gXK5yslUtjqok8naPvDoUC2YaVx6NDInJ/aeQ7jniN3KlmgkaCkWfSSubgT2VM5abhtgP9Gb3+OnpwhtBCuYiRlxCjdECr2gSO7EY6Djh+3cCHqHd5bRor5LUNZhxAF/IQ+PMv3MeBytIYtiqtnJWsIidRFrN14Ui/VXer9Jg7+EwginzLgVpcn3/rPOrZKjaPzcn5Lo9fqELYs5WzlMhxAXtqOOFCD3m7xa7+81rnf8sgFPoNizmYKE+NBT+7lNQThb91uefueAGlkEOrrs9p/1cdkSEFvxXqHRVHgLrC01KC1wRD4X4u60pqPbGnblsk/HJICgeZ/CFUbNFQAL14+uRgImSnHq4t7x1D7f37pppQP5pkid/htZlAx+glGwEKaUjfU9+iGE2w9fNFC--sY+4KMxFLUGle4FT--wjfFfGVruvhKsbX1gS/5gw==")
                .param("email", "jim@yahoo.com"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @WithUserDetails("testuser")
    public void shouldAllowUserWithAuthorities() throws Exception {
        DummyTag testTag = new DummyTag();
        List<TagView> tags = new ArrayList<>();
        tags.add(testTag);

        given(tagRepo.findAll(TagView.class)).willReturn(tags);

        mockMvc.perform(MockMvcRequestBuilders.get("/tags")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].value").value("test"));
    }

    @Test
    @WithUserDetails("blauser")
    public void givenInvalidRole_whenGetSecureRequest_thenForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tags")
                .accept(MediaType.ALL))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}
