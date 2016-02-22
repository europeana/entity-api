package eu.europeana.entity.web.controller;

import org.springframework.stereotype.Controller;

import eu.europeana.api.common.config.swagger.SwaggerSelect;
import io.swagger.annotations.Api;

@Controller
@Api(tags = "Discovery API")
@SwaggerSelect
public class SearchController {

}
