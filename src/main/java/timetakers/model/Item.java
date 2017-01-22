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

import timetakers.repository.converter.ColorConverter;

import javax.persistence.*;
import java.awt.*;
import java.util.UUID;

/**
 * @author Martin Geßenich
 * @author Christian Rehaag
 */

@Entity
@Table(name = "item")
public class Item extends AbstractIdEntity {

    @Column(name = "title")
    private String title;

    @ManyToOne(targetEntity = Item.class)
    @JoinColumn(name = "father_id")
    private Item father;

    @Column(name = "color")
    @Convert(converter = ColorConverter.class)
    private Color color;

    public Item() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Item getFather() {
        return father;
    }

    public void setfather(Item father) {
        this.father = father;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id = UUID.randomUUID();
        private String title;
        private Item father;
        private Color color;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withFather(Item father) {
            this.father = father;
            return this;
        }

        public Builder withColor(Color color) {
            this.color = color;
            return this;
        }

        public Item createItem() {
            Item item = new Item();
            item.setId(id);
            item.setTitle(title);
            item.setfather(father);
            item.setColor(color);
            return item;
        }
    }
}
