//package uz.supersite;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import uz.supersite.controller.CategoryController;
//import uz.supersite.entity.Category;
//import uz.supersite.exception.CategoryNotFoundException;
//import uz.supersite.service.CategoryService;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.ContentResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(CategoryController.class)
//public class CategoryApiControllerTest {
//    private static final String END_POINT_PATH = "/v1/categories";
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper mapper;
//
//    @MockBean
//    CategoryService categoryService;
//
//    @Test
//    public void testAddShouldReturn400BadRequest() throws Exception {
//        Category category = new Category();
//
//        String bodyContent = mapper.writeValueAsString(category);
//
//        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }
//
//    @Test
//    public void testAddShouldReturn201Created() throws Exception {
//        Category category = new Category();
//        category.setId(1);
//        category.setName("Electronics");
//        category.setParent(null);
//        category.setEnabled(true);
//        category.setAlias("electronics");
//        category.setImage("category_image.png");
//        category.setChildren(null);
//        category.setHasChildren(false);
//
//        Mockito.when(categoryService.add(category)).thenReturn(category);
//
//        String bodyContent = mapper.writeValueAsString(category);
//
//        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.id",is(1)))
//                .andExpect(jsonPath("$.alias",is("electronics")))
//                .andExpect(header().string("Category", "/v1/categories/1"))
//                .andDo(print());
//
//    }
//
//    @Test
//    public void testListShouldReturn204NoContent() throws Exception {
//        Mockito.when(categoryService.list()).thenReturn(Collections.emptyList());
//        mockMvc.perform(get(END_POINT_PATH))
//                .andExpect(status().isNoContent())
//                .andDo(print());
//
//    }
//
//    @Test
//    public void testListShouldReturn200OK() throws Exception {
//        Category category1 = new Category();
//        category1.setId(1);
//        category1.setName("Electronics");
//        category1.setParent(null);
//        category1.setEnabled(true);
//        category1.setAlias("electronics");
//        category1.setImage("category_image.png");
//        category1.setChildren(null);
//        category1.setHasChildren(false);
//
//        Category category2 = new Category();
//        category2.setId(2);
//        category2.setName("Arts and Marketing");
//        category2.setParent(null);
//        category2.setEnabled(true);
//        category2.setAlias("arts_and_marketing");
//        category2.setImage("arts_image.png");
//        category2.setChildren(null);
//        category2.setHasChildren(false);
//
//        Mockito.when(categoryService.list()).thenReturn(List.of(category1,category2));
//
//        mockMvc.perform(get(END_POINT_PATH))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$[0].id",is(1)))
//                .andExpect(jsonPath("$[0].alias",is("electronics")))
//                .andExpect(jsonPath("$[1].id",is(2)))
//                .andExpect(jsonPath("$[1].alias",is("arts_and_marketing")))
//                .andDo(print());
//    }
//
//    @Test
//    public void testGetShouldReturn404NotFound() throws Exception {
//        String requestURL = END_POINT_PATH + "/123";
//
//        mockMvc.perform(get(requestURL))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }
//
//    @Test
//    public void testGetShouldReturn200Ok() throws Exception {
//        int id = 1;
//        String requestURL = END_POINT_PATH + "/" + id;
//
//        Category category = new Category();
//        category.setId(1);
//        category.setName("Electronics");
//        category.setParent(null);
//        category.setEnabled(true);
//        category.setAlias("electronics");
//        category.setImage("category_image.png");
//        category.setChildren(null);
//        category.setHasChildren(false);
//
//        Mockito.when(categoryService.get(id)).thenReturn(category);
//
//        mockMvc.perform(get(requestURL))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.id",is(id)))
//                .andExpect(jsonPath("$.name",is("Electronics")))
//                .andExpect(jsonPath("$.alias",is("electronics")))
//                .andDo(print());
//    }
//
//    @Test
//    public void testUpdateShouldReturn404NotFound() throws Exception {
//        Category category = new Category();
//        category.setId(11);
//        category.setName("Electronics");
//        category.setParent(null);
//        category.setEnabled(true);
//        category.setAlias("electronics");
//        category.setImage("category_image.png");
//        category.setChildren(null);
//        category.setHasChildren(false);
//
//        Mockito.when(categoryService.update(category, category.getId())).thenThrow(new CategoryNotFoundException("No category found"));
//        String bodyContent = mapper.writeValueAsString(category);
//
//        mockMvc.perform(put(END_POINT_PATH + "/update/" + category.getId()).contentType("application/json").content(bodyContent))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }
//
//    @Test
//    public void testUpdateShouldReturn400BadRequest() throws Exception {
//        Category category = new Category();
////        category.setId(112);
//        category.setName("Electronics");
//        category.setParent(null);
//        category.setEnabled(true);
////        category.setAlias("electronics");
//        category.setImage("category_image.png");
//        category.setChildren(null);
//        category.setHasChildren(false);
//
////        Mockito.when(categoryService.update(category)).thenReturn(category);
//        String bodyContent = mapper.writeValueAsString(category);
//
//        mockMvc.perform(put(END_POINT_PATH + "/update/" + category.getId()).contentType("application/json").content(bodyContent))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }
//
//    @Test
//    public void testUpdateShouldReturn200Ok() throws Exception {
//        Category category = new Category();
//        category.setId(1);
//        category.setName("Electronics");
//        category.setParent(null);
//        category.setEnabled(true);
//        category.setAlias("electronics");
//        category.setImage("category_image.png");
//        category.setChildren(null);
//        category.setHasChildren(false);
//
//        Mockito.when(categoryService.update(category, category.getId())).thenReturn(category);
//        String bodyContent = mapper.writeValueAsString(category);
//
//        mockMvc.perform(put(END_POINT_PATH + "/update/" + category.getId()).contentType("application/json").content(bodyContent))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.id",is(1)))
//                .andExpect(jsonPath("$.alias",is("electronics")))
//                .andDo(print());
//
//    }
//
//    @Test
//    public void testDeleteShouldReturn404NotFound() throws Exception {
//        int id = 1;
//        String requestURL = END_POINT_PATH + "/delete/" + id;
//
//        Mockito.doThrow(CategoryNotFoundException.class).when(categoryService).delete(id);
//        mockMvc.perform(delete(requestURL))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }
//
//    @Test
//    public void testDeleteShouldReturn204NoContent() throws Exception {
//        int id = 1;
//        String requestURL = END_POINT_PATH + "/delete/" + id;
//
//        Mockito.doNothing().when(categoryService).delete(id);
//        mockMvc.perform(delete(requestURL))
//                .andExpect(status().isNoContent())
//                .andDo(print());
//    }
//}
