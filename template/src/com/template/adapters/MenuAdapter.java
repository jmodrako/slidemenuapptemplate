package com.template.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.template.R;
import com.template.menu.MenuItem;
import com.template.menu.MenuParent;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 09.07.2013
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */

public class MenuAdapter extends ArrayAdapter<MenuItem> {

    private static final int MENU_ITEM = 0;
    private static final int MENU_PARENT = 1;
    MenuParent[] menuParents;
    LayoutInflater inflater;
    Context context;
    MenuItem current;

    public MenuAdapter(Context context, MenuParent[] menuParents) {
        super(context, R.layout.menu_item, menuParents);
        this.menuParents = menuParents;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == MENU_ITEM) {
            HolderItem holderItem = null;
            if (convertView == null) {
                holderItem = new HolderItem();
                convertView = inflater.inflate(R.layout.menu_item, parent, false);
                holderItem.icon = (ImageView) convertView.findViewById(R.id.item_icon);
                holderItem.text = (TextView) convertView.findViewById(R.id.item_text);
                holderItem.radio = (RadioButton) convertView.findViewById(R.id.item_radio);

                convertView.setTag(holderItem);
            } else {
                holderItem = (HolderItem) convertView.getTag();
            }

            MenuItem menuItem = getItem(position);
            holderItem.text.setText(menuItem.getTitle());

            if (menuItem.isCheckable()) {
                convertView.setBackgroundColor(Color.WHITE);
                holderItem.radio.setVisibility(View.VISIBLE);
                holderItem.icon.setVisibility(View.VISIBLE);
                holderItem.radio.setChecked(menuItem.isChecked());
                holderItem.text.setTextColor(context.getResources().getColor(R.color.text_gray));
                ImageLoader.getInstance().displayImage(menuItem.getLogoUrl(), holderItem.icon);
            } else {

                if (menuItem.equals(current)) {
                    convertView.setBackgroundColor(context.getResources().getColor(R.color.menu_active_elem));
                    holderItem.text.setTextColor(Color.WHITE);
                } else {
                    int bottom = convertView.getPaddingBottom();
                    int top = convertView.getPaddingTop();
                    int right = convertView.getPaddingRight();
                    int left = convertView.getPaddingLeft();
                    convertView.setBackgroundResource(R.drawable.menu_item);
                    convertView.setPadding(left, top, right, bottom);

                    holderItem.text.setTextColor(context.getResources().getColorStateList(R.color.menu_item_text));
                }

                holderItem.radio.setVisibility(View.GONE);
                holderItem.icon.setVisibility(View.GONE);
            }
        } else {
            HolderParent holderParent = null;
            if (convertView == null) {
                holderParent = new HolderParent();
                convertView = inflater.inflate(R.layout.menu_parent, parent, false);
                holderParent.text = (TextView) convertView.findViewById(R.id.item_text);
                convertView.setTag(holderParent);
            } else {
                holderParent = (HolderParent) convertView.getTag();
            }

            MenuItem menuItem = getItem(position);
            holderParent.text.setText(menuItem.getTitle().toUpperCase());

        }


        return convertView;
    }

    @Override
    public MenuItem getItem(int position) {

        int count = 0;
        for (MenuParent menuParent : menuParents) {
            if (count == position) {
                return menuParent;
            }
            count += menuParent.getItems().length;

            if (count >= position) {
                return menuParent.getItems()[menuParent.getItems().length - (count - position) - 1];
            }
            count++;
        }

        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int count = 0;
        for (MenuParent menuParent : menuParents) {
            if (count == position) {
                return MENU_PARENT;
            }

            count += menuParent.getItems().length;

            if (count >= position)
                break;
            count++;
        }
        return MENU_ITEM;
    }

    @Override
    public int getCount() {
        int count = 0;
        for (MenuParent menuParent : menuParents) {
            count += menuParent.getItems().length;
        }
        return count + menuParents.length;
    }

    public void resetItem() {
        for (MenuParent menuParent : menuParents) {
            for (MenuItem item : menuParent.getItems()) {
                if (!item.isCheckable()) {
                    current = item;
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    public void setItem(Class<? extends Fragment> fragment) {
        for (MenuParent menuParent : menuParents) {
            for (MenuItem item : menuParent.getItems()) {
                Fragment itemFragment = item.getFragment();
                if (itemFragment != null && itemFragment.getClass().equals(fragment)) {
                    setItem(item);
                }
            }
        }
    }

    public void setItem(MenuItem current) {
        this.current = current;
        notifyDataSetChanged();
    }

    public MenuItem getCurrent() {
        return current;
    }

    static class HolderItem {

        ImageView icon;
        TextView text;
        RadioButton radio;
    }

    static class HolderParent {

        TextView text;
    }
}
