package timetakers.web.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import timetakers.model.Record;
import timetakers.web.model.RecordDto;

/**
 * Created by Martin Geßenich on 14.02.2017.
 */
@Component
public class RecordOverviewAssembler extends RecordAssembler {

    @Autowired
    ItemAssembler itemAssembler;

    public RecordOverviewAssembler() {
        super();
    }

    @Override
    public RecordDto toResource(Record record) {

        RecordDto recordDto = super.toResource(record);
        recordDto.item = itemAssembler.toResource( record.getItem());
        return recordDto;
    }
}
