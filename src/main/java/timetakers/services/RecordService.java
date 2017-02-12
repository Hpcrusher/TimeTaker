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
import org.springframework.stereotype.Service;
import timetakers.model.Person;
import timetakers.model.Record;
import timetakers.repository.RecordRepository;
import timetakers.repository.specification.RecordSpecification;
import timetakers.util.DateHelper;

import java.util.List;

/**
 * @author David Liebl
 */

@Service
public class RecordService {

    private RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public Record getRunniningRecord(Person person) {
        List<Record> records = recordRepository.findAll(new RecordSpecification(person, true));
        if (records.size() == 0) {
            return null;
        } else if (records.size() == 1) {
            return records.get(0);
        } else {
            // if there schould be more than one open record all will be closed
            records.forEach(record -> {
                record.setEnd(DateHelper.now());
                recordRepository.save(record);
            });
            return null;
        }
    }

}
