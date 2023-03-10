package com.junkyu.searchblogservice.web.blog;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import com.junkyu.searchblogservice.annotation.ControllerTest;
import com.junkyu.searchblogservice.port.in.FindBlogUseCase;
import com.junkyu.searchblogservice.web.blog.response.FindBlogResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FindBlogController.class)
@ControllerTest
class FindBlogControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  FindBlogUseCase findBlogUseCase;

  private static final String DEFAULT_URI_PATH = "/v1/blog";

  @Nested
  @DisplayName("FindBlogController ????????????")
  class Describe_of_FindBlogController {

    @Nested
    @DisplayName("Find ????????????")
    class Context_with_find {

      @Nested
      @DisplayName("????????? ?????? ????????? ?????????")
      class Context_with_FindBlogRequest {

        @Test
        @DisplayName("?????? ????????? ????????????????????? ????????????.")
        void it_returns_page() throws Exception {

          given(findBlogUseCase.find(any()))
              .willReturn(getFindBlogResponse());

          mockMvc.perform(get(DEFAULT_URI_PATH + "/list")
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON)
              .param("query", "kakao")
              .param("sort", "recency")
              .param("page", "1")
              .param("size", "10"))
              .andDo(print()).andExpect(status().isOk())
              .andDo(document("doc/blog/find",
                  preprocessRequest(prettyPrint()),
                  preprocessResponse(prettyPrint()),
                  requestParameters(
                      parameterWithName("query").description("????????? ?????? ??????"),
                      parameterWithName("sort").description("?????? ?????? ??????[accuracy | recency]"),
                      parameterWithName("page").description("?????? ????????? ??????"),
                      parameterWithName("size").description("??? ???????????? ????????? ?????? ???")
                  ),
                  responseFields(
                      fieldWithPath("content[]").type(ARRAY).description("?????? ?????? ??????"),
                      fieldWithPath("content[].title").type(STRING).description("????????? ??? ??????"),
                      fieldWithPath("content[].contents").type(STRING).description("????????? ??? ??????"),
                      fieldWithPath("content[].url").type(STRING).description("????????? ??? URL"),
                      fieldWithPath("content[].blogName").type(STRING).description("????????????"),
                      fieldWithPath("content[].dateTime").type(STRING).description("????????? ?????? ??????"),
                      fieldWithPath("pageable").type(STRING).description("????????? ?????? ??????"),
                      fieldWithPath("sort").type(OBJECT).description("?????? ??????"),
                      fieldWithPath("sort.sorted").type(BOOLEAN).description("?????? ??????"),
                      fieldWithPath("sort.unsorted").type(BOOLEAN).description("????????? ??????"),
                      fieldWithPath("sort.empty").type(BOOLEAN).description("?????? ?????? ?????? ??????"),
                      fieldWithPath("totalPages").type(NUMBER).description("?????? ????????? ??????"),
                      fieldWithPath("totalElements").type(NUMBER).description("?????? ???????????? ??????"),
                      fieldWithPath("size").type(NUMBER).description("????????? ?????????"),
                      fieldWithPath("numberOfElements").type(NUMBER).description("?????? ????????? ??? ???????????? ??????"),
                      fieldWithPath("number").type(NUMBER).description("????????? ??????"),
                      fieldWithPath("first").type(BOOLEAN).description( "????????? ????????? ??????"),
                      fieldWithPath("last").type(BOOLEAN).description( "????????? ????????? ??????"),
                      fieldWithPath("empty").type(BOOLEAN).description("???????????? ??????????????? ??????")
                  )));
        }
      }
    }
  }

  private Page<FindBlogResponse> getFindBlogResponse() {
    return new PageImpl<>(
        List.of(
            FindBlogResponse.builder()
                .title("????????? ??? ??????")
                .contents("????????? ??????")
                .url("????????? URL")
                .blogName("????????????")
                .dateTime(LocalDateTime.now())
                .build()));
  }
}