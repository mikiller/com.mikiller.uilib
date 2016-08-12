package com.uilib.spannableeditbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uilib.R;

import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by Mikiller on 2016/7/6.
 */
public class SpannableEditbox extends LinearLayout implements View.OnClickListener{

    private boolean clickableSpan;
    private boolean bold;
    private boolean italic;
    private boolean underLine;
    private boolean foregroundColor;
    private boolean needToolbar;

    private RichEditor editText;
    private ImageView undo, redo, btnBold, btnItalic, btnUnderLine;//, btnForegroundColor;
    private LinearLayout toolbar;

    private boolean isBold = false, isItalic = false, isUnderLine = false;
    private int textColor = Color.BLACK;

//    private String content;

    public SpannableEditbox(Context context) {
        super(context);
    }

    public SpannableEditbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomAttr(context, attrs, 0);
        initView(context);
    }

    public SpannableEditbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomAttr(context, attrs, defStyleAttr);
        initView(context);
    }

    private void setCustomAttr(Context context, AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpannableEditbox ,defStyleAttr, 0);
        for(int i = 0; i < typedArray.getIndexCount(); i++){
            int attr = typedArray.getIndex(i);
            if(R.styleable.SpannableEditbox_clickableSpan == attr)
                clickableSpan = typedArray.getBoolean(attr, false);
            else if(R.styleable.SpannableEditbox_bold == attr)
                bold = typedArray.getBoolean(attr, false);
            else if(R.styleable.SpannableEditbox_italic == attr)
                italic = typedArray.getBoolean(attr, false);
            else if(R.styleable.SpannableEditbox_underLine == attr)
                underLine = typedArray.getBoolean(attr, false);
            else if(R.styleable.SpannableEditbox_foregroundColor == attr)
                foregroundColor = typedArray.getBoolean(attr, false);
            else if(R.styleable.SpannableEditbox_showToolbar == attr)
                needToolbar = typedArray.getBoolean(attr, false);
            else {
                //do something if need
                break;
            }
        }
        typedArray.recycle();
    }

    private void initView(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.spannable_editbox_layout, this, true);
        editText = (RichEditor) findViewById(R.id.editor);
//        editText.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
//            @Override
//            public void onTextChange(String s) {
//                content = s;
//            }
//        });
        editText.setOnDecorationChangeListener(new RichEditor.OnDecorationStateListener() {
            @Override
            public void onStateChangeListener(String s, List<RichEditor.Type> list) {
                setBtnState(btnBold, (isBold = list.contains(RichEditor.Type.BOLD)) ? R.mipmap.bold_selected : R.mipmap.bold);
                setBtnState(btnItalic, (isItalic = list.contains(RichEditor.Type.ITALIC)) ? R.mipmap.italic_selected : R.mipmap.italic);
                setBtnState(btnUnderLine, (isUnderLine = list.contains(RichEditor.Type.UNDERLINE)) ? R.mipmap.underline_selected : R.mipmap.underline);
            }
        });
        editText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                if(event.getAction() == (MotionEvent.ACTION_DOWN)) {
                    ((WebView) v).requestDisallowInterceptTouchEvent(true);
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showToolbar();
                        }
                    },200l);

                }
                return false;

            }
        });
        editText.focusEditor();
        btnBold = (ImageView) findViewById(R.id.action_bold);
        btnBold.setOnClickListener(this);
        btnBold.setVisibility(bold ? View.VISIBLE : View.GONE);
        btnItalic = (ImageView) findViewById(R.id.action_italic);
        btnItalic.setOnClickListener(this);
        btnItalic.setVisibility(italic ? View.VISIBLE : View.GONE);
        btnUnderLine = (ImageView) findViewById(R.id.action_underline);
        btnUnderLine.setOnClickListener(this);
        btnUnderLine.setVisibility(underLine ? View.VISIBLE : View.GONE);
        undo = (ImageView) findViewById(R.id.action_undo);
        undo.setOnClickListener(this);
        redo = (ImageView) findViewById(R.id.action_redo);
        redo.setOnClickListener(this);
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        toolbar.setVisibility(needToolbar ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if(R.id.action_bold == v.getId()){
            setBtnState(v, (isBold = !isBold) ? R.mipmap.bold_selected : R.mipmap.bold);
            editText.setBold();
        }else if(R.id.action_italic == v.getId()){
            setBtnState(v, (isItalic = !isItalic) ? R.mipmap.italic_selected : R.mipmap.italic);
            editText.setItalic();
        }else if(R.id.action_underline == v.getId()){
            setBtnState(v, (isUnderLine = !isUnderLine) ? R.mipmap.underline_selected : R.mipmap.underline);
            editText.setUnderline();
        }else if(R.id.action_undo == v.getId()){
            editText.undo();
        }else if(R.id.action_redo == v.getId()){
            editText.redo();
        }
    }

    private void setBtnState(View btn, int res){
        ((ImageView)btn).setImageDrawable(getResources().getDrawable(res));
//        btn.setBackgroundDrawable(getResources().getDrawable(res));
    }

    @Deprecated
    private SpannableString getSpannableString(String src){
        SpannableString spannaText = new SpannableString(src);
        Object span;
        if(isUnderLine)
            span = new UnderlineSpan();
        else if(isBold && !isItalic)
            span = new StyleSpan(Typeface.BOLD);
        else if(isItalic && !isBold)
            span = new StyleSpan(Typeface.ITALIC);
        else if(isBold && isItalic)
            span  =new StyleSpan(Typeface.BOLD_ITALIC);
        else
            span = new StyleSpan(Typeface.NORMAL);
        spannaText.setSpan(span, 0, src.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannaText;
    }

    public String getHtml(){
        return editText.getHtml();
    }

    public void showToolbar(){
        if(toolbar != null)
            toolbar.setVisibility(View.VISIBLE);
    }

    public void hideToolbar(){
        if(toolbar != null)
            toolbar.setVisibility(View.GONE);
    }

    public boolean isToolbarShow(){
        return toolbar.getVisibility() == View.VISIBLE;
    }

    public void setText(String text){
        if(!TextUtils.isEmpty(text))
            editText.setHtml(text);
    }

    public void focus(){
        editText.focusEditor();
    }
}
