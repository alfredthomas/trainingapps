package com.example.sschuuac3;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class WrapPanel extends ViewGroup{

	public WrapPanel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
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
		int hPos=0;
		int vPos=0;
		int maxHeight = 0;
		int maxWidth = 0;
		int containerWidth = MeasureSpec.getSize(widthMeasureSpec);
		int containerWidthMode = MeasureSpec.getMode(widthMeasureSpec);
		int containerHeight = MeasureSpec.getSize(heightMeasureSpec);
		//int containerHeightMode = MeasureSpec.getMode(heightMeasureSpec);
		int totMaxHeight = 0;
		int totMaxWidth = 0;
		if (!(containerWidthMode == MeasureSpec.UNSPECIFIED)){
			for(int i =0; i< this.getChildCount();i++)
			{
				View child = this.getChildAt(i);
				measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
				
				LayoutParams lp = (LayoutParams) child.getLayoutParams();
				
				//get child width
				int width = child.getMeasuredWidth();
				int height = child.getMeasuredHeight();
				
				//if width + hPos< widthMeasuredSpec
				if(width+hPos < containerWidth)
				{
					lp.left = hPos;
					//horizontal position is current + width of view
					hPos = width+hPos;
					lp.right = hPos;
					//update maxHeight = Math.max(maxHeight,vPos+child.getHeight)
					lp.top = vPos;
					lp.bottom =vPos+height;
					maxHeight = Math.max(maxHeight, vPos+height);
					maxWidth = Math.max(hPos, maxWidth);	
				}
				//else go to next line
				else{
					//vPos = maxHeight
					//hPos = 0
					vPos = maxHeight;
					hPos = 0;
					lp.left = hPos;
					hPos = width+hPos;
					lp.right = hPos;
					
					lp.top = vPos;
					lp.bottom =vPos+height;
					maxHeight = Math.max(maxHeight, vPos+height);
				}
				totMaxWidth = Math.max(hPos, totMaxWidth);
				totMaxHeight = Math.max(maxHeight, totMaxHeight);
		}
			
		}
		//horizontal scroll
		else
		{for(int i =0; i< this.getChildCount();i++)
		{
			View child = this.getChildAt(i);
			measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
			
			LayoutParams lp = (LayoutParams) child.getLayoutParams();
			
			//get child width
			int width = child.getMeasuredWidth();
			int height = child.getMeasuredHeight();
			
			//if height + vPos< Measured height
			if(height+vPos < containerHeight)
			{
				lp.top = vPos;
				//vertical position is current + height of view
				vPos = height+vPos;
				lp.bottom = vPos;
				//update maxWidth = Math.max(maxWidth,hPos+child.getWidth)
				lp.left = hPos;
				lp.right =hPos+width;
				maxWidth = Math.max(maxWidth, hPos+width);
			}
			//else go to next line
			else{
				//hPos = maxWidth
				//vPos = 0
				vPos = 0;
				hPos = maxWidth;
				lp.top = vPos;
				vPos = height+vPos;
				lp.bottom = vPos;
				
				lp.left = hPos;
				lp.right =hPos+width;
				maxWidth = Math.max(maxWidth, hPos+width);
				
				}
			totMaxWidth = Math.max(maxWidth, totMaxWidth);
			totMaxHeight = Math.max(vPos, totMaxHeight);
			}

		}
		setMeasuredDimension(totMaxWidth, totMaxHeight);
		
	}
//	@Override
//    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return new WrapPanel.LayoutParams(getContext(), attrs);
//    }

//    @Override
//    protected LayoutParams generateDefaultLayoutParams() {
//        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }
	
	//
	public static class LayoutParams extends MarginLayoutParams {
        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         */
        
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
