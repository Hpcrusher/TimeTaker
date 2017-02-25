/*
 * Copyright (c) 2016 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package timetakers.web.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import timetakers.model.Record;
import timetakers.web.model.RecordDto;

import java.time.format.DateTimeFormatter;

/**
 * @author Martin Geßenich
 */

@Component
public class RecordAssembler extends ResourceAssemblerSupport<Record, RecordDto> {

    public RecordAssembler() {
        super(RecordAssembler.class, RecordDto.class);
    }

    @Override
    public RecordDto toResource(Record record) {
        if (record == null){
            return null;
        }
        RecordDto dto  = new RecordDto();
        dto.oid = record.getId();
        dto.comment = record.getComment();
        dto.itemId = record.getItem().getId();
        dto.start = record.getStart();
        dto.end = record.getEnd();
        if (dto.end != null) {
            DateTimeFormatter formatter;
            if (dto.start.getDayOfYear() == dto.end.getDayOfYear()){
                formatter = DateTimeFormatter.ofPattern("HH:mm");
            } else {
                formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            }
            dto.startTimeString = dto.start.format(formatter);
            dto.endTimeString = dto.end.format(formatter);
        }
        return dto;
    }
}