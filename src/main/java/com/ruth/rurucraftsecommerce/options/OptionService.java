package com.ruth.rurucraftsecommerce.options;

import java.util.List;

public interface OptionService {

    List<Option> getAllOptions();
    Option getOptionById(Integer id);

    Option updateOption(Integer id,Option option);
    Option createOption(Option option);

    boolean deleteOptionById(Integer id);


    List<OptionGroup> getAllOptionGroups();
    OptionGroup getOptionGroupById(Integer id);

    OptionGroup updateOptionGroup(Integer id,OptionGroup optionGroup);
    OptionGroup createOptionGroup(OptionGroup optionGroup);

    boolean deleteOptionGroupById(Integer id);
}
