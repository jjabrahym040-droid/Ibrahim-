package com.androidify.myandroida

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private data class Product(
        val id: String,
        val name: String,
        val description: String,
        val price: Int,
        val emoji: String,
        val color: String
    )

    private val products = listOf(
        Product(
            "headphones",
            "سماعات لاسلكية",
            "صوت واضح • تصميم مريح • بطارية تدوم حتى ٢٠ ساعة",
            129,
            "🎧",
            "#E7EDF8"
        ),
        Product(
            "backpack",
            "حقيبة ظهر يومية",
            "خفيفة وعملية • جيب للحاسوب • مناسبة للعمل والسفر",
            89,
            "🎒",
            "#FBEEDC"
        )
    )

    private val navy = Color.rgb(20, 40, 61)
    private val orange = Color.rgb(255, 173, 50)
    private val muted = Color.rgb(93, 108, 122)
    private val quantities = mutableMapOf<String, Int>()
    private val prefs by lazy { getSharedPreferences("cart", MODE_PRIVATE) }
    private lateinit var productList: LinearLayout
    private lateinit var cartButton: Button
    private lateinit var summary: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        products.forEach {
            quantities[it.id] = prefs.getInt(it.id, 0).coerceIn(0, 99)
        }

        val root = column().apply {
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            setBackgroundColor(Color.rgb(244, 246, 248))
        }
        setContentView(root)
        root.setOnApplyWindowInsetsListener { view, insets ->
            @Suppress("DEPRECATION")
            view.setPadding(
                insets.systemWindowInsetLeft,
                insets.systemWindowInsetTop,
                insets.systemWindowInsetRight,
                insets.systemWindowInsetBottom
            )
            insets
        }
        root.requestApplyInsets()

        val header = column().apply {
            setPadding(dp(20), dp(20), dp(20), dp(18))
            setBackgroundColor(navy)
        }
        header.addView(label("متجري  /  تسوّق ببساطة", 25, Color.WHITE, true))
        header.addView(label("منتجات مختارة، بأسعار تحبّها", 14, Color.rgb(206, 219, 231)))
        root.addView(header)

        val scroll = ScrollView(this).apply { isFillViewport = true }
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        val content = column().apply { setPadding(dp(18), dp(16), dp(18), dp(20)) }
        scroll.addView(content)

        val search = EditText(this).apply {
            hint = "ابحث عن منتج..."
            textSize = 16f
            setSingleLine(true)
            setTextColor(navy)
            setHintTextColor(muted)
            setPadding(dp(16), dp(10), dp(16), dp(10))
            background = shape(Color.WHITE, 14)
            contentDescription = "البحث في المنتجات"
        }
        content.addView(search, LinearLayout.LayoutParams(-1, dp(52)))

        val banner = column().apply {
            setPadding(dp(18), dp(16), dp(18), dp(16))
            background = shape(Color.rgb(255, 235, 201), 18)
        }
        banner.addView(label("اختيارات قليلة. جودة كبيرة.", 22, navy, true))
        banner.addView(label("منتجان لأسلوب حياتك • شحن مجاني", 14, navy))
        content.addView(banner, spacedParams(16))
        content.addView(label("اكتشف المنتجات", 21, navy, true), spacedParams(20))

        productList = column()
        content.addView(productList)
        showProducts("")

        val footer = column().apply {
            setPadding(dp(18), dp(12), dp(18), dp(12))
            background = shape(Color.WHITE, 0)
            elevation = dp(8).toFloat()
        }
        summary = label("", 16, navy, true)
        footer.addView(summary)
        cartButton = actionButton("") { showCart() }
        footer.addView(cartButton, spacedParams(8))
        root.addView(footer)
        updateSummary()

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showProducts(s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showProducts(query: String) {
        productList.removeAllViews()
        val matches = products.filter {
            "${it.name} ${it.description}".contains(query.trim(), ignoreCase = true)
        }
        if (matches.isEmpty()) {
            productList.addView(label("لا توجد نتائج. جرّب «سماعات» أو «حقيبة».", 16, muted))
        }
        matches.forEach { product ->
            val card = column().apply {
                setPadding(dp(16), dp(16), dp(16), dp(16))
                background = shape(Color.WHITE, 18)
            }
            val image = label(product.emoji, 64, navy).apply {
                gravity = Gravity.CENTER
                background = shape(Color.parseColor(product.color), 14)
                contentDescription = product.name
            }
            card.addView(image, LinearLayout.LayoutParams(-1, dp(136)))
            card.addView(label(product.name, 22, navy, true), spacedParams(12))
            card.addView(label(product.description, 14, muted))
            card.addView(label("متوفر الآن  •  شحن مجاني", 13, Color.rgb(30, 116, 77)))
            card.addView(label("${product.price} ر.س", 24, navy, true), spacedParams(8))
            card.addView(actionButton("أضف إلى السلة  +") {
                val current = quantities[product.id] ?: 0
                if (current < 99) {
                    quantities[product.id] = current + 1
                    saveCart()
                    Toast.makeText(this, "تمت إضافة ${product.name}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "الحد الأقصى ٩٩ قطعة لكل منتج", Toast.LENGTH_SHORT).show()
                }
            }, spacedParams(8))
            productList.addView(card, spacedParams(14))
        }
    }

    private fun showCart() {
        val body = column().apply {
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            setPadding(dp(20), dp(8), dp(20), dp(16))
        }
        val scroll = ScrollView(this).apply { addView(body) }
        val dialog = AlertDialog.Builder(this)
            .setTitle("سلة مشترياتك")
            .setView(scroll)
            .setNegativeButton("متابعة التسوّق", null)
            .setPositiveButton("إتمام الطلب", null)
            .create()

        fun render() {
            body.removeAllViews()
            val selected = products.filter { (quantities[it.id] ?: 0) > 0 }
            if (selected.isEmpty()) {
                body.addView(label("سلتك فارغة. أضف منتجك الأول!", 18, muted))
            }
            selected.forEach { product ->
                val quantity = quantities[product.id] ?: 0
                body.addView(label("${product.emoji}  ${product.name}", 18, navy, true), spacedParams(12))
                body.addView(label("${product.price} ر.س للقطعة", 14, muted))
                val controls = LinearLayout(this).apply {
                    gravity = Gravity.CENTER_VERTICAL
                }
                controls.addView(actionButton("−") {
                    quantities[product.id] = quantity - 1
                    saveCart()
                    render()
                }, LinearLayout.LayoutParams(dp(56), dp(48)))
                controls.addView(label("$quantity", 18, navy, true).apply {
                    gravity = Gravity.CENTER
                    contentDescription = "الكمية $quantity"
                }, LinearLayout.LayoutParams(0, dp(48), 1f))
                controls.addView(actionButton("+") {
                    if (quantity < 99) {
                        quantities[product.id] = quantity + 1
                        saveCart()
                        render()
                    }
                }.apply { isEnabled = quantity < 99 }, LinearLayout.LayoutParams(dp(56), dp(48)))
                body.addView(controls, spacedParams(8))
                body.addView(label("المجموع: ${quantity * product.price} ر.س", 15, navy))
            }
            body.addView(label("الشحن: مجاني\nالإجمالي: ${total()} ر.س", 19, navy, true), spacedParams(20))
            body.addView(label("طلب تجريبي فقط، دون دفع أو توصيل فعلي.", 13, muted))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = selected.isNotEmpty()
        }

        dialog.setOnShowListener {
            render()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val amount = total()
                AlertDialog.Builder(this)
                    .setTitle("تأكيد الطلب التجريبي")
                    .setMessage("الإجمالي $amount ر.س مع شحن مجاني.\nلن يتم تحصيل أي مبلغ أو إرسال المنتجات.")
                    .setNegativeButton("رجوع", null)
                    .setPositiveButton("تأكيد") { _, _ ->
                        quantities.clear()
                        saveCart()
                        dialog.dismiss()
                        AlertDialog.Builder(this)
                            .setTitle("تم تسجيل طلبك التجريبي ✓")
                            .setMessage("شكرًا لتجربتك! يمكنك الآن العودة للتسوّق وإضافة منتجات من جديد.")
                            .setPositiveButton("حسنًا", null)
                            .show()
                    }
                    .show()
            }
        }
        dialog.show()
    }

    private fun total(): Int = products.sumOf { it.price * (quantities[it.id] ?: 0) }

    private fun saveCart() {
        prefs.edit().apply {
            products.forEach { putInt(it.id, quantities[it.id] ?: 0) }
        }.apply()
        updateSummary()
    }

    private fun updateSummary() {
        val count = quantities.values.sum()
        summary.text = "الإجمالي: ${total()} ر.س  •  الشحن مجاني"
        cartButton.text = "عرض السلة ($count)"
    }

    private fun column() = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
    }

    private fun label(
        value: String,
        size: Int,
        color: Int,
        bold: Boolean = false
    ) = TextView(this).apply {
        text = value
        textSize = size.toFloat()
        setTextColor(color)
        gravity = Gravity.START
        setLineSpacing(dp(3).toFloat(), 1f)
        if (bold) setTypeface(typeface, Typeface.BOLD)
        setPadding(0, dp(4), 0, dp(4))
    }

    private fun actionButton(value: String, action: () -> Unit) = Button(this).apply {
        text = value
        textSize = 16f
        isAllCaps = false
        setTextColor(navy)
        typeface = Typeface.DEFAULT_BOLD
        background = shape(orange, 12)
        minHeight = dp(48)
        setPadding(dp(8), dp(8), dp(8), dp(8))
        setOnClickListener { action() }
    }

    private fun shape(color: Int, radius: Int) = GradientDrawable().apply {
        setColor(color)
        cornerRadius = dp(radius).toFloat()
    }

    private fun spacedParams(top: Int) = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply { topMargin = dp(top) }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density + 0.5f).toInt()
}
