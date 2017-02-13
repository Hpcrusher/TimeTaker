/*
 * Copyright (c) 2017 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package timetakers.web.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import timetakers.model.Person;
import timetakers.model.Setting;
import timetakers.repository.SettingRepository;
import timetakers.services.SecurityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author David Liebl
 */

@Component
public class DBLocaleResolver implements LocaleResolver {

    private static final String SETTING_KEY = "locale";

    private final SettingRepository settingRepository;

    @Autowired
    public DBLocaleResolver(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        Person loggedInPerson = SecurityService.getLoggedInPerson();
        if (loggedInPerson == null) {
            return Locale.GERMANY;
        }
        Setting localeSetting = settingRepository.findByKeyAndPerson(SETTING_KEY, loggedInPerson);
        if (localeSetting == null) {
            localeSetting = settingRepository.save(Setting.builder().withKey(SETTING_KEY).withValue("de_DE").withPerson(loggedInPerson).build());
        }
        String[] locale = localeSetting.getValue().split("_");
        return new Locale(locale[0], locale[1]);
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
        Person loggedInPerson = SecurityService.getLoggedInPerson();
        if (loggedInPerson == null) {
            return;
        }
        Setting localeSetting = settingRepository.findByKeyAndPerson(SETTING_KEY, loggedInPerson);
        if (localeSetting == null) {
            settingRepository.save(Setting.builder().withKey(SETTING_KEY).withValue(locale.getLanguage() + "_" + locale.getCountry()).withPerson(loggedInPerson).build());
        } else {
            localeSetting.setValue( locale.getLanguage() + "_" + locale.getCountry());
            settingRepository.save(localeSetting);
        }
    }

}
