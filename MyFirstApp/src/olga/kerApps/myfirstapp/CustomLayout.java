package olga.kerApps.myfirstapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

public class CustomLayout extends ViewGroup {
	/** The amount of space used by children in the left gutter. */
    
    /** The amount of space used by children in the right gutter. */
   ;

    /** These are used for computing child frames based on their gravity. */
    //private final Rect mTmpContainerRect = new Rect();
   // private final Rect mTmpChildRect = new Rect();

    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
   
    /**
     * Any layout manager that doesn't scroll will want this.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    /**
     * Ask all children to measure themselves and compute the measurement of this
     * layout based on the children.
     */
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);        
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        
        int leftStart =  getPaddingLeft();
        int leftPos = leftStart;
        int topStart = getPaddingTop(); 
        int topPos = topStart;
        int nextTopPos = topPos;
        int nextLeftPos = leftPos;
        
        // Measurement will ultimately be computing these values.
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;

        // Iterate through all children, measuring them and computing our dimensions
        // from their size.
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                // Measure the child.
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

                // Update our size information based on the layout params.  Children
                // that asked to be positioned on the left or right go in those gutters.
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();                
                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();
                
                this.setChildLayoutPosition( lp, leftPos, topPos, width, height);
                
                /* If we have unlimited height or both width and height are set */
                if (heightMode == MeasureSpec.UNSPECIFIED || 
                		(heightMode != MeasureSpec.UNSPECIFIED && widthMode != MeasureSpec.UNSPECIFIED )) {
	                
                	int hSpaceChildWants = width + lp.leftMargin + lp.rightMargin;	                
	                int leftHSpace = widthSize - leftPos;
	                
	                if (hSpaceChildWants <= leftHSpace) {
	                	
	                	// update the left render cursor
	                	leftPos = lp.layoutPosition.right;                	
	                	if (lp.layoutPosition.bottom > nextTopPos)
	                		nextTopPos = lp.layoutPosition.bottom;
	                }
	                else {
	                	leftPos = leftStart;
	                	topPos = nextTopPos;
	                	
	                	this.setChildLayoutPosition(lp, leftPos, topPos, width, height);	                	
	                 	if (lp.layoutPosition.bottom > nextTopPos)
	                		nextTopPos = lp.layoutPosition.bottom;
	                	
	                }
	                	                	                
	                maxHeight = Math.max(maxHeight, lp.layoutPosition.bottom);
                }
                /* Unlimited width*/
                else {
            		
                	int vSpaceChildWants = height + lp.topMargin + lp.bottomMargin;	                
	                int leftVSpace = heightSize - topPos;
	                
	                if (vSpaceChildWants <= leftVSpace) {
	                	
	                	// update the top render cursor
	                	topPos = lp.layoutPosition.bottom;                	
	                	if (lp.layoutPosition.right > nextLeftPos)
	                		nextLeftPos = lp.layoutPosition.right;
	                }
	                else {
	                	topPos = topStart;
	                	leftPos = nextLeftPos;
	                	  
	                	this.setChildLayoutPosition(lp, leftPos, topPos, width, height);	                	
	                	if (lp.layoutPosition.right > nextLeftPos)
	                		nextLeftPos = lp.layoutPosition.right;
	                	
	                }
	                
	                maxWidth = Math.max(maxWidth, lp.layoutPosition.right);
                }
                
                                   
                childState = combineMeasuredStates(childState, child.getMeasuredState());
            }
        }

       
        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

       
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }
    
    private void setChildLayoutPosition(LayoutParams lp, int leftPos, int topPos, int width, int height) {
    	lp.layoutPosition.left = lp.leftMargin + leftPos;
        lp.layoutPosition.right = lp.layoutPosition.left + width +  lp.rightMargin;
        lp.layoutPosition.top = lp.topMargin + topPos;
        lp.layoutPosition.bottom = lp.layoutPosition.top + height + lp.bottomMargin;
               
    }
    /**
     * Position all children within this layout.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                
               
                child.layout(lp.layoutPosition.left, lp.layoutPosition.top,
                		lp.layoutPosition.right, lp.layoutPosition.bottom);
            }
        }
    }
 
    // ----------------------------------------------------------------------
    // The rest of the implementation is for custom per-child layout parameters.
    // If you do not need these (for example you are writing a layout manager
    // that does fixed positioning of its children), you can drop all of this.

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /**
     * Custom per-child layout information.
     */
    public static class LayoutParams extends MarginLayoutParams {
        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         */
        public int gravity = Gravity.TOP | Gravity.START;

        public static int POSITION_MIDDLE = 0;
        public static int POSITION_LEFT = 1;
        public static int POSITION_RIGHT = 2;

        public int position = POSITION_MIDDLE;

        public Rect layoutPosition;
        
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            layoutPosition = new Rect();
            // Pull the layout param values from the layout XML during
            // inflation.  This is not needed if you don't care about
            // changing the layout behavior in XML.
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CustomLayoutLP);
            gravity = a.getInt(R.styleable.CustomLayoutLP_android_layout_gravity, gravity);
            position = a.getInt(R.styleable.CustomLayoutLP_layout_position, position);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            layoutPosition = new Rect();
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            layoutPosition = new Rect();
        }
    }
}


