package com.chillingburnouts.smarthack.apis;

import com.chillingburnouts.smarthack.dtos.CompanyDto;
import com.chillingburnouts.smarthack.dtos.requests.search.SearchCompanyRequestVeridion;
import com.chillingburnouts.smarthack.dtos.responses.requests.SearchCompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "veridion", url = "${veridion.api.url.base}")
public interface VeridionClient {

    @PostMapping("/match/v4/companies")
    CompanyDto getData(@RequestBody CompanyDto data);

    @PostMapping("/search/v1/companies?page_size=10&pagination_token=")
    SearchCompanyResponse searchCompany(@RequestBody SearchCompanyRequestVeridion data);
}
