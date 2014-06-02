package com.example.shakethepiggybank;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ShrinkView extends ViewGroup{
	
	public ShrinkView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		for(int i =0; i< this.getChildCount();i++)
		{
			View child = this.getChildAt(i);
			LayoutParams lp = (LayoutParams)child.getLayoutParams();
			child.layout(lp.left,lp.top, lp.right, lp.bottom);

		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		int containerWidth = MeasureSpec.getSize(widthMeasureSpec);
		int containerHeight = MeasureSpec.getSize(heightMeasureSpec);
		int images = this.getChildCount();
		int hPos = 0;
		int widthPerImage = containerWidth/images;
		for(int i = 0; i<images;i++)
		{
			View child = this.getChildAt(i);
			measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
			
			LayoutParams lp = (LayoutParams) child.getLayoutParams();
			
			//get child width
			int width = child.getMeasuredWidth();
			int height = child.getMeasuredHeight();
			
			if(width > widthPerImage)
			child.setScaleX((float)widthPerImage/width);
			if(height > containerHeight)
			child.setScaleY((float)containerHeight/height);

			
			lp.top = 0;
			lp.bottom = (int) (child.getScaleY()*height);
			lp.left = hPos;
			hPos +=(int)(child.getScaleX()*width);
			lp.right = hPos;
			
			
		}
		setMeasuredDimension(containerWidth, containerHeight);
		
	}
	@Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }
	
	//
	public static class LayoutParams extends ViewGroup.LayoutParams {
        
		public int left;
		public int right;
		public int top;
		public int bottom;
		public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

}
