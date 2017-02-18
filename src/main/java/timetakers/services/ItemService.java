/*
 * Copyright (c) 2016 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package timetakers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import timetakers.model.Item;
import timetakers.model.Record;
import timetakers.repository.RecordRepository;
import timetakers.repository.specification.RecordSpecification;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author David Liebl
 */

@Service
public class ItemService {

    private final RecordRepository recordRepository;

    @Autowired
    public ItemService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<Item> getLastUsedItems(Pageable pageable) {
        RecordSpecification specification = new RecordSpecification(SecurityService.getLoggedInPerson());
        final List<Record> records = recordRepository.findAll(specification, pageable).getContent();
        return records.stream().map(Record::getItem).distinct().collect(Collectors.toList());
    }

}
