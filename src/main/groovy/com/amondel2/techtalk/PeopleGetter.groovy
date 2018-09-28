package com.amondel2.techtalk

import pl.touk.excel.export.getters.PropertyGetter


class PeopleGetter extends PropertyGetter<List, String> {
    def index
    PeopleGetter(String propertyName) {
        super(propertyName)
        this.index = 0
    }

    PeopleGetter(String propertyName,String index) {
        super(propertyName)
        this.index = index
    }

    @Override
    protected Double format(List value) {
        return value.get(this.index.toInteger()) / 100 // you can do anything you like in here
    }
}
