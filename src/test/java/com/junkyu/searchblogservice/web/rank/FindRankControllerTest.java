package com.junkyu.searchblogservice.web.rank;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.junkyu.searchblogservice.annotation.ControllerTest;
import com.junkyu.searchblogservice.port.in.FindRankUseCase;
import com.junkyu.searchblogservice.web.rank.response.FindRankResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FindRankController.class)
@ControllerTest
class FindRankControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  FindRankUseCase findRankUseCase;

  private static final String DEFAULT_URI_PATH = "/v1/rank";

  @Nested
  @DisplayName("FindRankController 클래스의")
  class Describe_of_FindRankController {

    @Nested
    @DisplayName("findTop10Query 메소드는")
    class Context_with_findTop10Query {

      @Test
      @DisplayName("인기 검색어 Top10 결과를 반환한다.")
      void it_returns_page() throws Exception {

        given(findRankUseCase.findTop10Query())
            .willReturn(getFindRankResponse());

        mockMvc.perform(get(DEFAULT_URI_PATH + "/query/top10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk())
            .andDo(document("doc/rank/top10",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[]").type(ARRAY).description("검색 결과 목록"),
                    fieldWithPath("[].query").type(STRING).description("검색 쿼리"),
                    fieldWithPath("[].score").type(NUMBER).description("검색 횟수")
                )));
      }
    }
  }

  private List<FindRankResponse> getFindRankResponse() {
    return List.of(
        FindRankResponse.builder()
            .query("banana")
            .score(10)
            .build(),
        FindRankResponse.builder()
            .query("apple")
            .score(8)
            .build()
    );
  }
}