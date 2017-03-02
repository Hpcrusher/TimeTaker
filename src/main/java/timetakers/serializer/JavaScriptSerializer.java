/*
 * Copyright (c) 2016 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package timetakers.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.standard.serializer.IStandardJavaScriptSerializer;

import java.io.IOException;
import java.io.Writer;

/**
 * @author David Liebl
 */
public class JavaScriptSerializer implements IStandardJavaScriptSerializer {


    private final ObjectMapper mapper;

    JavaScriptSerializer(ObjectMapper mapper) {
        super();
        if (mapper == null) {
            this.mapper = new Jackson2ObjectMapperBuilder()
                    .indentOutput(true)
                    .failOnEmptyBeans(false)
                    .simpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .build();
        } else {
            this.mapper = mapper.copy();
        }

        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.mapper.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
        this.mapper.enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        this.mapper.getFactory().setCharacterEscapes(new JacksonThymeleafCharacterEscapes());
    }

    public void serializeValue(final Object object, final Writer writer) {
        try {
            mapper.writeValue(writer, object);
        } catch (final IOException e) {
            throw new TemplateProcessingException(
                    "An exception was raised while trying to serialize object to JavaScript using Jackson", e);
        }
    }

    /*
     * This CharacterEscapes implementation makes sure that the slash ('/') and ampersand ('&') characters
     * are also escaped, which is not standard Jackson behaviour.
     *
     * Escaping '/' covers against the possible premature closing of <script> tags inside inlined JavaScript
     * literals, thus preventing code injection in templates being processed by browsers as HTML.
     *
     * Escaping '&' covers against the injection of XHTML-escaped code which might prematurely close the
     * inlined JavaScript literals and even the container <script> tags in templates being processed
     * by browsers as XHTML.
     *
     * Note that, unfortunately Jackson's escape customization mechanism offers no way to only escape '/' when it
     * is preceded by '<', so that only '</' is escaped. Therefore, all '/' need to be escaped. Which is a
     * difference with the default Unbescape-based mechanism.
     */
    private static final class JacksonThymeleafCharacterEscapes extends CharacterEscapes {

        private static final int[] CHARACTER_ESCAPES;
        private static final SerializableString SLASH_ESCAPE;
        private static final SerializableString AMPERSAND_ESCAPE;

        static {

            CHARACTER_ESCAPES = CharacterEscapes.standardAsciiEscapesForJSON();
            CHARACTER_ESCAPES['/'] = CharacterEscapes.ESCAPE_CUSTOM;
            CHARACTER_ESCAPES['&'] = CharacterEscapes.ESCAPE_CUSTOM;

            SLASH_ESCAPE = new SerializedString("\\/");
            AMPERSAND_ESCAPE = new SerializedString("\\u0026");

        }

        JacksonThymeleafCharacterEscapes() {
            super();
        }

        @Override
        public int[] getEscapeCodesForAscii() {
            return CHARACTER_ESCAPES.clone();
        }

        @Override
        public SerializableString getEscapeSequence(final int ch) {
            if (ch == '/') {
                return SLASH_ESCAPE;
            }
            if (ch == '&') {
                return AMPERSAND_ESCAPE;
            }
            return null;
        }

    }
}
