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
import timetakers.exception.ValidationRuntimeException;
import timetakers.model.Item;
import timetakers.model.Person;
import timetakers.model.Record;
import timetakers.repository.ItemRepository;
import timetakers.repository.RecordRepository;
import timetakers.repository.specification.RecordSpecification;
import timetakers.services.SecurityService;
import timetakers.util.TextKey;
import timetakers.web.assembler.RecordAssembler;
import timetakers.web.model.RecordDto;

import java.time.LocalDateTime;
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

    @Autowired
    public RecordController(RecordRepository recordRepository, RecordAssembler recordAssembler, ItemRepository itemRepository) {
        this.recordRepository = recordRepository;
        this.recordAssembler = recordAssembler;
        this.itemRepository = itemRepository;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public void createRecord( @RequestBody RecordDto recordDto ) {
        Item item = itemRepository.getOne(recordDto.item);
        Record record = Record.builder()
                .withComment(recordDto.comment)
                .withItem(item)
                .withStart(recordDto.start)
                .withEnd(recordDto.end)
                .createRecord();

        recordRepository.save(record);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public void deleteRecord( @RequestParam UUID id ) {
        recordRepository.delete(id);
    }

    @RequestMapping(value = "/udpate", method = RequestMethod.PATCH)
    @ResponseBody
    public void updateRecord( @RequestBody RecordDto recordDto ) {
        Record record = recordRepository.getOne(recordDto.oid);
        if ( record == null){
            throw new IllegalArgumentException("No matching record found");
        }
        record.setComment(recordDto.comment);
        record.setStart(recordDto.start);
        record.setEnd(recordDto.end);
        record.setItem(itemRepository.getOne(recordDto.item));
        recordRepository.save(record);
    }

    @RequestMapping(value = "/{id}/update/end", method = RequestMethod.PATCH)
    @ResponseBody
    public void updateRecordEndTime(@PathVariable UUID id, @RequestParam LocalDateTime endTime) {
        Record record = recordRepository.getOne(id);
        if (record == null) {
            throw new IllegalArgumentException( "Found no matching record");
        }
        record.setEnd(endTime);
        recordRepository.save(record);
    }

    @RequestMapping(value = "/running", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RecordDto isAnyRecordRunning(){
        Person person = SecurityService.getLoggedInPerson();
        List<Record> list = recordRepository.findAll(new RecordSpecification(person, true));
        if (list == null || list.size()==0){
            return null;
        }
        if (list.size() > 1){
            throw new ValidationRuntimeException(new TextKey("validation.toManyOpenRecord"),"");
        }
        return recordAssembler.toResource(list.get(0));
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<RecordDto> getRecordsAsJson() {
        return recordAssembler.toResources(recordRepository.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RecordDto getRecordWithIdAsJson(@PathVariable UUID id) {
        return recordAssembler.toResource(recordRepository.findOne(id));
    }
}
