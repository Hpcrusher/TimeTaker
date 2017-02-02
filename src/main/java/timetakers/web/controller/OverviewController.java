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
import org.springframework.web.bind.annotation.*;
import timetakers.model.Item;
import timetakers.model.Record;
import timetakers.repository.ItemRepository;
import timetakers.repository.RecordRepository;
import timetakers.repository.specification.RecordSpecification;
import timetakers.services.SecurityService;
import timetakers.util.DateHelper;
import timetakers.web.assembler.RecordAssembler;
import timetakers.web.model.RecordDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


/**
 * @author Martin Geßenich
 */

@Controller
@Transactional
@RequestMapping(value = "/overview")
public class OverviewController {

    private RecordRepository recordRepository;
    private RecordAssembler recordAssembler;
    private ItemRepository itemRepository;

    @Autowired
    public OverviewController(RecordRepository recordRepository, RecordAssembler recordAssembler, ItemRepository itemRepository) {
        this.recordRepository = recordRepository;
        this.recordAssembler = recordAssembler;
        this.itemRepository = itemRepository;
    }

//    @RequestMapping(value = "/today", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//    public List<RecordDto> getPersonWithIdAsJson(@PathVariable UUID person) {
//        return recordRepository.findAll(new RecordSpecification(SecurityService.getLoggedInPerson(), DateHelper.getStartPointOf(LocalDateTime.now()), LocalDateTime.now(Clock.systemUTC())));
//    }
}