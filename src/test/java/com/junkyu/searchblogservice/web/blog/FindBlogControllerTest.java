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
  @DisplayName("FindBlogController 클래스의")
  class Describe_of_FindBlogController {

    @Nested
    @DisplayName("Find 메소드는")
    class Context_with_find {

      @Nested
      @DisplayName("블로그 검색 요청을 받으면")
      class Context_with_FindBlogRequest {

        @Test
        @DisplayName("검색 결과를 페이징처리하여 반환한다.")
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
                      parameterWithName("query").description("블로그 검색 쿼리"),
                      parameterWithName("sort").description("결과 정렬 방식[accuracy | recency]"),
                      parameterWithName("page").description("결과 페이지 번호"),
                      parameterWithName("size").description("한 페이지에 보여질 문서 수")
                  ),
                  responseFields(
                      fieldWithPath("content[]").type(ARRAY).description("검색 결과 목록"),
                      fieldWithPath("content[].title").type(STRING).description("블로그 글 제목"),
                      fieldWithPath("content[].contents").type(STRING).description("블로그 글 요약"),
                      fieldWithPath("content[].url").type(STRING).description("블로그 글 URL"),
                      fieldWithPath("content[].blogName").type(STRING).description("블로그명"),
                      fieldWithPath("content[].dateTime").type(STRING).description("블로그 게시 날짜"),
                      fieldWithPath("pageable").type(STRING).description("페이징 설정 정보"),
                      fieldWithPath("sort").type(OBJECT).description("정렬 정보"),
                      fieldWithPath("sort.sorted").type(BOOLEAN).description("정렬 여부"),
                      fieldWithPath("sort.unsorted").type(BOOLEAN).description("미정렬 여부"),
                      fieldWithPath("sort.empty").type(BOOLEAN).description("정렬 객체 공백 여부"),
                      fieldWithPath("totalPages").type(NUMBER).description("모든 페이지 개수"),
                      fieldWithPath("totalElements").type(NUMBER).description("모든 엘리먼트 개수"),
                      fieldWithPath("size").type(NUMBER).description("페이징 사이즈"),
                      fieldWithPath("numberOfElements").type(NUMBER).description("현재 페이지 내 엘리먼트 개수"),
                      fieldWithPath("number").type(NUMBER).description("페이지 번호"),
                      fieldWithPath("first").type(BOOLEAN).description( "첫번째 페이지 여부"),
                      fieldWithPath("last").type(BOOLEAN).description( "마지막 페이지 여부"),
                      fieldWithPath("empty").type(BOOLEAN).description("페이지가 비어있는지 여부")
                  )));
        }
      }
    }
  }

  private Page<FindBlogResponse> getFindBlogResponse() {
    return new PageImpl<>(
        List.of(
            FindBlogResponse.builder()
                .title("블로그 글 제목")
                .contents("블로그 요약")
                .url("블로그 URL")
                .blogName("블로그명")
                .dateTime(LocalDateTime.now())
                .build()));
  }
}