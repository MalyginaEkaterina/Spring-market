package ru.geekbrains.spring.market;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.market.model.*;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DeliveryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthClient authClient;

    @Test
    public void getAllTypesTest() throws Exception {
        mvc.perform(get("/types")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getAllShopsTest() throws Exception {
        mvc.perform(get("/shops")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getAllPointsTest() throws Exception {
        mvc.perform(get("/pick_up_points")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser(username = "User1", roles = "ADMIN")
    @Transactional
    public void addShopTest() throws Exception {
        String jsonRequest = "{\n" +
                "        \"city\": \"moscow\",\n" +
                "        \"location\": \"street podolskaya\",\n" +
                "        \"workHours\": \"8-20\",\n" +
                "        \"phone\": null\n" +
                "    }";
        mvc.perform(post("/add_shop")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mvc.perform(get("/shops")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    @WithMockUser(username = "User1", roles = "ADMIN")
    @Transactional
    public void updateShopTest() throws Exception {
        MvcResult result = mvc.perform(get("/shops")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<Shop> shops = objectMapper.readValue(content, typeFactory.constructCollectionType(List.class, Shop.class));
        Shop shopUpdated = shops.get(0);
        shopUpdated.setCity("new");
        String jsonRequest = objectMapper.writeValueAsString(shopUpdated);
        mvc.perform(put("/update_shop")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get("/shops")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].city", is("new")));
    }

    @Test
    public void getDeliveryPriceTest() throws Exception {
        DeliveryPriceRequestDto req1 = new DeliveryPriceRequestDto("COURIER", 7000f);
        DeliveryPriceRequestDto req2 = new DeliveryPriceRequestDto("PICK_UP_POINT", 100f);
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post("/price")
                .content(objectMapper.writeValueAsString(req1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(222.0)));
        mvc.perform(post("/price")
                .content(objectMapper.writeValueAsString(req2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(111.0)));
    }

    @Test
    public void getDeliveryDetailsShopsTest() throws Exception {
        MvcResult resultShops = mvc.perform(get("/shops")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        String contentShops = resultShops.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<Shop> shops = objectMapper.readValue(contentShops, typeFactory.constructCollectionType(List.class, Shop.class));
        Shop shop = shops.get(0);

        DeliveryInfoRequestDto reqShop = new DeliveryInfoRequestDto(DeliveryType.SHOP.getId(), shop.getId());
        mvc.perform(post("/delivery_details")
                .content(objectMapper.writeValueAsString(reqShop))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shop.location", is(shop.getLocation())));
    }

    @Test
    public void getDeliveryDetailsPointsTest() throws Exception {
        MvcResult resultPoints = mvc.perform(get("/pick_up_points")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        String contentPoints = resultPoints.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<PickUpPoint> points = objectMapper.readValue(contentPoints, typeFactory.constructCollectionType(List.class, PickUpPoint.class));
        PickUpPoint point = points.get(0);

        DeliveryInfoRequestDto reqPoint = new DeliveryInfoRequestDto(DeliveryType.PICK_UP_POINT.getId(), point.getId());
        mvc.perform(post("/delivery_details")
                .content(objectMapper.writeValueAsString(reqPoint))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickUpPoint.location", is(point.getLocation())));
    }

    @Test
    public void getDeliveryDetailsExceptionTest() throws Exception {
        DeliveryInfoRequestDto reqException = new DeliveryInfoRequestDto(10, 10L);
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post("/delivery_details")
                .content(objectMapper.writeValueAsString(reqException))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getDeliveryDetailsCourierTest() throws Exception {
        UserDeliveryAddressDto add = new UserDeliveryAddressDto();
        add.setCity("Moscow");
        add.setStreet("test street");
        Mockito
                .doReturn(add)
                .when(authClient)
                .getUserAddress(1L);
        DeliveryInfoRequestDto req = new DeliveryInfoRequestDto(DeliveryType.COURIER.getId(), 1L);
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post("/delivery_details")
                .content(objectMapper.writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userDeliveryAddress.street", is("test street")));
        Mockito.verify(authClient, Mockito.times(1)).getUserAddress(1L);
    }
}
