package ru.shopitem.web;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.shopitem.AbstractTestCase;
import ru.shopitem.TestJsonUtil;
import ru.shopitem.TestMather;
import ru.shopitem.TestQueries;
import ru.shopitem.model.ShopItem;
import ru.shopitem.service.ShopItemService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.shopitem.ShopItemsTestData.PHONE;
import static ru.shopitem.ShopItemsTestData.TELEVISION;

class ItemControllerTest extends AbstractTestCase {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ShopItemService service;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }

    @Test
    void createREST() throws Exception {
        Map<String, String> options = new HashMap<>();
        options.put("Key One", "Value One");
        options.put("Key Two", "Value Two");
        ShopItem testShopItem = new ShopItem("REST test", "REST description", options);

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(ItemController.REST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestJsonUtil.writeValueToJson(testShopItem)));

        ShopItem returned = TestJsonUtil.readFromJsonResultAction(action, ShopItem.class);
        testShopItem.setId(returned.getId());

        TestMather.assertMatch(testShopItem, returned);
        TestMather.assertMatch(testShopItem, service.get(testShopItem.getId()));
    }

    @Test
    void getREST() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(ItemController.REST_URL + "/" + TELEVISION.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> TestMather.assertMatch(TestJsonUtil.readFromJsonMvcResult(result, ShopItem.class), TELEVISION));
    }

    @Test
    void getByNameREST() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ItemController.REST_URL + "/filter?name=" + PHONE.getName()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result ->
                        TestMather.assertMatch(
                                TestJsonUtil.readListFromJsonMvcResult(result, new TypeReference<List<ShopItem>>(){}),
                                Collections.singletonList(PHONE)
                        ));
    }

    @Test
    void getByOptionKeyAndValueREST() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ItemController.REST_URL + "/filter/option?key=Color&value=Black"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result ->
                        TestMather.assertMatch(
                                TestJsonUtil.readListFromJsonMvcResult(result, new TypeReference<List<ShopItem>>(){}),
                                Collections.singletonList(PHONE)
                        ));
    }
}