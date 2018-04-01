package com.github.lit.dictionary.config;

import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.tool.DictionaryTools;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * User : liulu
 * Date : 2018/4/1 15:34
 * version $Id: DictionaryFreemarkerTools.java, v 0.1 Exp $
 */
public class DictionaryDirectiveModel implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Object dictKey = params.get("dictKey");
        if (StringUtils.isEmpty(dictKey)) {
            return;
        }

        String[] dictKeys = StringUtils.tokenizeToStringArray(dictKey.toString(), ",");

        List<Dictionary> dictionaries = DictionaryTools.findChildByKeys(dictKeys);

        DefaultObjectWrapper objectWrapper = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_23).build();

        env.setVariable("dictionaries", objectWrapper.wrap(dictionaries));
        body.render(env.getOut());

    }


}
