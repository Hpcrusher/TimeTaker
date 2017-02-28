/*
 * Copyright (c) 2016 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package timetakers.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import timetakers.model.Person;
import timetakers.model.Record;
import timetakers.repository.ItemRepository;
import timetakers.repository.RecordRepository;
import timetakers.repository.specification.RecordSpecification;
import timetakers.services.SecurityService;
import timetakers.util.DateHelper;
import timetakers.web.assembler.RecordOverviewAssembler;
import timetakers.web.model.RecordDto;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;


/**
 * @author Martin Geßenich
 */

@Controller
@Transactional
@RequestMapping(value = "/overview")
public class OverviewController {

    private RecordRepository recordRepository;
    private RecordOverviewAssembler recordOverviewAssembler;

    @Autowired
    public OverviewController(RecordRepository recordRepository, ItemRepository itemRepository, RecordOverviewAssembler recordOverviewAssembler) {
        this.recordRepository = recordRepository;
        this.recordOverviewAssembler = recordOverviewAssembler;
    }

    @RequestMapping(value = "/today", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getTodaysSummaryOfRecords() {
        ModelAndView modelAndView = new ModelAndView("overview");

        /* calculate sum of time that user has worked today */
        Person loggedInPerson = SecurityService.getLoggedInPerson();
        List<Record> listOfTodaysRecords = recordRepository.findAll(
                new RecordSpecification(
                        loggedInPerson,
                        DateHelper.getStartOfToday(),
                        LocalDateTime.now(Clock.systemUTC())
                )
        );
        long sum = 0;
        for (Record record : listOfTodaysRecords) {
            sum += (record.getEnd().toEpochSecond(ZoneOffset.UTC) - record.getStart().toEpochSecond(ZoneOffset.UTC));
        }
        Duration duration = Duration.ofSeconds(sum);
        StringBuilder sumStringBuilder = new StringBuilder();
        if (duration.toHours() > 0) {
            if (duration.toHours() < 10) {
                sumStringBuilder.append('0');
            }
            sumStringBuilder.append(duration.toHours()).append(":");
        }
        if (duration.toMinutes() % 60 < 10) {
            sumStringBuilder.append('0');
        }
        sumStringBuilder.append(duration.toMinutes() % 60).append(":");
        if (duration.getSeconds() % 60 < 10) {
            sumStringBuilder.append('0');
        }
        sumStringBuilder.append(duration.getSeconds() % 60).toString();
        modelAndView.addObject("sum", sumStringBuilder.toString());
        return modelAndView;
    }

    @RequestMapping(value = "/today", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RecordDto> getTodaysSummaryOfRecordsAsJSON() {
        Person loggedInPerson = SecurityService.getLoggedInPerson();
        return recordOverviewAssembler.toResources(recordRepository.findAll(
                new RecordSpecification(
                        loggedInPerson,
                        DateHelper.getStartOfToday(),
                        LocalDateTime.now(Clock.systemUTC())
                ))
        );
    }
}
