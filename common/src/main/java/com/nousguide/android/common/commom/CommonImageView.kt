package com.nousguide.android.common.commom

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import com.nousguide.android.common.commom.shapeable.CommonShapeable
import com.nousguide.android.common.commom.shapeable.CommonShapeableHandler
import com.nousguide.android.common.extensions.color

class CommonImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShapeableImageView(context, attrs, defStyleAttr), CommonShapeable<CommonImageView> {

    override val shapeableHandler = CommonShapeableHandler { this }

    override fun bgColor(colorRes: Int) {
        setBackgroundColor(color(colorRes))
    }

    override fun border(colorRes: Int, width: Int) {
        border(color(colorRes), width)
    }

    override fun getShapeAppearanceModel(): ShapeAppearanceModel {
        return super<ShapeableImageView>.getShapeAppearanceModel()
    }

    override fun setShapeAppearanceModel(shapeAppearanceModel: ShapeAppearanceModel) {
        super<ShapeableImageView>.setShapeAppearanceModel(shapeAppearanceModel)
    }
}
