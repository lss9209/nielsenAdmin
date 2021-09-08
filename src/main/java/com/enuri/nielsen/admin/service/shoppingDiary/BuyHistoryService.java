package com.enuri.nielsen.admin.service.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Column;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.SearchMode;
import com.enuri.nielsen.admin.domain.shoppingDiary.formDto.SearchInputForm;
import com.enuri.nielsen.admin.domain.shoppingDiary.viewDto.SearchResult;
import com.enuri.nielsen.admin.exception.shoppingDiary.NormalDateOutOfBoundForConvertingToIndexDateException;
import com.enuri.nielsen.admin.repository.shoppingDiary.BuyHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuyHistoryService {

    @Autowired
    BuyHistoryRepository buyHistoryRepository;

    public Page<SearchResult> search(SearchInputForm searchInputForm, Pageable pageable) {
        checkIfAggregationQuerySearch(searchInputForm);
        checkAllIfNothingSelected(searchInputForm);
        Page<SearchResult> searchResult = buyHistoryRepository.search(searchInputForm, pageable);
        return searchResult;
    }

    private void checkIfAggregationQuerySearch(SearchInputForm searchInputForm) {
        if(searchInputForm.getAggregation() != null) {
            searchInputForm.setSearchMode(SearchMode.AGGREGATION);
        } else {
            searchInputForm.setSearchMode(SearchMode.NORMAL);
        }
    }

    private void checkAllIfNothingSelected(SearchInputForm searchInputForm) {
        Set<Column> selectedColumnsSet = searchInputForm.getSelectedColumnSet();
        if(selectedColumnsSet.size() == 0) {
            for(Column column : Column.values()) {
                selectedColumnsSet.add(column);
            }
        }
    }

    public LocalDate getIndexDateWithGivenNormalDate(String normalDate, String startDateOrEndDate) throws NormalDateOutOfBoundForConvertingToIndexDateException {
        String switchedIndexDateStr = buyHistoryRepository.getIndexDateWithGivenNormalDate(normalDate);
        StringBuilder switchedIndexDateStrFormattedToDateStyle = parseSwitchedIndexDateStringForConvertingToDate(switchedIndexDateStr, startDateOrEndDate);
        LocalDate switchedIndexDate = convertFormattedStrToDateType(switchedIndexDateStrFormattedToDateStyle);
        return switchedIndexDate;
    }

    private StringBuilder parseSwitchedIndexDateStringForConvertingToDate(String switchedIndexDateStr, String startDateOrEndDate) throws NormalDateOutOfBoundForConvertingToIndexDateException {
        if(switchedIndexDateStr == null) throw new NormalDateOutOfBoundForConvertingToIndexDateException();
        String switchedIndexDateYearStr = switchedIndexDateStr.substring(0,4);
        String switchedIndexDateMonthStr = switchedIndexDateStr.substring(6,8);
        String switchedIndexDateDayStr = startDateOrEndDate.equals("startIndexDate") ? "01" : getEndDayOfMonth(switchedIndexDateYearStr, switchedIndexDateMonthStr);
        StringBuilder switchedIndexDateStrFormattedToDateStyle = new StringBuilder();
        switchedIndexDateStrFormattedToDateStyle.append(switchedIndexDateYearStr);
        switchedIndexDateStrFormattedToDateStyle.append("-");
        switchedIndexDateStrFormattedToDateStyle.append(switchedIndexDateMonthStr);
        switchedIndexDateStrFormattedToDateStyle.append("-");
        switchedIndexDateStrFormattedToDateStyle.append(switchedIndexDateDayStr);
        return switchedIndexDateStrFormattedToDateStyle;
    }

    private LocalDate convertFormattedStrToDateType(StringBuilder switchedIndexDateStrFormattedToDateStyle) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate switchedIndexDate = LocalDate.parse(switchedIndexDateStrFormattedToDateStyle, formatter);
        return switchedIndexDate;
    }

    private String getEndDayOfMonth(String switchedIndexDateYearStr, String switchedIndexDateMonthStr) {
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(switchedIndexDateYearStr);
        int month = Integer.parseInt(switchedIndexDateMonthStr);
        cal.set(year, month, 10);
        return String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    }
}
