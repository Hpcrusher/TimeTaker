/*
 * Copyright (c) 2016 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package timetakers.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Martin Geßenich
 * @author Christian Rehaag
 */

@Entity
@Table(name = "record")
public class Record extends AbstractIdEntity {

    @ManyToOne(optional = false, targetEntity = Item.class)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "comment")
    private String comment;

    @Column(name = "start_time")
    private LocalDateTime start;

    @Column(name = "end_time")
    private LocalDateTime end;

    public Record() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id = UUID.randomUUID();
        private Item item;
        private String comment;
        private LocalDateTime start;
        private LocalDateTime end;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder withItem(Item item) {
            this.item = item;
            return this;
        }

        public Builder withComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder withStart(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public Builder withEnd(LocalDateTime end) {
            this.end = end;
            return this;
        }

        public Record createRecord() {
            Record record = new Record();
            record.setId(id);
            record.setItem(item);
            record.setComment(comment);
            record.setStart(start);
            record.setEnd(end);
            return record;
        }
    }
}
