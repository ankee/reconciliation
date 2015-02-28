package com.sample.problem1.model;

import com.sample.problem1.core.Identifiable;

/**
 * Created by aagarwal1 on 2/28/2015.
 */
public class Item  implements Identifiable {

    private Integer id;

    private String name;

    private String data;

    private Item(Integer id, String name, String data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public static ItemBuilder getItemBuilder(){
       return new ItemBuilder();
    }

    public static class ItemBuilder {

        private Integer id;

        private String name;

        private String data;

        public ItemBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public ItemBuilder setName(String name) {
            this.name = name;
            return this;

        }

        public ItemBuilder setData(String data) {
            this.data = data;
            return this;
        }

        public Item build(){
            return new Item(id, name, data);
        }
    }
}
