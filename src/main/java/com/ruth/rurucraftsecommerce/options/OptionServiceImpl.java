package com.ruth.rurucraftsecommerce.options;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
public class OptionServiceImpl implements OptionService{
    public  Locale locale = LocaleContextHolder.getLocale();


    @Autowired
    MessageSource messageSource;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Override
    public List<Option> getAllOptions() {

        return optionRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public Option getOptionById(Integer id) {
        Locale locale = LocaleContextHolder.getLocale();
        return optionRepository.findById(id).orElseThrow(()->new NoSuchElementException(messageSource.getMessage("get.fail.message",new Object[]{Option.class.getSimpleName(), id}, locale)));
    }

    @Override
    public Option updateOption(Integer id, Option option) {
        Option retrievedOption=getOptionById(id);
        retrievedOption.setName(option.getName());
        retrievedOption.setOptionGroup(option.getOptionGroup());
        return optionRepository.save(retrievedOption);


    }

    @Override
    public Option createOption(Option option) {
        return optionRepository.save(option);
    }

    @Override
    public boolean deleteOptionById(Integer id) {
        Option retrievedOption=getOptionById(id);
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());
        retrievedOption.setDeletedAt(convertedDate);
        Option savedOption=optionRepository.save(retrievedOption);
        return true;
    }

    @Override
    public List<OptionGroup> getAllOptionGroups() {
        return optionGroupRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public OptionGroup getOptionGroupById(Integer id) {
        Locale locale = LocaleContextHolder.getLocale();
        return optionGroupRepository.findById(id).
                orElseThrow(()->new NoSuchElementException(messageSource.getMessage("get.fail.message",new Object[]{OptionGroup.class.getSimpleName(), id}, locale)));
    }

    @Override
    public OptionGroup updateOptionGroup(Integer id, OptionGroup optionGroup) {
        OptionGroup retrievedOptionGroup=getOptionGroupById(id);
        retrievedOptionGroup.setName(optionGroup.getName());
        return optionGroupRepository.save(retrievedOptionGroup);
    }

    @Override
    public OptionGroup createOptionGroup(OptionGroup optionGroup) {
        return optionGroupRepository.save(optionGroup);
    }

    @Override
    public boolean deleteOptionGroupById(Integer id) {
        OptionGroup retrievedOptionGroup=getOptionGroupById(id);
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());
        retrievedOptionGroup.setDeletedAt(convertedDate);
        OptionGroup savedOptionGroup=optionGroupRepository.save(retrievedOptionGroup);
        return true;
    }
}
