package com.app.purecookbook.purecookbook.index;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.app.purecookbook.purecookbook.R;
import com.app.purecookbook.purecookbook.cookbook.CookActivity;
import com.app.purecookbook.purecookbook.info.ClassifyLabelInfo;

public class CooksListAdapter extends BaseAdapter {


    private ClassifyLabelInfo classifyLabelInfo;
    private Context context;
    public CooksListAdapter(Context context, ClassifyLabelInfo classifyLabelInfo) {
        this.context = context;
        this.classifyLabelInfo = classifyLabelInfo;
    }

    @Override
    public int getCount() {
        return classifyLabelInfo.getResult().size();
    }

    @Override
    public Object getItem(int position) {
        return classifyLabelInfo.getResult().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListHolder listHolder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_books, parent, false);
            listHolder = new ListHolder();
            listHolder.textView = (TextView) convertView.findViewById(R.id.text_letter_item_books);
            listHolder.gridView = (GridView) convertView.findViewById(R.id.book_grid_view);
            Typeface face = Typeface.createFromAsset(context.getAssets(), "fangzhengxiyuanjian.ttf");
            listHolder.textView.setTypeface(face);
            convertView.setTag(listHolder);
            listHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int parentPosition = (int) listHolder.gridView.getTag();
                    Intent intent = new Intent(context, CookActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", Integer.parseInt(classifyLabelInfo.getResult().get(parentPosition).getList().get(position).getId()));
                    bundle.putString("title", classifyLabelInfo.getResult().get(parentPosition).getList().get(position).getName());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        else
        {
            listHolder = (ListHolder) convertView.getTag();
        }
        listHolder.textView.setText(classifyLabelInfo.getResult().get(position).getName());
        listHolder.gridView.setAdapter(new CooksGridAdapter(classifyLabelInfo.getResult().get(position)));
        listHolder.gridView.setTag(position);

        return convertView;
    }
    private class ListHolder
    {
        public TextView textView;
        public GridView gridView;
    }
    private class CooksGridAdapter extends BaseAdapter
    {
        private ClassifyLabelInfo.Result result;

        public CooksGridAdapter(ClassifyLabelInfo.Result result) {
            this.result = result;
        }

        @Override
        public int getCount() {
            return result.getList().size();
        }

        @Override
        public Object getItem(int position) {
            return result.getList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridHolder gridHolder;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, parent, false);
                gridHolder = new GridHolder();
                gridHolder.textView = (TextView) convertView.findViewById(R.id.item_grid_text);
                convertView.setTag(gridHolder);
            }
            else
            {
                gridHolder = (GridHolder) convertView.getTag();
            }
            gridHolder.textView.setText(result.getList().get(position).getName());
            return convertView;
        }
        private class GridHolder
        {
            public TextView textView;
        }
    }
}
