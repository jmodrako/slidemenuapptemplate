package com.template.menu;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 09.07.2013
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
public class MenuParent extends MenuItemImpl {


    protected MenuItem[] items;


    public MenuParent(int title, MenuItem[] items) {
        super(title);
        this.items = items;
    }


    public MenuItem[] getItems() {
        return items;
    }
}
