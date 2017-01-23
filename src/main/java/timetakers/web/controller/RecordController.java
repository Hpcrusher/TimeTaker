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
import timetakers.services.SecurityService;
import timetakers.web.assembler.RecordAssembler;
import timetakers.web.model.RecordDto;

import java.util.List;
import java.util.UUID;


/**
 * @author Martin Geßenich
 */

@Controller
@Transactional
@RequestMapping(value = "/record")
public class RecordController {

    private RecordRepository recordRepository;
    private RecordAssembler recordAssembler;
    private ItemRepository itemRepository;
    private SecurityService securityService;

    @Autowired
    public RecordController(RecordRepository recordRepository, RecordAssembler recordAssembler, ItemRepository itemRepository, SecurityService securityService) {
        this.recordRepository = recordRepository;
        this.recordAssembler = recordAssembler;
        this.itemRepository = itemRepository;
        this.securityService = securityService;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public void createItem( @RequestBody RecordDto recordDto ) {
        Item item = itemRepository.getOne(recordDto.item);
        Record record = Record.builder()
                .withComment(recordDto.comment)
                .withItem(item)
                .withPerson(securityService.getLoggedInPerson())
                .withStart(recordDto.start)
                .withEnd(recordDto.end)
                .createRecord();

        recordRepository.save(record);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<RecordDto> getPersonsAsJson() {
        return recordAssembler.toResources(recordRepository.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RecordDto getPersonWithIdAsJson(@PathVariable UUID id) {
        return recordAssembler.toResource(recordRepository.findOne(id));
    }

}
