package com.moneytracker.nativeapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import com.nphlab.sdk.ads.NphAds
import com.nphlab.sdk.ads.listener.NphAdListener
import com.nphlab.sdk.ads.AdError
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.moneytracker.nativeapp.R
import com.moneytracker.nativeapp.data.Category
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import com.moneytracker.nativeapp.data.TransactionType
import kotlinx.coroutines.launch
import java.util.Locale

class CategoriesActivity : AppCompatActivity() {
    
    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val languageCode = prefs.getString("language", "en") ?: "en"
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = newBase.resources.configuration
        config.setLocale(locale)
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }
    
    private lateinit var repository: MoneyTrackerRepository
    private lateinit var rvCategories: RecyclerView
    private lateinit var tabLayout: TabLayout
    private lateinit var fabAdd: FloatingActionButton
    
    private var currentType = TransactionType.EXPENSE
    private var categories = listOf<Category>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        
        repository = MoneyTrackerRepository(this)
        
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        
        // Register back button interstitial ad with timeout fallback
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            private var isHandling = false
            override fun handleOnBackPressed() {
                if (isHandling) return
                isHandling = true
                val handler = android.os.Handler(mainLooper)
                val fallback = Runnable { if (!isFinishing) finish() }
                handler.postDelayed(fallback, 3000)
                NphAds.showInterstitial(
                    activity = this@CategoriesActivity,
                    nameSpace = "nsp_inter_categories",
                    listener = object : NphAdListener() {
                        override fun onAdDismissed() {
                            handler.removeCallbacks(fallback)
                            finish()
                        }
                        override fun onAdFailed(error: AdError) {
                            handler.removeCallbacks(fallback)
                            finish()
                        }
                    }
                )
            }
        })
        
        rvCategories = findViewById(R.id.rvCategories)
        tabLayout = findViewById(R.id.tabLayout)
        fabAdd = findViewById(R.id.fabAddCategory)
        
        rvCategories.layoutManager = LinearLayoutManager(this)
        
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentType = when (tab?.position) {
                    1 -> TransactionType.INCOME
                    else -> TransactionType.EXPENSE
                }
                loadCategories()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        
        fabAdd.setOnClickListener {
            showCategoryDialog(null)
        }
        
        loadCategories()
    }
    
    private fun loadCategories() {
        lifecycleScope.launch {
            repository.getAllCategories().observe(this@CategoriesActivity) { allCategories ->
                categories = allCategories?.filter { it.type == currentType } ?: emptyList()
                rvCategories.adapter = CategoryAdapter(categories, 
                    onEdit = { category -> showCategoryDialog(category) },
                    onDelete = { category -> deleteCategory(category) }
                )
            }
        }
    }
    
    private fun showCategoryDialog(category: Category?) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_category, null)
        val etName = dialogView.findViewById<TextInputEditText>(R.id.etCategoryName)
        val rvIcons = dialogView.findViewById<RecyclerView>(R.id.rvIcons)
        val rvColors = dialogView.findViewById<RecyclerView>(R.id.rvColors)
        val btnCancel = dialogView.findViewById<MaterialButton>(R.id.btnCancel)
        val btnSave = dialogView.findViewById<MaterialButton>(R.id.btnSave)
        
        var selectedIcon = category?.icon ?: getDefaultIcon()
        var selectedColor = category?.color ?: "#FF6B6B"
        
        if (category != null) {
            etName.setText(category.name)
            dialogView.findViewById<TextView>(R.id.tvDialogTitle).text = getString(R.string.edit_category)
        }
        
        // Setup icons grid
        val icons = listOf(
            "ic_restaurant" to R.drawable.ic_restaurant,
            "ic_directions_car" to R.drawable.ic_directions_car,
            "ic_shopping_bag" to R.drawable.ic_shopping_bag,
            "ic_movie" to R.drawable.ic_movie,
            "ic_receipt" to R.drawable.ic_receipt,
            "ic_local_hospital" to R.drawable.ic_local_hospital,
            "ic_school" to R.drawable.ic_school,
            "ic_work" to R.drawable.ic_work,
            "ic_computer" to R.drawable.ic_computer,
            "ic_trending_up" to R.drawable.ic_trending_up,
            "ic_card_giftcard" to R.drawable.ic_card_giftcard,
            "ic_more" to R.drawable.ic_more
        )
        
        rvIcons.layoutManager = GridLayoutManager(this, 4)
        rvIcons.adapter = IconAdapter(icons, selectedIcon) { iconName ->
            selectedIcon = iconName
        }
        
        // Setup colors grid
        val colors = listOf(
            "#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4", 
            "#FFEAA7", "#DDA0DD", "#98D8C8", "#95A5A6"
        )
        
        rvColors.layoutManager = GridLayoutManager(this, 4)
        rvColors.adapter = ColorAdapter(colors, selectedColor) { color ->
            selectedColor = color
        }
        
        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .create()
        
        btnCancel.setOnClickListener { dialog.dismiss() }
        
        btnSave.setOnClickListener {
            val name = etName.text?.toString()?.trim() ?: ""
            if (name.isEmpty()) {
                etName.error = getString(R.string.category_name)
                return@setOnClickListener
            }
            
            lifecycleScope.launch {
                val newCategory = Category(
                    id = category?.id ?: name.lowercase().replace(" ", "_"),
                    name = name,
                    type = currentType,
                    icon = selectedIcon,
                    color = selectedColor,
                    isDefault = false
                )
                
                if (category != null) {
                    repository.updateCategory(newCategory)
                } else {
                    repository.addCategory(newCategory)
                }
                
                Toast.makeText(this@CategoriesActivity, R.string.category_saved, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                loadCategories()
            }
        }
        
        dialog.show()
    }
    
    private fun deleteCategory(category: Category) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.delete_category)
            .setMessage(R.string.delete_category_confirm)
            .setPositiveButton(R.string.delete) { _, _ ->
                lifecycleScope.launch {
                    repository.deleteCategory(category)
                    Toast.makeText(this@CategoriesActivity, R.string.category_deleted, Toast.LENGTH_SHORT).show()
                    loadCategories()
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
    
    private fun getDefaultIcon(): String {
        return if (currentType == TransactionType.EXPENSE) "ic_restaurant" else "ic_work"
    }
}

class CategoryAdapter(
    private val categories: List<Category>,
    private val onEdit: (Category) -> Unit,
    private val onDelete: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivIcon: ImageView = view.findViewById(R.id.ivIcon)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val btnEdit: ImageView = view.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = view.findViewById(R.id.btnDelete)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.tvName.text = category.name
        
        val iconRes = getIconResource(holder.itemView.context, category.icon)
        holder.ivIcon.setImageResource(iconRes)
        
        // Parse color and apply
        try {
            val color = android.graphics.Color.parseColor(category.color)
            holder.ivIcon.background.setTint(color)
        } catch (e: Exception) {
            // Use default color
        }
        
        holder.btnEdit.setOnClickListener { onEdit(category) }
        holder.btnDelete.setOnClickListener { onDelete(category) }
    }
    
    override fun getItemCount() = categories.size
    
    private fun getIconResource(context: Context, iconName: String): Int {
        return context.resources.getIdentifier(iconName, "drawable", context.packageName)
            .takeIf { it != 0 } ?: R.drawable.ic_more
    }
}

class IconAdapter(
    private val icons: List<Pair<String, Int>>,
    private var selectedIcon: String,
    private val onIconSelected: (String) -> Unit
) : RecyclerView.Adapter<IconAdapter.ViewHolder>() {
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivIcon: ImageView = view.findViewById(R.id.ivIcon)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        val imageView = ImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(80, 80)
            setPadding(16, 16, 16, 16)
        }
        return ViewHolder(imageView)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, resId) = icons[position]
        holder.ivIcon.setImageResource(resId)
        holder.ivIcon.alpha = if (name == selectedIcon) 1.0f else 0.5f
        holder.ivIcon.setOnClickListener {
            selectedIcon = name
            onIconSelected(name)
            notifyDataSetChanged()
        }
    }
    
    override fun getItemCount() = icons.size
}

class ColorAdapter(
    private val colors: List<String>,
    private var selectedColor: String,
    private val onColorSelected: (String) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewColor: View = view.findViewById(R.id.viewColor)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_color, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = colors[position]
        try {
            val parsedColor = android.graphics.Color.parseColor(color)
            holder.viewColor.setBackgroundColor(parsedColor)
        } catch (e: Exception) {
        }
        
        holder.viewColor.alpha = if (color == selectedColor) 1.0f else 0.5f
        holder.viewColor.setOnClickListener {
            selectedColor = color
            onColorSelected(color)
            notifyDataSetChanged()
        }
    }
    
    override fun getItemCount() = colors.size
}
