package com.lovotech.fr.gxld.cra.services;

import com.lovotech.fr.gxld.core.bean.cra.DTO.PageDTO;
import com.lovotech.fr.gxld.core.bean.cra.common.ConventionEnum;
import com.lovotech.fr.gxld.core.bean.cra.common.SearchCriteria;
import com.lovotech.fr.gxld.core.bean.cra.common.SearchOperationEnum;
import com.lovotech.fr.gxld.core.bean.cra.common.TimesheetStatusEnum;

import com.lovotech.fr.gxld.core.bean.cra.domain.PublicHoliday;
import com.lovotech.fr.gxld.core.bean.cra.domain.Timesheet;
import com.lovotech.fr.gxld.core.bean.cra.domain.UserProfile;
import com.lovotech.fr.gxld.core.bean.cra.domain.WorkingDay;
import com.lovotech.fr.gxld.core.bean.cra.exception.TechnicalException;
import com.lovotech.fr.gxld.cra.repositories.PublicHolidayRepository;
import com.lovotech.fr.gxld.cra.repositories.TimesheetRepository;
import com.lovotech.fr.gxld.cra.repositories.spec.TimesheetSpecification;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.NonNull;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final UserProfileService userProfileService;
    private final PublicHolidayRepository publicHolidayRepository;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public TimesheetService(TimesheetRepository timesheetRepository, UserProfileService userProfileService,
            PublicHolidayRepository publicHolidayRepository) {
        this.timesheetRepository = timesheetRepository;
        this.userProfileService = userProfileService;
        this.publicHolidayRepository = publicHolidayRepository;
    }

    public PageDTO<Timesheet> getByDate(LocalDate date,Pageable pageable) {
        return new PageDTO<Timesheet>(timesheetRepository.getByDate(date,pageable));
    }

    public Timesheet initTimesheet(final Long userId, final LocalDate date) throws TechnicalException {
        final int daysInMonth = YearMonth.of(date.getYear(), date.getMonthValue()).lengthOfMonth();
        final UserProfile userProfile = userProfileService.getuserProfile(userId);
        return Timesheet.builder().date(date).userProfile(userProfile)
                .convention(Objects.isNull(userProfile.getAuthUser().getConvention()) ? ConventionEnum.SYNTEC : userProfile.getAuthUser().getConvention())
                .workingDays(prepareWorkingDays(date, daysInMonth, userProfile)).status(TimesheetStatusEnum.DRAFT).build();
    }

    public Timesheet saveOrUpdate(Timesheet timesheet) {
        timesheet.getWorkingDays().forEach(workingDay -> workingDay.setTimesheet(timesheet));
        return timesheetRepository.save(timesheet);
    }

    public Optional<Timesheet> getTimeSheetByUserIdAndDate(Long userId, LocalDate date) {
        UserProfile userProfile = userProfileService.getuserProfile(userId);
        return timesheetRepository.getTimeSheetByUserProfileIdAndDate(userProfile.getId(), date);
    }

    private List<WorkingDay> prepareWorkingDays(LocalDate date, int daysInMonth, UserProfile userProfile) {
        List<PublicHoliday> publicHolidays = publicHolidayRepository.findAllByDateBetweenOrderByDateAsc(date,
                date.plusDays(daysInMonth - 1));
        return IntStream.range(0, daysInMonth)
                .mapToObj(operand -> WorkingDay.builder().date(date.plusDays(operand))
                        .publicHoliday(publicHolidays.stream()
                                .filter(publicHoliday -> date.plusDays(operand).equals(publicHoliday.getDate()))
                                .findFirst().orElse(null))
                        .dayOfWeek(date.plusDays(operand).getDayOfWeek().getValue())
                        .workedHoursNumber(Objects.isNull(userProfile.getAuthUser().getConvention()) ? ConventionEnum.SYNTEC.value
                                : userProfile.getAuthUser().getConvention().value)
                        .build())
                .collect(Collectors.toList());
    }
    public PageDTO<Timesheet>getAllByFilter(@NonNull Map<String,String> inputs, Pageable pageable,LocalDate date) {
        Map<String,String> filters =  inputs.entrySet().stream().filter(i->i.getValue()!=null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if(filters.isEmpty()) return this.getByDate(date,pageable);
        List<Specification> specs = new ArrayList<>();
        filters.forEach((key,value)->{
            if(value.length()==0){
                return;
            }
           else if(isBoolean(value)){
                    SearchCriteria searchCriteria = new SearchCriteria(key, SearchOperationEnum.EQUALITY, Boolean.valueOf(value));
                    specs.add(new TimesheetSpecification(searchCriteria) {
                    });
            }
            else if(isDate(value)){
                SearchCriteria searchCriteria = new SearchCriteria(key, SearchOperationEnum.LIKE ,value);
                specs.add(new TimesheetSpecification(searchCriteria) {
                });
            }
           else if(NumberUtils.isParsable(value)){
                SearchCriteria searchCriteria = new SearchCriteria(key, SearchOperationEnum.STARTS_WITH ,value);
                specs.add(new TimesheetSpecification(searchCriteria) {
                });
            }
           else if(key.contains("_")){
                SearchCriteria searchCriteria = new SearchCriteria(key, SearchOperationEnum.JOIN, value);
                specs.add(new TimesheetSpecification(searchCriteria));
            }
            else {
                SearchCriteria searchCriteria = new SearchCriteria(key, SearchOperationEnum.CONTAINS, value);
                specs.add(new TimesheetSpecification(searchCriteria));
            }
        });
        if (specs.size()==0) return this.getByDate(date,pageable);
        SearchCriteria searchCriteria = new SearchCriteria("date", SearchOperationEnum.LIKE ,date);
        specs.add(new TimesheetSpecification(searchCriteria) {
        });
        final Specification[] result = {specs.get(0)};
        specs.stream().skip(1).forEach(specification -> result[0] =Specification.where(result[0]).and(specification));
        return new PageDTO<Timesheet>(timesheetRepository.findAll(result[0],pageable));
    }
    private boolean isDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, this.dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
    private boolean isBoolean(String val){
        val = val.toLowerCase();
        return (val.equals("true") || val.equals("false"));
    }
}
