package com.example.fifthweek

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text

class CustomMainFoodItem : LinearLayout {
    lateinit var item_image : ImageView
    lateinit var item_no : TextView
    lateinit var item_name : TextView
    lateinit var item_location : TextView
    lateinit var item_distance : TextView
    lateinit var textVisit : TextView
    lateinit var textReview : TextView

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, delStyleAttrs: Int)
            : super(context, attrs, delStyleAttrs) {
        init(context, attrs, delStyleAttrs)
        getAttrs(attrs, delStyleAttrs)
    }

    private fun init(context: Context, attrs: AttributeSet?, delStyleAttrs: Int) {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.custom_main_food_item, this, false)
        addView(view)

        item_image = view.findViewById(R.id.item_image)
        item_no = view.findViewById(R.id.item_no)
        item_name = view.findViewById(R.id.item_name)
        item_location = view.findViewById(R.id.item_location)
        item_distance = view.findViewById(R.id.item_distance)
        textVisit = view.findViewById(R.id.textVisit)
        textReview = view.findViewById(R.id.textReview)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMainFoodItem)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMainFoodItem)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        item_image.setImageResource(typedArray.getResourceId(R.styleable.CustomMainFoodItem_item_image, R.drawable.food_item_bossam))
        item_no.text = typedArray.getText(R.styleable.CustomMainFoodItem_item_no)
        item_name.text = typedArray.getText(R.styleable.CustomMainFoodItem_item_name)
        item_location.text = typedArray.getText(R.styleable.CustomMainFoodItem_item_location)
        item_distance.text = typedArray.getText(R.styleable.CustomMainFoodItem_item_distance)
        textVisit.text = typedArray.getText(R.styleable.CustomMainFoodItem_text_visit)
        textReview.text = typedArray.getText(R.styleable.CustomMainFoodItem_text_review)
    }

}