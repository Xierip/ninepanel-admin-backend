package dev.nine.ninepanel.error;

import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

  private final ErrorAttributes errorAttributes;

  @Autowired
  ErrorController(ErrorAttributes errorAttributes) {
    this.errorAttributes = errorAttributes;
  }

  @Override
  public String getErrorPath() {
    return ApiLayers.ERROR;
  }

  @RequestMapping(ApiLayers.ERROR)
  public ErrorDto error(WebRequest webRequest, HttpServletResponse response) {
    Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, false);
    return new ErrorDto(response.getStatus(), errorAttributes.get("error").toString());
  }

}
