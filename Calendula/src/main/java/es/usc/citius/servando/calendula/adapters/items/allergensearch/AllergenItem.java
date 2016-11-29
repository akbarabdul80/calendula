package es.usc.citius.servando.calendula.adapters.items.allergensearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import es.usc.citius.servando.calendula.R;
import es.usc.citius.servando.calendula.allergies.AllergenVO;

/**
 * Created by alvaro.brey.vilas on 22/11/16.
 */

public class AllergenItem extends AbstractItem<AllergenItem, AllergenItem.ViewHolder> implements Comparable<AllergenItem> {

    private String allergenType;
    private AllergenVO vo;
    private ViewHolder holder;

    protected String title;
    private SpannableStringBuilder titleSpannable;

    public String getTitle() {
        return title;
    }

    public void setTitleSpannable(SpannableStringBuilder titleSpannable) {
        this.titleSpannable = titleSpannable;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AllergenVO getVo() {
        return vo;
    }

    public AllergenItem(AllergenVO vo, Context context) {
        this.title = vo.getName();
        switch (vo.getType()) {
            case ACTIVE_INGREDIENT:
                allergenType = context.getString(R.string.active_ingredient);
                break;
            case EXCIPIENT:
                allergenType = context.getString(R.string.excipient);
                break;
        }
        this.vo = vo;
    }

    @Override
    public int getType() {
        return R.id.fastadapter_allergen_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.allergen_search_list_item;
    }

    @Override
    public void bindView(ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);
        this.holder = viewHolder;
        viewHolder.title.setText(titleSpannable != null ? titleSpannable : title);
        viewHolder.subtitle.setText(allergenType);
    }

    //reset the view here (this is an optional method, but recommended)
    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.title.setText(null);
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    public int compareTo(AllergenItem o) {
        return this.title.compareTo(o.title);
    }


    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected final TextView subtitle;
        protected TextView title;

        public ViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.text1);
            this.subtitle = (TextView) view.findViewById(R.id.text2);
        }
    }
}