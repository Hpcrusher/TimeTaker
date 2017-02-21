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
import timetakers.model.Item;
import timetakers.web.model.ItemDto;

/**
 * Created by Martin Geßenich on 16.01.2017.
 */

@Component
public class ItemAssembler extends ResourceAssemblerSupport<Item, ItemDto> {

    public ItemAssembler() {
        super(ItemAssembler.class, ItemDto.class);
    }

    @Override
    public ItemDto toResource(Item item) {
        if (item == null) {
            return null;
        }
        ItemDto dto  = new ItemDto();
        dto.oid = item.getId();
        dto.title = item.getTitle();
        dto.person = item.getPerson().getId();
        dto.father = item.getFather() == null ? null : item.getFather().getId();
        final Item father = item.getFather();
        if (father != null) {
            dto.fatherTitle = father.getTitle();
        }
        dto.color = item.getColor() == null ? null : item.getColor();
        return dto;
    }
}