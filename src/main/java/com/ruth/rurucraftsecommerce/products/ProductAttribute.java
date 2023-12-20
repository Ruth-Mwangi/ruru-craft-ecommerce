package com.ruth.rurucraftsecommerce.products;

import com.ruth.rurucraftsecommerce.options.Option;
import com.ruth.rurucraftsecommerce.options.OptionGroup;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ProductAttribute {

    @ManyToOne(fetch = FetchType.EAGER)
    private OptionGroup optionGroup;
    @ManyToOne(fetch = FetchType.EAGER)
    private Option option;

    public ProductAttribute(OptionGroup optionGroup, Option option) {
        this.optionGroup = optionGroup;
        this.option = option;
    }

    public ProductAttribute() {
    }

    public OptionGroup getOptionGroup() {
        return optionGroup;
    }

    public void setOptionGroup(OptionGroup optionGroup) {
        this.optionGroup = optionGroup;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }
}
