package com.lovotech.fr.gxld.cra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.Map;

@FeignClient(url = "${external.holidays.endpoint.url}", name = "holidaysClient")
public interface PublicHolidaysClient {

    @GetMapping("/{year}.json")
    Map<LocalDate, String> getAllPublicHolidays(@PathVariable(value = "year") String year);
}

